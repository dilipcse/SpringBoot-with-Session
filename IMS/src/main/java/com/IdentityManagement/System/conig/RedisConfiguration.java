package com.IdentityManagement.System.conig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
	
	@Bean
	JedisConnectionFactory getJedisConnection() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		return jedisConnectionFactory;
	}
	
	@Bean
	RedisTemplate <String, Object> getRedisTemplate(){
		RedisTemplate<String, Object> redis = new RedisTemplate<>();
		redis.setConnectionFactory(getJedisConnection());
		redis.setHashKeySerializer(new StringRedisSerializer());
		redis.setHashValueSerializer(new StringRedisSerializer());
		redis.setKeySerializer(new StringRedisSerializer());
		redis.setValueSerializer(new StringRedisSerializer());
		return redis;
	}
}
