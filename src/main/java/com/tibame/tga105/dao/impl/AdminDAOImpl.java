package com.tibame.tga105.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tibame.tga105.dao.AdminDAO;
import com.tibame.tga105.vo.AdminVO;
import com.tibame.tga105.vo.UserVO;
@Repository
public class AdminDAOImpl implements AdminDAO {
	@PersistenceContext
	private Session session;
	
	public Session getSession() {
		return session;
	}

	@Override
	public AdminVO getAdminVO(String account, String password) {
		Query<AdminVO> query = this.getSession().createQuery("FROM AdminVO WHERE account = :account and password = :password",
				AdminVO.class);
		query.setParameter("account", account);
		query.setParameter("password", password);
		return query.uniqueResult();
	}

}
