package cn.newcapec.city.smart.gateway.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Controller的类或方法上使用此注解，
 * 应用于类上时，该类的所有方法在映射时将检查用户是否登录；
 * 应用于方法上时，该方法在映射时会检查用户是否登录；
 * 当类和方法上均有注解时，方法上的注解生效
 *
 * @author WEIXING
 * @date 2018年6月28日
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AppAuthorization {

    //认证方式，默认进行APP用户认证
    AuthorizationEnum authorization() default AuthorizationEnum.APP_AUTHORIZATION;

}
