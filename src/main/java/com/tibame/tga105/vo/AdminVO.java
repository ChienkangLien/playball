package com.tibame.tga105.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "ADMIN")
@Component
public class AdminVO {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ACCOUNT", nullable = false, unique = true)
	private String account;

	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	public AdminVO() {
	}

	public AdminVO(Integer id, String account, String password) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
	}

	@Override
	public String toString() {
		return "AdminVO [id=" + id + ", account=" + account + ", password=" + password + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
