package cn.newcapec.city.smart.aspect;

import cn.newcapec.city.smart.annotation.TimingAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 函数操作计时切入点
 * Created by es on 2018/5/8.
 */
@Slf4j
@Aspect
@Component
public class TimingAspect {

    //定义一个切入点
    @Pointcut("@annotation(cn.newcapec.city.smart.annotation.TimingAnnotation)")
    public void timingAnnotation() {
    }

    ///括号中的 annotation 并不是指所有自定标签，而是指在你的注释实现类中对应注解对象的别名
    @Around(value = "timingAnnotation()&&@annotation(annotation)")
    public Object twiceAsOld(ProceedingJoinPoint joinPoint, TimingAnnotation annotation) throws Throwable {
        long start = System.currentTimeMillis();
        //环绕通知必须执行，否则不进入注解的方法
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            log.debug(" =========== "
                    + joinPoint.getSignature().getDeclaringTypeName()
                    + "." + joinPoint.getSignature().getName()
                    + ": " + annotation.value() + " 执行耗时: "
                    + (System.currentTimeMillis() - start)
                    + "ms =========== ");
        }
    }

}
