package com.tibame.tga105.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tibame.tga105.dao.UserDAO;
import com.tibame.tga105.vo.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {
	@Resource
	private RedisTemplate<String, UserVO> redisTemplate;

	@PersistenceContext
	private Session session;

	public Session getSession() {
		return session;
	}

	@Override
	public int insert(UserVO userVO) {
		Query<UserVO> query = this.getSession().createQuery("FROM UserVO WHERE email = :email", UserVO.class);
		query.setParameter("email", userVO.getEmail());
		if (query.uniqueResult() != null) {
			return -1;
		}

		// 使用SHA-256算法做加密
		String encryptedPassword = encryptPassword(userVO.getPassword());
		userVO.setPassword(encryptedPassword);
		int result = (int) this.getSession().save(userVO);
		
		//刪除redis中的資料、以求兩個資料庫資料同步
		redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
		        connection.flushDb();
		        return null;
		});
		return  result > 0 ? 1 : -1;
	}

	@Override
	public int update(UserVO userVO) {
		UserVO temp = this.getSession().get(UserVO.class, userVO.getId());
		if (temp != null) {
			temp.setFouls(userVO.getFouls());
			String redisKeyPattern = "*:id:" + userVO.getId() + ":*";
			Set<String> redisKeys = redisTemplate.keys(redisKeyPattern);
			redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
				for (String redisKey : redisKeys) {
					UserVO redisUserVO = redisTemplate.opsForValue().get(redisKey);
					// 修改 UserVO 對象
					redisUserVO.setFouls(temp.getFouls());
					// 更新 Redis 中的資料
					redisTemplate.opsForValue().set(redisKey, redisUserVO);
				}
				return null;
			});
			return 1;
		}
		return -1;
	}

	@Override
	public UserVO getUserVO(Integer id) {
		return this.getSession().get(UserVO.class, id);
	}

	@Override
	public UserVO getUserVO(String email, String password) {
		Query<UserVO> query = this.getSession().createQuery("FROM UserVO WHERE email = :email and password = :password",
				UserVO.class);
		query.setParameter("email", email);
		query.setParameter("password", encryptPassword(password));
		return query.uniqueResult();
	}

	@Override
	public List<UserVO> getUserVOs(String email) {
		List<UserVO> userVOs = new ArrayList<>();
		String redisKeyPattern = "userVOs:" + email + ":id:*";
		Set<String> redisKeys = redisTemplate.keys(redisKeyPattern);
		for (String redisKey : redisKeys) {
			UserVO userVO = redisTemplate.opsForValue().get(redisKey);
//			System.out.println("redis: " + userVO);
			userVOs.add(userVO);
		}
		if (!userVOs.isEmpty()) {
			// Redis 中有緩存，直接返回
			Collections.sort(userVOs);
			return userVOs;
		}

		// Redis 中沒有緩存，從資料庫讀取，並將結果存入 Redis 中
		Query<UserVO> query = this.getSession().createQuery("FROM UserVO WHERE email like :email", UserVO.class);
		query.setParameter("email", email + "%");
		userVOs = query.list();
		Collections.sort(userVOs);

		for (UserVO userVO : userVOs) {
			String voRedisKey = "userVOs:" + email + ":id:" + userVO.getId() + ":";
			redisTemplate.opsForValue().set(voRedisKey, userVO);
//			System.out.println("mysql: " + userVO);
		}
		return userVOs;
	}

	@Override
	public List<UserVO> getUserVOs() {
		Query<UserVO> query = this.getSession().createQuery("FROM UserVO", UserVO.class);
		return query.list();
	}

	private String encryptPassword(String password) {
		String encryptedPassword = null;
		try {
			// 使用SHA-256算法做加密
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			// 將加密後的字節數組轉換成16進制字符串
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			encryptedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedPassword;
	}
}
