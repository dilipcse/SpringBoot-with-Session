package com.IdentityManagement.System.DAO;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRedisRepository {
	@Autowired
	private RedisTemplate<String, Object> redis;
	
	public void putIntoRedis(String token, String userId) {
		redis.opsForValue().set(token, userId);
		//redis.opsForHash().put(REDIX_INDEX,Token , name );     /* token is stored on redis server  */
		redis.expire(token , 3000 , TimeUnit.SECONDS);  
	}
	
	public String getFromRedis(String token) {
		String userId = (String) redis.opsForValue().get(token);
		System.out.println("userId "+ userId);
		if(userId != null) {
			redis.expire(token, 3000, TimeUnit.SECONDS);
		}
		return userId;
	}
	
	public void deleteFromRedis(String token) {
		redis.expire(token, 0, TimeUnit.SECONDS);
	}
}
