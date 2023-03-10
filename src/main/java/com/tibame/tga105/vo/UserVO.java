package com.tibame.tga105.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "USER")
@Component
public class UserVO implements Comparable<UserVO> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;


	@Column(name = "FOULS", nullable = false, insertable = false)
	private Integer fouls;

	public UserVO() {
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getFouls() {
		return fouls;
	}

	public void setFouls(Integer fouls) {
		this.fouls = fouls;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public UserVO(Integer id, String email, String password, Integer fouls) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fouls = fouls;
	}


	@Override
	public String toString() {
		return "UserVO [id=" + id + ", email=" + email + ", password=" + password  + ", fouls="
				+ fouls + "]";
	}


	@Override
	public int compareTo(UserVO o) {
		return Integer.compare(this.id, o.id);
	}


}
