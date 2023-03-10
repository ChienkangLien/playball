package com.tibame.tga105.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tibame.tga105.dao.UserDAO;
import com.tibame.tga105.service.UserService;
import com.tibame.tga105.vo.UserVO;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserVO login(UserVO userVO) {
		return userDAO.getUserVO(userVO.getEmail(), userVO.getPassword());
	}

	@Override
	public String register(UserVO userVO) {
		return userDAO.insert(userVO) > 0 ? "註冊成功，請重新登入" : "email已被其他人使用，請重新輸入";
	}

	@Override
	public List<UserVO> searchUsers(String email) {
		return userDAO.getUserVOs(email);
	}

	@Override
	public List<UserVO> searchUsers() {
		return userDAO.getUserVOs();
	}

	@Override
	public int judge(Integer id, Integer time) {
		Integer newFouls = null;
		if (time > 0) {
			newFouls = userDAO.getUserVO(id).getFouls() + 1;
		} else if (time < 0) {
			newFouls = userDAO.getUserVO(id).getFouls() - 1;
		}
		UserVO userVO = new UserVO();
		userVO.setFouls(newFouls);
		userVO.setId(id);

		return userDAO.update(userVO);
	}

}
