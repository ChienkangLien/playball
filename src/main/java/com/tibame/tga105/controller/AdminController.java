package com.tibame.tga105.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tibame.tga105.service.AdminService;
import com.tibame.tga105.vo.AdminVO;

@RestController
@RequestMapping("/AdminController")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody AdminVO adminVO,HttpSession session) {
		Map<String, String>map = new HashMap<String, String>();
		AdminVO temp;
		String result = "登入失敗";
		if (adminVO != null) {
			temp = adminService.login(adminVO);
			if(temp!=null) {
				session.setAttribute("adminVO", temp);
				result = temp.getAccount()+"歡迎回來";
			}
		}
		map.put("message", result);
		return map;
	}

	@PostMapping("/logout")
	public Map<String, String> logout(HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		session.removeAttribute("adminVO");
		String result = "登出成功，請重新登入";
		map.put("message", result);
		return map;
	}
}
