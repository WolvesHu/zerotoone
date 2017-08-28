package com.wolves.test.zerotoone.redis;

import org.apache.commons.pool2.impl.GenericObjectPool;

import redis.clients.jedis.JedisPoolConfig;

public class RedisBase {
	private static String[] ReadWriteHosts = { "", "" };
	private static String[] ReadOnlyHosts = { "", "" };
	public  PooledRedisClientManager CreateManager(String[] ReadWriteHosts,String[]  ReadOnlyHosts){
		JedisPoolConfig  pool = new JedisPoolConfig();
//		pool.setma
		return null;
		
	}
}
