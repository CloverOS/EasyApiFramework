package cn.khthink.easyapi.redis;

import cn.khthink.easyapi.config.CoreConfig;
import cn.khthink.easyapi.kit.EasyLogger;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis工具
 *
 * @author kh
 */
public class EasyRedis {
    private JedisPool jedisPool;
    private boolean isAutoRelease = false;

    private static class Redis {
        private static EasyRedis instance = new EasyRedis();
    }

    private EasyRedis() {
    }

    public static EasyRedis getInstance() {
        return Redis.instance;
    }

    /**
     * 启动Redis连接池服务
     */
    public void init() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        jedisPool = new JedisPool(genericObjectPoolConfig, CoreConfig.redisAds);
        Jedis jedis = getJedis();
        EasyLogger.infowithgui("启动redis连接池 -->" + jedis.ping());
        releaseJedis(jedis);
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
     * 获取redis服务
     *
     * @return redis实例
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 获取redis服务
     *
     * @param isAutoRelease 是否自动释放
     * @return redis实例
     */
    public Jedis getJedis(boolean isAutoRelease) {
        this.isAutoRelease = isAutoRelease;
        return jedisPool.getResource();
    }

    /**
     * 设置键值
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

}
