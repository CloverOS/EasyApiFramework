package cn.khthink.easyapi.redis;

import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.kit.EasyLogger;
import com.alibaba.fastjson.JSON;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

import java.util.Hashtable;
import java.util.Map;

/**
 * Redis工具
 *
 * @author kh
 */
public class EasyRedis {
    /**
     * 是否自动释放Jedis客户端
     */
    private boolean isAutoRelease = true;
    private Map<Integer, JedisPool> jedisPoolMap = new Hashtable<>();

    private static class Redis {
        private static EasyRedis instance = new EasyRedis();
    }

    public static String NX = "NX";
    public static String XX = "XX";
    public static String EX = "EX";
    public static String PX = "PX";

    private EasyRedis() {
    }

    public static EasyRedis getInstance() {
        return Redis.instance;
    }

    /**
     * 初始化Redis服务
     *
     * @param database 分块
     */
    public synchronized void init(int... database) {
        EasyLogger.info("Redis服务初始化--->");
        if (database.length > 0) {
            for (int i : database) {
                JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), CoreConfig.redisAds,
                        CoreConfig.redisPort, Protocol.DEFAULT_TIMEOUT, CoreConfig.redisPasswd,
                        i, null);
                Jedis resource = jedisPool.getResource();
                EasyLogger.info("Redis--->连接" + i + ":" + resource.ping());
                releaseJedis(resource);
                jedisPoolMap.put(i, jedisPool);
            }
        } else {
            JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), CoreConfig.redisAds,
                    CoreConfig.redisPort, Protocol.DEFAULT_TIMEOUT, CoreConfig.redisPasswd,
                    Protocol.DEFAULT_DATABASE, null);
            Jedis resource = jedisPool.getResource();
            EasyLogger.info("Redis--->连接" + 0 + ":" + resource.ping());
            releaseJedis(resource);
            jedisPoolMap.put(0, jedisPool);
        }
        EasyLogger.info("Redis服务初始化--->完毕");
    }

    /**
     * 获取Jedis连接池
     *
     * @param database 分块
     * @return JedisPool
     */
    public JedisPool getJedisPool(int database) {
        return jedisPoolMap.getOrDefault(database, getJedisPool(Protocol.DEFAULT_PORT));
    }

    /**
     * 获取默认Jedis连接池
     *
     * @return JedisPool
     */
    public JedisPool getDefaultJedisPool() {
        return getJedisPool(Protocol.DEFAULT_DATABASE);
    }

    /**
     * 获取对应数据库Jedis客户端
     *
     * @param database 分块
     * @return Jedis
     */
    public Jedis getJedis(int database) {
        return getJedisPool(database).getResource();
    }

    /**
     * 获取对应数据库Jedis客户端
     *
     * @param database      分块
     * @param isAutoRelease 是否自动释放资源
     * @return Jedis
     */
    public Jedis getJedis(int database, boolean isAutoRelease) {
        this.isAutoRelease = isAutoRelease;
        return getJedisPool(database).getResource();
    }

    /**
     * 获取默认数据库Jedis客户端
     *
     * @return Jedis
     */
    public Jedis getDefaultJedis() {
        return getJedis(Protocol.DEFAULT_DATABASE);
    }

    /**
     * 获取默认数据库Jedis客户端
     *
     * @param isAutoRelease 是否自动释放资源
     * @return Jedis
     */
    public Jedis getDefaultJedis(boolean isAutoRelease) {
        return getJedis(Protocol.DEFAULT_DATABASE, isAutoRelease);
    }

    /**
     * 释放redis
     *
     * @param jedis jedis实例
     */
    public void releaseJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设置键值(无过期时间)
     *
     * @param jedis jedis实例
     * @param key   键
     * @param value 值
     */
    public void set(Jedis jedis, String key, String value) {
        jedis.set(key, value);
        if (isAutoRelease) {
            releaseJedis(jedis);
        }
    }

    /**
     * 设置键值并设置过期时间
     *
     * @param jedis jedis实例
     * @param key   键
     * @param value 值
     * @param nxxx  NX|XX, "NX" 只有键不存在的时候才设置
     *              "XX" 只有在键存在的时候才设置
     * @param expx  EX|PX 时间单位 "EX" = 秒; "PX" = 毫秒
     * @param time  根据<code>expx</code>单位设置的时间
     */
    public void set(Jedis jedis, String key, String value, String nxxx, String expx, long time) {
        jedis.set(key, value, nxxx, expx, time);
        if (isAutoRelease) {
            releaseJedis(jedis);
        }
    }

    /**
     * 获取对应的值的对象
     *
     * @param jedis jedis实例
     * @param key   键
     * @param type  值类型
     * @param <T>   类型
     * @return ObjectT
     */
    public <T> T getObject(Jedis jedis, String key, Class<T> type) {
        String s = getValue(jedis, key);
        if (s != null && !s.isEmpty()) {
            return JSON.parseObject(s, type);
        }
        return null;
    }

    /**
     * 获取对应值的字符串
     *
     * @param jedis jedis实例
     * @param key   键
     * @return String
     */
    public String getValue(Jedis jedis, String key) {
        String s = jedis.get(key);
        if (isAutoRelease) {
            releaseJedis(jedis);
        }
        return s;
    }

    /**
     * 是否存在键
     *
     * @param jedis jedis实例
     * @param key   键
     * @return boolean
     */
    public boolean isExists(Jedis jedis, String key) {
        boolean exists = jedis.exists(key);
        if (isAutoRelease) {
            releaseJedis(jedis);
        }
        return exists;
    }

    /**
     * 插入列表
     *
     * @param jedis 实例
     * @param key   键
     * @param param 值
     */
    public void lpush(Jedis jedis, String key, String... param) {
        jedis.lpush(key, param);
        if (isAutoRelease) {
            releaseJedis(jedis);
        }
    }

    /**
     * 删除键值对
     *
     * @param jedis 实例
     * @param key   键
     * @return 是否删除
     */
    public boolean del(Jedis jedis, String key) {
        Long del = jedis.del(key);
        if (isAutoRelease) {
            releaseJedis(jedis);
        }
        return del > 0;
    }
}
