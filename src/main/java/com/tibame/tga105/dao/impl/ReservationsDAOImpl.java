package com.tibame.tga105.dao.impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tibame.tga105.dao.ReservationsDAO;
import com.tibame.tga105.vo.AdminVO;
import com.tibame.tga105.vo.ReservationsVO;
import com.tibame.tga105.vo.UserVO;

@Repository
public class ReservationsDAOImpl implements ReservationsDAO {
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return session;
	}

	@Override
	public int insert(ReservationsVO reservationsVO) {
		return (int) this.getSession().save(reservationsVO) > 0 ? 1 : -1;
	}

	@Override
	public int delete(Integer id) {
		ReservationsVO temp = this.getSession().get(ReservationsVO.class, id);
		if (temp != null) {
			this.getSession().delete(temp);
			return 1;
		}
		return -1;
	}

	@Override
	public List<ReservationsVO> getReservationsVOsByUser(UserVO userVO) {
		Query<ReservationsVO> query = this.getSession().createQuery("FROM ReservationsVO WHERE userVO.id = :userId",
				ReservationsVO.class);
		query.setParameter("userId", userVO.getId());
		return query.list();
	}

	@Override
	public List<ReservationsVO> getReservationsVOsByDate(Date date, String timeSlot) {
		Query<ReservationsVO> query = this.getSession().createQuery(
				"FROM ReservationsVO WHERE bookDate = :date and timeSlot = :timeSlot", ReservationsVO.class);
		query.setParameter("date", date);
		query.setParameter("timeSlot", timeSlot);
		return query.list();
	}

	@Override
	public ReservationsVO getNext(ReservationsVO reservationsVO) {
		Query query = getSession().createQuery("FROM ReservationsVO WHERE bookDate=:bookDate AND timeSlot=:timeSlot");
		query.setParameter("bookDate", reservationsVO.getBookDate());
		query.setParameter("timeSlot", reservationsVO.getTimeSlot());
		query.setMaxResults(19);

		// 正取18位、要取第19位且是候補的狀態
		List<ReservationsVO> orders = query.list();
		for (ReservationsVO reservations : orders) {
			if (reservations.getSendMail() == 0 && reservations.getBookOrder().startsWith("候補")) {
				return reservations;
			}
		}
		return null;
	}

	@Override
	public ReservationsVO getReservationsVO(Integer id) {
		return this.getSession().get(ReservationsVO.class, id);
	}

	@Override
	public int update(Integer id, String status) {
		ReservationsVO temp = this.getSession().get(ReservationsVO.class, id);
		if (temp != null) {
			temp.setStatus(status);
			return 1;
		}
		return -1;
	}

}
