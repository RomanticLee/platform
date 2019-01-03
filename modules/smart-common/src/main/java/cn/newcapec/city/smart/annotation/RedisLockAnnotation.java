package cn.newcapec.city.smart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加redis锁的注解
 * Created by es on 2018/12/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisLockAnnotation {

    /**
     * 加锁的key，请保证全局唯一
     *
     * @return
     */
    String key();

    /**
     * 锁超时时间，单位毫秒
     *
     * @return
     */
    int timeOut() default 30000;

    /**
     * 加锁最长等待时间，单位毫秒；超过该时间则加锁失败
     *
     * @return
     */
    int waitTime() default 3000;

    /**
     * 锁自旋频率，单位毫秒
     *
     * @return
     */
    int frequency() default 10;

}
