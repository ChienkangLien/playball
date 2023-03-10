package com.tibame.tga105.vo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "RESERVATIONS")
@Component
public class ReservationsVO {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "BOOK_DATE", nullable = false)
	private Date bookDate;

	@Column(name = "TIME_SLOT", nullable = false)
	private String timeSlot;

	@Column(name = "STATUS", nullable = false, insertable = false)
	private String status;

	@Column(name = "BOOK_ORDER", nullable = false)
	private String bookOrder;

	@Column(name = "SEND_MAIL", nullable = false, insertable = false)
	private Integer sendMail;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private UserVO userVO;

	public ReservationsVO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBookOrder() {
		return bookOrder;
	}

	public void setBookOrder(String bookOrder) {
		this.bookOrder = bookOrder;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public Integer getSendMail() {
		return sendMail;
	}

	public void setSendMail(Integer sendMail) {
		this.sendMail = sendMail;
	}

	@Override
	public String toString() {
		return "ReservationsVO [id=" + id + ", bookDate=" + bookDate + ", timeSlot=" + timeSlot + ", status=" + status
				+ ", bookOrder=" + bookOrder + ", sendMail=" + sendMail + "]";
	}

	public ReservationsVO(Integer id, Date bookDate, String timeSlot, String status, String bookOrder, Integer sendMail,
			UserVO userVO) {
		super();
		this.id = id;
		this.bookDate = bookDate;
		this.timeSlot = timeSlot;
		this.status = status;
		this.bookOrder = bookOrder;
		this.sendMail = sendMail;
		this.userVO = userVO;
	}

}
