package cn.newcapec.city.smart.gateway.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口结果返回例外处理(即不返回标准格式)
 * Created by es on 2018/5/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface ExclusionAnnotation {

    //是否例外处理，默认true,例外处理
    boolean isExclusion() default true;
}
