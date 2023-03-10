package com.tibame.tga105.dao;

import java.util.List;


import com.tibame.tga105.vo.UserVO;

public interface UserDAO {
	public int insert(UserVO userVO);
	public int update(UserVO userVO);
	public UserVO getUserVO(Integer id);
	public UserVO getUserVO(String email, String password);
	public List<UserVO> getUserVOs(String email);
	public List<UserVO> getUserVOs();
}
