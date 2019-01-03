package cn.newcapec.city.smart.gateway.authorization.manager;

import cn.newcapec.city.smart.gateway.authorization.annotation.AuthorizationEnum;
import cn.newcapec.city.smart.gateway.authorization.model.AuthResult;
import cn.newcapec.city.smart.gateway.authorization.model.TokenModel;
import cn.newcapec.city.smart.model.AppUserModel;

/**
 * 对Token进行操作的接口
 *
 * @author ScienJus
 * @date 2015/7/31.
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     *
     * @param appUserDto APP用户对象
     * @return 生成的token
     */
    TokenModel createToken(AppUserModel appUserDto);

    /**
     * 检查token是否合法
     *
     * @param type           token验证类型
     * @param authentication 要验证的token信息
     * @return 返回null说明token不合法
     */
    AuthResult checkToken(AuthorizationEnum type, String authentication);

    /**
     * 清除token
     *
     * @param token 登录用户的token
     */
    boolean deleteToken(String token);

    /**
     * 更新token的用户信息
     *
     * @param appUserDto
     * @return
     */
    boolean updateToken(AppUserModel appUserDto);

}
