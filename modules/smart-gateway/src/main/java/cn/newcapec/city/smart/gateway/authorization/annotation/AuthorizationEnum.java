package cn.newcapec.city.smart.gateway.authorization.annotation;

/**
 * 认证方式
 * Created by es on 2018/6/28.
 */
public enum AuthorizationEnum {

    APP_AUTHORIZATION, //进行APP用户认证
    URL_AUTHORIZATION, //进行URL Token认证
    NOT_AUTHORIZATION //不进行认证
    ;

}
