package com.tibame.tga105.service;

import java.sql.Date;
import java.util.List;

import com.tibame.tga105.vo.ReservationsVO;
import com.tibame.tga105.vo.UserVO;

public interface ReservationsService {
	public String add(ReservationsVO reservationsVO);
	public String remove(Integer id);
	public List<ReservationsVO> getByUser(UserVO userVO);
	public List<ReservationsVO> getByDate(Date date, String timeSlot);
	public String getAva(Date date, String timeSlot);
	public String changeStatus(Integer id, String status);
	}
