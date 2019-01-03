package cn.newcapec.city.smart.gateway.authorization.manager.impl;

import cn.newcapec.city.smart.api.IAppService;
import cn.newcapec.city.smart.common.redis.RedisClient;
import cn.newcapec.city.smart.common.utils.CollectionUtils;
import cn.newcapec.city.smart.common.utils.ThreadUtils;
import cn.newcapec.city.smart.common.utils.Utils;
import cn.newcapec.city.smart.common.utils.security.MD5Util;
import cn.newcapec.city.smart.gateway.authorization.annotation.AuthorizationEnum;
import cn.newcapec.city.smart.gateway.authorization.manager.TokenManager;
import cn.newcapec.city.smart.gateway.authorization.model.AuthResult;
import cn.newcapec.city.smart.gateway.authorization.model.JWTModel;
import cn.newcapec.city.smart.gateway.authorization.model.TokenModel;
import cn.newcapec.city.smart.gateway.config.appuser.AppUserConfig;
import cn.newcapec.city.smart.model.AppUserModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 *
 * @author ScienJus
 * @date 2015/7/31.
 */
@Slf4j
@Component
public class RedisTokenManager implements TokenManager {

    private static final String TOKEN_PRE = "token_redis_";
    private static final String APPUSER_TOKEN = "appuser_token_redis_";
    private static final String TOKEN_REPEAT_PRE = "token_repeat_redis_";
    private static final String CHARSET = "UTF-8";

    private final Gson myGson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    @Autowired
    private AppUserConfig appUserConfig;
    @Autowired
    protected RedisClient redisClient;
    //引用dubbo服务接口
    @Reference
    protected IAppService appService;

    //创建一个token关联上指定用户
    @Override
    public TokenModel createToken(AppUserModel appUser) {
        if (appUser == null) {
            return null;
        }
        //判断是否有当前用户的token
        String token = redisClient.get(APPUSER_TOKEN + appUser.getId());
        if (token != null) {
            //有其他用户的token，通知APP端退出
            TokenModel model = redisClient.get(TOKEN_PRE + token, TokenModel.class);
            if (model != null) {
                //获得登录的用户的设备ID
                String deviceId = model.getAppUser().getDeviceId();
                //不是同一个设备的登录
                if (!Objects.equals(deviceId, appUser.getDeviceId())) {
                    //TODO 生产一条用户被迫登出的消息
                }
            }
            //销毁token
            redisClient.del(TOKEN_PRE + token);
            redisClient.del(APPUSER_TOKEN + appUser.getId());
        }
        //使用uuid作为源token
        token = Utils.getUUIDShort();
        TokenModel model = new TokenModel(token);
        //保存用户token
        appUser.setToken(token);
        model.setAppUser(appUser);
        //设置token的创建时间
        model.setCtdt(appService.nowTime());
        //存储到redis并设置过期时间
        int failTime = appUserConfig.getToken().getFailTime();
        boolean result = redisClient.set(TOKEN_PRE + token, model.toString(), failTime, TimeUnit.HOURS);
        if (!result) {
            return null;
        }
        //保存appUser的token
        result = redisClient.set(APPUSER_TOKEN + appUser.getId(), token, failTime, TimeUnit.HOURS);
        if (!result) {
            redisClient.del(TOKEN_PRE + token);
            return null;
        }
        return model;
    }

