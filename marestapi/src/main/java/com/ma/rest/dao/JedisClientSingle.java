package com.ma.rest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Repository
public class JedisClientSingle implements JedisClient{
	
	public Logger logger = LoggerFactory.getLogger(JedisClientSingle.class);
	
	@Autowired
	private JedisPool jedisPool; 
	
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public boolean set(String key, String value) {
		Jedis jedis = null;
        try {
        	jedis = jedisPool.getResource();
        	jedis.set(key, value);
        	
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);

        } finally {
            returnResource(jedis);
        }
		return false;
	}
	@Override
    public boolean set(String key,int second, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key,second, value);
            
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);

        } finally {
            returnResource(jedis);
        }
        return false;
    }
	@Override
	public boolean set(String key, String value, int seconds) {		
		boolean result=this.set(key, value);
		if (result) {
			long i = expire(key, seconds);
			return i == 1;
		}
		return false;		
	}
	
    @Override
    public Long setIfNotExists(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setnx(key, value);
        }
    }

	@Override
	public byte[] get(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		byte[] string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

    @Override
    public Long hincrBy(String key, String field, long value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

    @Override
    public Long incr(String key, long increment) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incrBy(key, increment);
        }
    }

	@Override
	public long expire(String key, int seconds) {		
		if (key == null || key.equals("")) {
			return 0;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.expire(key, seconds);
		} catch (Exception ex) {
			logger.error("EXPIRE error[key=" + key + " seconds=" + seconds
					+ "]" + ex.getMessage(), ex);
		} finally {
			returnResource(jedis);
		}
		return 0;
		
	}

    @Override
    public Long expireAt(String key, long unixTime) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expireAt(key, unixTime);
        }
    }

    @Override
    public Long pexpireAt(String key, Date date) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.pexpireAt(key, date.getTime());
        }
    }

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hkey, key);
		jedis.close();
		return result;
	}

	@Override
	public long lpush(String key, String... strings) {
		Jedis jedis = jedisPool.getResource();
		Long result=jedis.lpush(key, strings);
		jedis.close();
		return result;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		List<String> lrange = jedis.lrange(key, start, end);
		jedis.close();
		return lrange;
	}

	@Override
	public long decr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.decr(key);
		jedis.close();
		return result;
	}

	@Override
	public long setnx(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long result=jedis.setnx(key, key);
		System.out.println("---------------"+result);
		jedis.close();
		return result;
	}

	@Override
	public Map<String, String> hgetall(String hkey) {
		Jedis jedis = jedisPool.getResource();
		Map<String, String> map = jedis.hgetAll(hkey);
		jedis.close();
		return map;
	}

    @Override
    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }
	
	private void returnResource(Jedis jedis) {
		try {
			if(jedis !=null){
				jedis.close();
			}
		} catch (Exception e) {
			logger.error("returnResource error.", e);
		}
	}

    @Override
    public Set<String> keys(String keys) {
        Jedis jedis = jedisPool.getResource();
        Set<String> newkeys = jedis.keys(keys);
        returnResource(jedis);
        return newkeys;
    }
	
}
