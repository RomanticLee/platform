package cn.newcapec.city.smart.gateway.authorization.interceptor;

import cn.newcapec.city.smart.common.utils.AopTargetUtils;
import cn.newcapec.city.smart.gateway.authorization.annotation.AppAuthorization;
import cn.newcapec.city.smart.gateway.authorization.annotation.AuthorizationEnum;
import cn.newcapec.city.smart.gateway.authorization.manager.TokenManager;
import cn.newcapec.city.smart.gateway.authorization.model.AuthResult;
import cn.newcapec.city.smart.gateway.authorization.model.TokenModel;
import cn.newcapec.city.smart.gateway.exception.IRet;
import cn.newcapec.city.smart.gateway.exception.SysException;
import cn.newcapec.city.smart.gateway.utils.Constants;
import cn.newcapec.city.smart.model.AppUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 *
 * @author ScienJus
 * @date 2015/7/30.
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AppAuthorization appAuthorization;
        if (method.isAnnotationPresent(AppAuthorization.class)) {
            //获得该函数上的认证信息
            appAuthorization = method.getAnnotation(AppAuthorization.class);
        } else { //该函数上没有认证信息
            //获得当前的类对象(防止获取到的是代理类)
            Object target = AopTargetUtils.getTarget(handlerMethod.getBean());
            if (target.getClass().isAnnotationPresent(AppAuthorization.class)) {
                //获得当前函数所在类上的认证信息
                appAuthorization = target.getClass().getAnnotation(AppAuthorization.class);
            } else { //该函数所在的类上也没有认证信息
                return true;
            }
        }
        //忽略认证
        if (appAuthorization.authorization() == AuthorizationEnum.NOT_AUTHORIZATION) {
            return true;
        }
        //APP认证方式
        if (appAuthorization.authorization() == AuthorizationEnum.APP_AUTHORIZATION) {
            return appAuthorization(request);
        }
        //url Token认证方式
        if (appAuthorization.authorization() == AuthorizationEnum.URL_AUTHORIZATION) {
            return urlAuthorization(request);
        }
        //其他未知认证方式，默认不认证
        return true;
    }

    //APP认证方式
    private boolean appAuthorization(HttpServletRequest request) {
        //从header中得到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        //检查token是否合法
        AuthResult authResult = manager.checkToken(AuthorizationEnum.APP_AUTHORIZATION, authorization);
        return this.authorization(authResult, request);
    }

    //url Token认证方式
    private boolean urlAuthorization(HttpServletRequest request) {
        //从参数中获取token
        String authorization = request.getParameter(Constants.AUTHORIZATION);
        //检查token是否合法
        AuthResult authResult = manager.checkToken(AuthorizationEnum.URL_AUTHORIZATION, authorization);
        return this.authorization(authResult, request);
    }

    //通用的认证方式
    private boolean authorization(AuthResult authResult, HttpServletRequest request) {
        if (authResult.getResult() == AuthResult.Result.SUCCESS) {
            //验证通过，获得token里保存的用户数据
            TokenModel tokenModel = authResult.getTokenModel();
            AppUserModel appUser = tokenModel.getAppUser();
            //如果token验证成功，将token对应的用户存入request中，便于之后注入
            request.setAttribute(Constants.CURRENT_USER, appUser);
            //将商户编号存入request中
            request.setAttribute(Constants.MERCHANTCODE, appUser.getMerchantcode());
            //将设备编号存入request中
            request.setAttribute(Constants.DEVICEID, appUser.getDeviceId());
            return true;
        }
        if (authResult.getResult() == AuthResult.Result.REPEAT) {
            //token重复
            throw new SysException(IRet.Code.AUTHORIZATION_REPEAT);
        }
        if (authResult.getResult() == AuthResult.Result.TIMEOUT) {
            //token超时
            throw new SysException(IRet.Code.AUTHORIZATION_TIMEOUT);
        }
        //认证失败，返回认证错误信息
        throw new SysException(IRet.Code.INVALIDITY_AUTHORIZATION);
    }

}
