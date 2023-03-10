package com.tibame.tga105.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tibame.tga105.dao.ReservationsDAO;
import com.tibame.tga105.service.MailService;
import com.tibame.tga105.service.ReservationsService;
import com.tibame.tga105.service.UserService;
import com.tibame.tga105.vo.ReservationsVO;
import com.tibame.tga105.vo.UserVO;

@Service
@Transactional
public class ReservationsServiceImpl implements ReservationsService {
	@Autowired
	private ReservationsDAO reservationsDAO;
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;

	@Override
	public String add(ReservationsVO reservationsVO) {
		List<ReservationsVO> list = reservationsDAO.getReservationsVOsByDate(reservationsVO.getBookDate(),
				reservationsVO.getTimeSlot());
		int num = list.size();
		if (num < 18) {
			reservationsVO.setBookOrder("正取" + (num + 1));
		} else {
			reservationsVO.setBookOrder("候補" + (num - 17));
		}
		return reservationsDAO.insert(reservationsVO) > 0 ? "新增成功" : "新增失敗";
	}

	@Override
	public String remove(Integer id) {
		ReservationsVO temp = reservationsDAO.getReservationsVO(id);
		if (temp != null) {
			ReservationsVO next = reservationsDAO.getNext(temp);
			if (next != null) {
//				發送郵件功能
				mailService.sendEmail(next.getUserVO().getEmail());
				next.setSendMail(1);
				next.setBookOrder(next.getBookOrder() + "(補位成功)");
			}
			return reservationsDAO.delete(id) > 0 ? "刪除成功" : "刪除失敗";
		}
		return "刪除失敗";
	}

	@Override
	public List<ReservationsVO> getByUser(UserVO userVO) {
		if (userVO.getId() != null) {
			return reservationsDAO.getReservationsVOsByUser(userVO);
		}
		return null;
	}

	@Override
	public List<ReservationsVO> getByDate(Date date, String timeSlot) {
		if (date != null && timeSlot != null) {
			return reservationsDAO.getReservationsVOsByDate(date, timeSlot);
		}
		return null;
	}

	@Override
	public String getAva(Date date, String timeSlot) {
		List<ReservationsVO> list = reservationsDAO.getReservationsVOsByDate(date, timeSlot);
		if (list.size() < 18) {
			return "尚有空位:" + (18 - list.size()) + "個";
		} else {
			return "將是候補第" + (list.size() - 17) + "位";
		}
	}

	@Override
	public String changeStatus(Integer id, String status) {
		int result = 0;
		if (status != "完成報到") {
			ReservationsVO reservationsVO = reservationsDAO.getReservationsVO(id);
			result = userService.judge(reservationsVO.getUserVO().getId(), 1);
			if (result <= 0) {
				return "登錄失敗";
			}
		}
		return reservationsDAO.update(id, status) > 0 ? "登錄成功" : "登錄失敗";
	}

}
