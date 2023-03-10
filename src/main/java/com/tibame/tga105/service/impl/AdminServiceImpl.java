package com.tibame.tga105.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tibame.tga105.dao.AdminDAO;
import com.tibame.tga105.dao.impl.AdminDAOImpl;
import com.tibame.tga105.service.AdminService;
import com.tibame.tga105.vo.AdminVO;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDAO adminDAO ;

	@Override
	public AdminVO login(AdminVO adminVO) {
		return adminDAO.getAdminVO(adminVO.getAccount(), adminVO.getPassword());
	}

}
