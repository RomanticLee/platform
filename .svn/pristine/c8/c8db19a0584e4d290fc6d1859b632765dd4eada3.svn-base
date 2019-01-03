package cn.newcapec.city.smart.aspect;

import cn.newcapec.city.smart.annotation.RedisLockAnnotation;
import cn.newcapec.city.smart.common.redis.RedisLock;
import cn.newcapec.city.smart.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis锁切入点
 * Created by es on 2018/12/26.
 */
@Slf4j
@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private RedisLock redisLock;

    //定义一个切入点
    @Pointcut("@annotation(cn.newcapec.city.smart.annotation.RedisLockAnnotation)")
    public void redisLockAnnotation() {
    }

    ///括号中的 annotation 并不是指所有自定标签，而是指在你的注释实现类中对应注解对象的别名
    @Around(value = "redisLockAnnotation()&&@annotation(annotation)")
    public Object twiceAsOld(ProceedingJoinPoint joinPoint, RedisLockAnnotation annotation) throws Throwable {
        boolean isLock = false;
        String reqid = Utils.getUUIDShort();
        //环绕通知必须执行，否则不进入注解的方法
        try {
            isLock = redisLock.addLockWait(annotation.key(), reqid, annotation.timeOut(), annotation.waitTime(), annotation.frequency());
            if (isLock) {
                //加锁成功，执行逻辑
                Object result = joinPoint.proceed();
                return result;
            }
            return null;
        } finally {
            if (isLock) { //移除锁
                redisLock.deleteLock(annotation.key(), reqid);
            }
        }
    }

}
