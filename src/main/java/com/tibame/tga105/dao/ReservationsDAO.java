package com.tibame.tga105.dao;

import java.sql.Date;
import java.util.List;

import com.tibame.tga105.vo.ReservationsVO;
import com.tibame.tga105.vo.UserVO;

public interface ReservationsDAO {
	public int insert(ReservationsVO reservationsVO);
	public int delete(Integer id);
	public List<ReservationsVO> getReservationsVOsByUser(UserVO userVO);
	public List<ReservationsVO> getReservationsVOsByDate(Date date, String timeSlot);
	public ReservationsVO getNext(ReservationsVO reservationsVO);
	public ReservationsVO getReservationsVO(Integer id);
	public int update(Integer id, String status);
}
