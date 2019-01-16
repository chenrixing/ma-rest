package com.ma.rest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JedisClient {
	
	/**
	 * 获取单个缓存对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 获取单个缓存对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @return
	 */
	byte[] get(byte[] key);
	/**
	 * 设置缓存对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, String value);
	
	boolean set(String key, int second, String value);
	
	/**
	 * 设置缓存对象
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public boolean set(String key, String value, int seconds);
	
	/**
	 * 设置缓存对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @param value
	 * @return
	 */
	String set(byte[] key, byte[] value);

    /**
     * 将key设置值为value，如果key不存在，这种情况下等同于{@link #set(String, String)}。当key存在时，什么也不做。
     * 
     * @return 1：如果key被设置了。0：如果key没有被设置。
     */
    Long setIfNotExists(String key, String value);

	/**
	 * 获取hash对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param hkey
	 * @param key
	 * @return
	 */
	String hget(String hkey, String key);
	/**
	 * 根据hash-key获取所有对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param hkey
	 * @return
	 */
	Map<String, String> hgetall(String hkey);

    /**
     * 判断key是否存在
     */
    boolean exists(String key);

	/**
	 * 设置hash对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param hkey
	 * @param key
	 * @param value
	 * @return long
	 */
	long hset(String hkey, String key, String value);

    /**
     * 增加key所指定的Hash中指定字段的值。<br/>
     * 如果key不存在，则创建一个新的HASH。如果字段不存在，则执行操作前会将该字段设置为0。
     * 
     * @param key hash名
     * @param field 字段名
     * @param value 递增(减)值
     * @return 操作后该字段的值
     */
    Long hincrBy(String key, String field, long value);

	/**
	 * 自增
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @return long
	 */
	long incr(String key);

    /**
     * 将key对应的数字加increment。如果key不存在，操作之前，key就会被置为0。如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。
     * 
     * @see #incr(String)
     * @return 增加之后的value值。
     */
    Long incr(String key, long increment);

	/**
	 * 自减
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @return long
	 */
	long decr(String key);
	/**
	 * 设置有效期
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @param second
	 * @return long
	 */
	long expire(String key, int second);

    /**
     * 该方法和{@link #expire(String, int)}有着相同的作用，但是该方法并不是指定一个秒数来表示TTL。相反，该方法需要传入一个绝对的<a
     * href=
     * "http://baike.baidu.com/item/unix%E6%97%B6%E9%97%B4%E6%88%B3/2078227">Unix
     * timestamp</a>(1970年1月1日以来的<b>秒</b>数)。指定一个过去的时间戳将直接删除该key。
     * 
     * @param key key
     * @param unixTime absolute Unix timestamp (<b>seconds</b> since January 1,
     *            1970)
     * @return 返回1：timeout已设置；返回0：key不存在或无法设置timeout
     * @see #expire(String, int)
     */
    Long expireAt(String key, long unixTime);

    /**
     * 该方法在作用和语义上与{@link #expireAt(String, long)}是相同的。但是该方法的Unix
     * Time过期时间点是毫秒为单位的，而非以秒为单位
     * 
     * @param key key
     * @param date milliseconds-timestamp
     * @return 如果timeout已设置，返回1。<code>key</code>不存在或无法设置timeout，返回0
     * @see #expireAt(String, long)
     */
    Long pexpireAt(String key, Date date);

	/**
	 * 获取键到期时间
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @return long
	 */
	long ttl(String key);
	/**
	 * 删除缓存对象
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @return long
	 */
	long del(String key);
	/**
	 * 删除缓存hash
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param hkey
	 * @param key
	 * @return long
	 */
	long hdel(String hkey, String key);
	/**
	 * 设置list
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param hkey
	 * @param strings
	 * @return long
	 */
	long lpush(String hkey, String... strings);
	/**
	 * 取list
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @param start
	 * @param end
	 * @return List<String>
	 */
	List<String> lrange(String key, long start, long end);
	/**
	 * 分布式锁
	 * @auth:liguangming
	 * @date:2018年05月26日
	 * @param key
	 * @return long
	 */
	long setnx(String key);
	/**
	 * 模糊查找key
	 *@auth:liguangming
	 *@date:2017年3月30日
	 *@param keys
	 *@return Set<String>
	 */
	Set<String> keys(String keys);
}
