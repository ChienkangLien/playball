package com.tibame.tga105.service;

import java.util.List;

import com.tibame.tga105.vo.UserVO;

public interface UserService {
	public UserVO login(UserVO userVO);
	public String register(UserVO userVO);
	public List<UserVO> searchUsers(String email);
	public List<UserVO> searchUsers();
	public int judge(Integer id,Integer time);
}
