package com.tibame.tga105.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tibame.tga105.service.UserService;
import com.tibame.tga105.vo.UserVO;

@RestController
@RequestMapping("/UserController")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody UserVO userVO, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		UserVO temp;
		String result = "登入失敗";
		if (userVO != null) {
			temp = userService.login(userVO);
			if (temp != null) {
				session.setAttribute("userVO", temp);
				result = temp.getEmail() + "歡迎回來";
			}
		}
		map.put("message", result);
		return map;
	}

	@PostMapping("/register")
	public Map<String, String> register(@RequestBody UserVO userVO) {
		Map<String, String> map = new HashMap<String, String>();
		String result = "操作失敗";
		if (userVO != null) {
			result = userService.register(userVO);
		}
		map.put("message", result);
		return map;
	}
	
	@PostMapping("/logout")
	public Map<String, String> logout(HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		session.removeAttribute("userVO");
		String result = "登出成功，請重新登入";
		map.put("message", result);
		return map;
	}

	@GetMapping
	public ResponseEntity<UserVO> name(HttpSession session) {
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO != null) {
			return ResponseEntity.ok(userVO);
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/fromadmin")
	public ResponseEntity<List<UserVO>> searchUsers(@RequestParam("email") String email) {
		if (email != null) {
			List<UserVO> list = userService.searchUsers(email);
			return ResponseEntity.ok(list);
		}
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public Map<String, String> judge(@RequestParam("id") Integer id) {
		Map<String, String> map = new HashMap<String, String>();
		String result = "操作失敗";
		if (id != null) {
			result = userService.judge(id, -1) > 0 ? "登錄成功" : "登錄失敗";
		}
		map.put("message", result);
		return map;
	}
}
