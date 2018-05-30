package com.mr.redis;

public interface JedisClient {

	/**
	 * string类型赋值
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key, String value);

	/**
	 * string类型取值
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * String类型删除
	 * @param key
	 * @return
	 */
	Long del(String key);

	/**
	 * 判断key判断存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 设置过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key, int seconds);

	/**
	 * 查看获取时间
	 * @param key
	 * @return
	 */
	Long ttl(String key);

	/**
	 * +1
	 * @param key
	 * @return
	 */
	Long incr(String key);

	/**
	 * hash类型赋值
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);

	/**
	 * hash类型取值
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);

	/**
	 * hash类型删除
	 * @param key
	 * @param field
	 * @return
	 */
	Long hdel(String key, String... field);
}
