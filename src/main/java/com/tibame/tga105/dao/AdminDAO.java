package com.tibame.tga105.dao;

import java.util.List;

import com.tibame.tga105.vo.AdminVO;
import com.tibame.tga105.vo.UserVO;

public interface AdminDAO {
	public AdminVO getAdminVO(String account, String password);
}