    //检查token是否有效
    @Override
    public AuthResult checkToken(AuthorizationEnum type, String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return AuthResult.create(AuthResult.Result.FAIL);
        }
        //对token进行解码
        authentication = this.decode(authentication);
        //验证token合法性
        JWTModel jwtModel = this.jwtModel(authentication);
        if (jwtModel == null) {
            return AuthResult.create(AuthResult.Result.FAIL);
        }
        //APP用户认证
        if (type == AuthorizationEnum.APP_AUTHORIZATION) {
            //1.检查token是否已经过期
            long timeout = appUserConfig.getToken().getTimeout() * 1000L;
            if (appService.nowTime() - jwtModel.getTime() > timeout) {
                //token已经超时
                return AuthResult.create(AuthResult.Result.TIMEOUT);
            }
            //2.检查token是否重复
            boolean result = redisClient.setnx(TOKEN_REPEAT_PRE + jwtModel.getUuid(), "0", timeout);
            if (!result) {
                //添加缓存失败，证明缓存中已有该token，是重复token
                return AuthResult.create(AuthResult.Result.REPEAT);
            }
        } else if (type == AuthorizationEnum.URL_AUTHORIZATION) { //URL认证方式
            //检查token是否已经过期
            long urlTimeout = appUserConfig.getToken().getUrlTimeout() * 1000L;
            if (appService.nowTime() - jwtModel.getTime() > urlTimeout) {
                //token已经超时
                return AuthResult.create(AuthResult.Result.TIMEOUT);
            }
        }
        //检查缓存中的token是否合法
        TokenModel tokenModel = this.tokenModel(jwtModel);
        if (tokenModel == null) {
            return AuthResult.create(AuthResult.Result.FAIL);
        } else {
            return AuthResult.create(AuthResult.Result.SUCCESS).setTokenModel(tokenModel);
        }
    }

    //清除token
    @Override
    public boolean deleteToken(String token) {
        boolean result = true;
        TokenModel model = redisClient.get(TOKEN_PRE + token, TokenModel.class);
        if (model != null) {
            result = ThreadUtils.runThree(() -> redisClient.del(APPUSER_TOKEN + model.getAppUser().getId()));
        }
        //销毁token
        if (result) {
            result = ThreadUtils.runThree(() -> redisClient.del(TOKEN_PRE + token));
        }
        return result;
    }

    //更新token的用户信息
    @Override
    public boolean updateToken(AppUserModel appUser) {
        if (appUser == null) {
            return false;
        }
        String token = appUser.getToken();
        TokenModel model = redisClient.get(TOKEN_PRE + token, TokenModel.class);
        if (model == null) {
            return false;
        }
        //更新token里的appuser对象
        model.setAppUser(appUser);
        //存储到redis并设置过期时间
        int failTime = appUserConfig.getToken().getFailTime();
        return redisClient.set(TOKEN_PRE + token, model.toString(), failTime, TimeUnit.HOURS);
    }

    //对APP发送的token进行解码
    private String decode(String authentication) {
        if (authentication.endsWith(".")) {
            //去掉最后面的补位
            authentication = authentication.substring(0, authentication.indexOf("."));
        }
        try {
            //对 authentication 进行解码
            byte[] bytes = Base64.getUrlDecoder().decode(authentication.getBytes(CHARSET));
            authentication = new String(bytes, CHARSET);
        } catch (Exception e) {
            log.error("authentication解码失败，authentication=" + authentication);
        }
        return authentication;
    }

    //检查APP端发送的token是否合法
    private JWTModel jwtModel(String authentication) {
        if (authentication == null) {
            return null;
        }
        JWTModel jwtModel = null;
        try {
            jwtModel = JWTModel.toBean(authentication, JWTModel.class);
        } catch (Exception e) {
            log.error("token无效，authentication=" + authentication + ", " + e.getMessage(), e);
            return null;
        }
        //验证签名
        String sign = null;
        try {
            sign = this.sign(jwtModel.toMap());
        } catch (Exception e) {
            log.error("签名失败，" + e.getMessage(), e);
            return null;
        }
        if (!Objects.equals(sign, jwtModel.getSign())) {
            log.error("签名不一致，" + jwtModel.toString());
            return null;
        }
        return jwtModel;
    }

    //检查缓存中的token是否合法
    private TokenModel tokenModel(JWTModel jwtModel) {
        //从缓存中获取token信息
        String token = redisClient.get(TOKEN_PRE + jwtModel.getToken());
        if (token == null) {
            //缓存中没有该token，可能token已过期，或缓存服务异常
            return null;
        }
        TokenModel tokenModel;
        try {
            tokenModel = myGson.fromJson(token, TokenModel.class);
        } catch (Exception e) {
            log.error("解析token失败，token=" + token + ", " + e.getMessage(), e);
            return null;
        }
        //缓存中保存的token与APP发过来的token不一致
        if (!Objects.equals(tokenModel.getToken(), jwtModel.getToken())) {
            log.error("token校验异常，请检查是否被恶意攻击，tokenModel=" + tokenModel.toString() + ", jwt=" + jwtModel.toString());
            return null;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        int failTime = appUserConfig.getToken().getFailTime();
        boolean result = redisClient.expire(TOKEN_PRE + jwtModel.getToken(), failTime, TimeUnit.HOURS);
        if (!result) { //更新缓存失败，重试一次
            result = redisClient.expire(TOKEN_PRE + jwtModel.getToken(), failTime, TimeUnit.HOURS);
            if (!result) { //还是失败，返回token验证失败
                log.error("token缓存更新失败，请检查redis是否正常！");
            }
        }
        return tokenModel;
    }

    //对数据进行签名
    private String sign(Map<String, Object> map) throws Exception {
        JWTModel jwtModel = null;
        StringBuilder result = new StringBuilder();
        List<String> list = CollectionUtils.setToList(map.keySet());
        CollectionUtils.sort(list); // 对list进行排序
        for (String key : list) {
            if ("sign".equals(key)) {
                continue;
            }
            Object value = map.get(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            result.append(key + "=" + value + "&");
        }
        String beSign;
        if (result.length() > 1) {
            beSign = result.substring(0, result.length() - 1);
        } else {
            beSign = result.toString();
        }
        return MD5Util.sha256(beSign);
    }
}
