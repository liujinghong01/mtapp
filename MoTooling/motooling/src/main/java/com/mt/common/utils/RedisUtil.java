package com.mt.common.utils;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * Redis 工具类
 * 
 * @author caspar
 * 
 */
public class RedisUtil {

	public static final ResourceBundle REDIS = ResourceBundle.getBundle("config/redis");

	protected static Logger logger = Logger.getLogger(RedisUtil.class);

	private static JedisPool jedisPool =null;
	private static Integer port =Integer.valueOf(REDIS.getString("redis.port"));
	private static String host =REDIS.getString("redis.host");

	/**
	 * 初始化Redis连接池
	 */
	private static void initialPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(1000);
			config.setMaxIdle(100);
			jedisPool = new JedisPool(config, host, port, 60000,"M0w0rkMT00ling20!&");
			logger.info("-----------------初始化redis连接池成功！！！！！！！！！！！！！" + jedisPool);
		} catch (Exception e) {
			logger.error("First create JedisPool error : " + e);
		}
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static void poolInit() {
		try {
			if (jedisPool == null) {
				initialPool();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Jedis getJedis() {
		if (jedisPool == null) {
			poolInit();
		}
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
				logger.info("获取到jedis-----------------------" + jedis);
			}
		} catch (Exception e) {
			logger.error("Get jedis error : " + e);
		}
		return jedis;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null && jedisPool != null) {
			// jedisPool.returnResource(jedis);
			jedis.close();
			logger.info("--------------------------释放jedis成功--------------------" + jedis);
		}
	}

	/**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("Set key error : " + e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 设置 过期时间
	 * 
	 * @param key
	 * @param seconds
	 *            以秒为单位
	 * @param value
	 */
	public static void setString(String key, int seconds, String value) {
		Jedis jedis = getJedis();
		try {
			getJedis().setex(key, seconds, value);
		} catch (Exception e) {
			logger.error("Set keyex error : " + e);
		} finally {
			returnResource(jedis);
		}
	}


	public static void setCollectionExpire(String key, int seconds) {
		Jedis jedis = getJedis();
		try {
			getJedis().expire(key, seconds);
		} catch (Exception e) {
			logger.error("Set keyex error : " + e);
		} finally {
			returnResource(jedis);
		}
	}


	/**
	 * 获取String值
	 * 
	 * @param key
	 * @return value
	 */
	public static String getString(String key) {
		Jedis jedis = getJedis();
		try {
			if (jedis == null || !jedis.exists(key)) {
				return null;
			}
		} finally {
			returnResource(jedis);
		}
		return jedis.get(key);
	}

	/**
	 * 获取String值
	 *
	 * @param key
	 * @return value
	 */
	public static  Long delString(String key) {
		Jedis jedis = getJedis();
		try {
			if (jedis == null || !jedis.exists(key)) {
				return null;
			}
		} finally {
			returnResource(jedis);
		}
		return jedis.del(key);
	}

	/**
	 * 获取map 中 key 值
	 *
	 * @param key
	 * @return value
	 */
	public static String getMapString(String key, String fieId) {
		Jedis jedis = getJedis();
		try {
			if (jedis == null || !jedis.exists(key) || !jedis.hexists(key, fieId)) {
				return null;
			}
		} finally {
			returnResource(jedis);
		}
		return jedis.hget(key, fieId);
	}


	/**
	 * 设置map 中 key 值
	 *
	 * @param key
	 * @return value
	 */
	public static void setMapString(String key, String fieId, String value) {
		Jedis jedis = getJedis();
		try {
			jedis.hset(key, fieId, value);
		} catch (Exception e) {
			logger.error("Set key error : " + e);
		} finally {
			returnResource(jedis);
		}
	}


	/**
	 * 指定一个参数 motoolingComputeNo
	 * 生成流水号. fleId 是redis中 map的key
	 *
	 * @param fleId
	 * @return
	 */
	public static String motoolingComputeNoAdd(String key, String fleId, String prefixName) {
		String date = DateUtil.dateToString(new Date(), DateUtil.ISO_DATE_FORMAT);
		StringBuilder replace = null;
		try {
			if (RedisUtil.getMapString("motoolingComputeNo", fleId) == null) {
				RedisUtil.setMapString("motoolingComputeNo", fleId, prefixName + date + "000001");
				return prefixName + date + "000001";
			} else {
				String param = RedisUtil.getMapString("motoolingComputeNo", fleId);
				String substring = param.substring(1, param.length());
				replace = new StringBuilder(substring).replace(0, 8, date);
				RedisUtil.setMapString("motoolingComputeNo", fleId, prefixName + (Long.valueOf(replace.toString()) + 1L));
				return prefixName + (Long.valueOf(replace.toString()) + 1L);
			}
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
		return null;
	}


	public static void main(String[] args) {

		String s = motoolingComputeNoAdd("motoolingComputeNo", "1", "H");
		System.out.println(s);


	}
}
