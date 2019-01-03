package cn.newcapec.city.smart.common.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存管理
 */
@Slf4j
@Component
public class RedisClient {

    private static final Gson myGson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String COMPARE_AND_DELETE =
            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call('del',KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    private final String cacle_pre = "qrcode_";
    @Autowired
    private StringRedisTemplate redis;

    public RedisSerializer<String> getStringSerializer() {
        return redis.getStringSerializer();
    }

    /**
     * 将 key 的值设为 value
     *
     * @param key
     * @param value
     */
    public boolean set(String key, String value) {
        try {
            redis.opsForValue().set(cacle_pre + key, value);
            return true;
        } catch (Exception e) {
            log.error("[RedisClient.set(key,value)]设置缓存失败，key=" + cacle_pre + key + ",value=" + value + ", " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将 key 的值设为 value，并设置超时时间
     *
     * @param key
     * @param value
     * @param outTime 超时时间，单位毫秒
     * @return
     */
    public boolean set(String key, String value, long outTime) {
        return this.set(key, value, outTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 将 key 的值设为 value，并设置超时时间
     *
     * @param key
     * @param value
     * @param outTime
     * @param unit
     * @return
     */
    public boolean set(String key, String value, long outTime, TimeUnit unit) {
        try {
            redis.opsForValue().set(cacle_pre + key, value, outTime, unit);
            return true;
        } catch (Exception e) {
            log.error("[RedisClient.set(key,value,outTime,unit)]设置缓存失败，key=" + cacle_pre + key + ",value=" + value + ", " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 设置缓存的失效时间(单位：毫秒)
     *
     * @param key
     * @param outTime
     * @return
     */
    public boolean expire(String key, long outTime) {
        return this.expire(cacle_pre + key, outTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置缓存的失效时间
     *
     * @param key
     * @param outTime
     * @param unit
     * @return
     */
    public boolean expire(String key, long outTime, TimeUnit unit) {
        try {
            return redis.expire(cacle_pre + key, outTime, unit);
        } catch (Exception e) {
            log.error("[RedisClient.expire(key,value,outTime)]设置缓存失效时间失败，key=" + cacle_pre + key + ",outTime=" + outTime + ", " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在时。
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setnx(final String key, final String value) {
        try {
            return redis.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    RedisSerializer<String> redisSerializer = redis.getStringSerializer();
                    byte[] k = redisSerializer.serialize(cacle_pre + key);
                    byte[] v = redisSerializer.serialize(value);
                    return redisConnection.setNX(k, v);
                }
            });
        } catch (Exception e) {
            log.error("[RedisClient.setnx(key,value)]设置缓存失败，key=" + cacle_pre + key + ",value=" + value + ", " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在时。
     *
     * @param key
     * @param value
     * @param seconds 过期时间，单位：毫秒
     * @return
     */
    public boolean setnx(final String key, final String value, final long seconds) {
        try {
            return redis.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    RedisSerializer<String> redisSerializer = redis.getStringSerializer();
                    byte[] k = redisSerializer.serialize(cacle_pre + key);
                    byte[] v = redisSerializer.serialize(value);
                    Expiration expiration = Expiration.from(seconds, TimeUnit.MILLISECONDS);
                    RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.SET_IF_ABSENT;
                    return redisConnection.set(k, v, expiration, setOption);
                }
            });
        } catch (Exception e) {
            log.error("[RedisClient.setnx(key,value,seconds)]设置缓存失败，key=" + cacle_pre + key + ",value=" + value + ", " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据key获取缓存value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            return redis.opsForValue().get(cacle_pre + key);
        } catch (Exception e) {
            log.error("[RedisClient.get()]获得缓存失败，key=" + cacle_pre + key + ", " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据key获取缓存value
     *
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> tClass) {
        try {
            String result = redis.opsForValue().get(cacle_pre + key);
            if (!StringUtils.isEmpty(result)) {
                T t = myGson.fromJson(result, tClass);
                return t;
            }
        } catch (Exception e) {
            log.error("[RedisClient.get()]获得缓存失败，key=" + cacle_pre + key + ", " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据key删除
     *
     * @param key
     * @return
     */
    public boolean del(String key) {
        try {
            return redis.delete(cacle_pre + key);
        } catch (Exception e) {
            log.error("[RedisClient.del()]删除缓存失败，key=" + cacle_pre + key + ", " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 添加锁
     *
     * @param key          要添加的锁
     * @param reqid        加锁的客户端标志
     * @param milliseconds 超时时间，单位：毫秒
     * @return
     */
    public boolean addLock(String key, String reqid, int milliseconds) {
        return this.setnx(key, reqid, milliseconds);
    }

    /**
     * 删除锁
     *
     * @param key   要删除的锁
     * @param reqid 加锁的客户端标志
     * @return
     */
    public boolean delLock(String key, String reqid) {
        try {
            //使用Lua脚本删除锁，保证锁的删除操作的原子性
            List<String> keys = Collections.singletonList(cacle_pre + key);
            Long result = redis.execute(new DefaultRedisScript<Long>(COMPARE_AND_DELETE, Long.class), keys, reqid);
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("[RedisClient.delLock()]删除缓存锁失败，key=" + cacle_pre + key + ",reqid=" + reqid + ", " + e.getMessage(), e);
        }
        return false;
    }

    //region redis列表操作

    /**
     * 往列表右侧添加消息
     *
     * @param key
     * @param expireTime 失效时间，单位毫秒
     * @param values
     * @return
     */
    public boolean rpush(String key, long expireTime, String... values) {
        try {
            long result = redis.opsForList().rightPushAll(cacle_pre + key, values);
            if (result > 0) {
                boolean b = redis.expire(cacle_pre + key, expireTime, TimeUnit.MILLISECONDS);
                return b;
            }
        } catch (Exception e) {
            log.error("[RedisClient.rpush()]往列表右侧添加消息失败，key=" + cacle_pre + key + ",values=" + values + ", " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 移除列表左侧的元素并返回
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        try {
            return redis.opsForList().leftPop(cacle_pre + key);
        } catch (Exception e) {
            log.error("[RedisClient.lpop()]移除列表左侧的元素失败，key=" + cacle_pre + key + ", " + e.getMessage(), e);
        }
        return null;
    }
    //endregion

}
