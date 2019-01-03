package cn.newcapec.city.smart.gateway.service;

import cn.newcapec.city.smart.icall.IReturnCallback;
import cn.newcapec.city.smart.icall.IVoidCallback;
import cn.newcapec.city.smart.common.redis.RedisLock;
import cn.newcapec.city.smart.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 服务业务基类
 * Created by es on 2018/3/13.
 */
@Slf4j
public abstract class BaseService {

    @Value("${application.user.pageSize}")
    protected int pageSize; //查询数据每页的大小
    @Autowired
    private RedisLock redisLock;

    public String getParams(String name) {
        HttpServletRequest request = getRequest();
        return getParams(request, name);
    }

    public String getParams(ServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        Object obj = request.getAttribute(name);
        if (obj == null) {
            obj = request.getParameter(name);
            if (obj == null) {
                return null;
            }
        }
        String result = "";
        try {
            result = new String(obj.toString().getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            log.error("[BaseService.getParams]参数编码转换异常，request.name=" + name
                    + ", remoteAddr=" + request.getRemoteAddr() + ", error=" + e.getMessage());
        }
        return result;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * redis锁
     * @param lockKey
     * @param timeout
     * @param waitTime
     * @param callback
     * @return
     */
    protected boolean redisLockCall(String lockKey, int timeout, final int waitTime, IVoidCallback callback){
        boolean isLock = false;
        String reqid = Utils.getUUIDShort();
        try {
            isLock = redisLock.addLockWait(lockKey, reqid, timeout, waitTime);
            //加锁失败，直接返回
            if (!isLock) {
                return false;
            }
            if(callback != null){
                callback.callback();
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("处理及时统计异常失败，" + e.getMessage(), e);
            return false;
        } finally {
            if (isLock) { //移除锁
                redisLock.deleteLock(lockKey, reqid);
            }
        }
    }

    /**
     * redis锁
     * @param lockKey
     * @param timeout
     * @param waitTime
     * @param callback
     * @param <T>
     * @return
     */
    protected <T> T redisLockCall(String lockKey, int timeout, final int waitTime, IReturnCallback<T> callback){
        boolean isLock = false;
        String reqid = Utils.getUUIDShort();
        try {
            isLock = redisLock.addLockWait(lockKey, reqid, timeout, waitTime);
            //加锁失败，直接返回
            if (!isLock) {
                return null;
            }
            if(callback != null){
                return callback.callback();
            }
            return null;
        } catch (Exception e) {
            log.error("处理及时统计异常失败，" + e.getMessage(), e);
            return null;
        } finally {
            if (isLock) { //移除锁
                redisLock.deleteLock(lockKey, reqid);
            }
        }
    }

}
