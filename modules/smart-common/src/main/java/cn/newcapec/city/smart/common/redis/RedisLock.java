package cn.newcapec.city.smart.common.redis;

import cn.newcapec.city.smart.common.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis锁
 */
@Slf4j
@Component
public class RedisLock {

    private static final String LOCK_PREFIX = "datalock_"; //锁前缀
    private static final String LOCK_SUFFIX = "_datalock"; //锁后缀

    @Autowired
    private RedisClient redisClient;

    /**
     * 添加获取锁，如果获取失败则每隔10毫秒获取一次，在timeout时间内未获取到，则返回false，否则返回true
     *
     * @param key     加锁的键
     * @param reqId   客户端加锁标志
     * @param timeout 超时时间(单位：毫秒) ，超时时间务必设置，防止死锁，超时时间如果设置小于1000毫秒则强制改为1000毫秒
     * @return true 加锁成功   false 加锁失败
     */
    public boolean addLockWait(final String key, String reqId, final int timeout) {
        return addLockWait(key, reqId, timeout, timeout);
    }

    /**
     * 获取redis锁，如果获取失败则每隔10毫秒获取一次，在waitTime时间内未获取到，则返回false，否则返回true
     *
     * @param key      加锁的键
     * @param reqId    客户端加锁标志
     * @param timeout  锁超时时间(单位：毫秒) ，超时时间务必设置，防止死锁，超时时间如果设置小于1毫秒则强制改为1毫秒
     * @param waitTime 加锁操作等待超时时间(单位：毫秒)，如果锁被占用，等待多久获取锁，达到该时间还未获取锁则返回false
     * @return true成功获得锁  false获得锁超时
     */
    public boolean addLockWait(String key, String reqId, int timeout, int waitTime) {
        return addLockWait(key, reqId, timeout, waitTime, 10);
    }

    /**
     * 获取redis锁，如果获取失败则每隔offtime毫秒获取一次，
     * 在waitTime时间内未获取到，则返回false，否则返回true
     *
     * @param key                                                     加锁的键
     * @param reqId：必填，值，解锁时传入该值(要保证唯一性，建议用UUID)，判断加锁、解锁是否是同一个调用者
     * @param timeout：超时时间(单位：毫秒)，超时时间务必设置，防止死锁，超时时间如果设置小于1毫秒则强制改为1毫秒
     * @param timeout                                                 锁超时时间(单位：毫秒) ，超时时间务必设置，防止死锁，超时时间如果设置小于1毫秒则强制改为1毫秒
     * @param waitTime                                                加锁操作等待超时时间(单位：毫秒)，如果锁被占用，等待多久获取锁，达到该时间还未获取锁则返回false
     * @param offTime                                                 锁自旋频率(单位：毫秒)，即在waitTime时间内，隔多长时间尝试获取锁
     * @return true 加锁成功   false 加锁失败
     */
    public boolean addLockWait(String key, String reqId, int timeout, int waitTime, int offTime) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        boolean result = ThreadUtils.threadWait(() -> !addLock(key, reqId, timeout), offTime, waitTime);
        if (result) { //获取超时
            return false;
        }
        return true;
    }

    /**
     * 添加获取锁
     *
     * @param key:必填，键
     * @param reqid：必填，值，解锁时传入该值(要保证唯一性，建议用UUID)，判断加锁、解锁是否是同一个调用者
     * @param timeout：超时时间(单位：毫秒)，超时时间务必设置，防止死锁，超时时间如果设置小于1毫秒则强制改为1毫秒
     * @return true 加锁成功   false 加锁失败
     */
    public boolean addLock(String key, String reqid, int timeout) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        if (StringUtils.isEmpty(reqid)) {
            return false;
        }
        if (timeout < 1) {
            timeout = 1;
        }
        if (redisClient.addLock(LOCK_PREFIX + key + LOCK_SUFFIX, reqid, timeout)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param key   必填，需要解锁的key
     * @param reqid 必填，客户端加锁标志
     * @return
     */
    public boolean deleteLock(String key, String reqid) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        if (StringUtils.isEmpty(reqid)) {
            return false;
        }
        return redisClient.delLock(LOCK_PREFIX + key + LOCK_SUFFIX, reqid);
    }

}
