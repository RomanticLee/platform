package cn.newcapec.city.smart.gateway.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by es on 2017/7/12.
 */
@Order(value = 0)
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获得指定类型的类
     *
     * @param var1
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> var1) {
        Map<String, T> beansWithType = applicationContext.getBeansOfType(var1);
        if (beansWithType == null) {
            return new HashMap<String, T>();
        } else {
            return beansWithType;
        }
    }

    /**
     * 获得有指定注解的类
     *
     * @param var1
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> var1) {
        Map<String, Object> beansWithAnnotationMap = applicationContext.getBeansWithAnnotation(var1);
        if (beansWithAnnotationMap == null) {
            return new HashMap<String, Object>();
        } else {
            return beansWithAnnotationMap;
        }
    }

    /**
     * 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

}
