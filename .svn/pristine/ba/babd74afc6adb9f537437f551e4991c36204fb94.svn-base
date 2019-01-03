package cn.newcapec.city.smart.gateway.spring;

import cn.newcapec.city.smart.gateway.exception.IRet;
import cn.newcapec.city.smart.gateway.model.Msg;
import cn.newcapec.city.smart.gateway.spring.annotation.ExclusionAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * 用于预处理响应
 * Created by es on 2018/3/13.
 */
@Slf4j
@ControllerAdvice
public class DataResponseBodyAdvice implements ResponseBodyAdvice {

    //项目的包路径
    @Value("${application.project.package}")
    private String projectPackage;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        //获得接口函数对象
        Method method = methodParameter.getMethod();
        //判断该接口函数上是否有例外处理的注解
        boolean annotationPresent = method.isAnnotationPresent(ExclusionAnnotation.class);
        if (annotationPresent) { //有该注解
            ExclusionAnnotation respException = method.getDeclaredAnnotation(ExclusionAnnotation.class);
            if (respException.isExclusion()) {
                return false; //返回false，例外处理，不进入通用的处理逻辑
            }
        }
        //方法定义所在的类
        Class<?> declaringClass = method.getDeclaringClass();
        //获得类的规范名称
        String className = declaringClass.getCanonicalName();
        //当前项目的接口方法加上通用的处理逻辑
        if (className.startsWith(projectPackage)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //处理异常，可以再添加一个异常处理的类，用于处理异常返回格式
        Msg msg = new Msg();
        if (methodParameter.hasMethodAnnotation(ExceptionHandler.class)) {
            msg.setCode(IRet.Code.FAIL.getCode());
            msg.setMsg(IRet.Code.FAIL.getMessage());
        } else {
            msg.setCode(IRet.Code.SUCCESS.getCode());
            msg.setMsg(IRet.Code.SUCCESS.getCode());
        }
        msg.setData(body);
        log.debug("返回数据: " + msg.toString());
        if (body instanceof String) {
            return msg.toString();
        } else {
            return msg;
        }
    }
}
