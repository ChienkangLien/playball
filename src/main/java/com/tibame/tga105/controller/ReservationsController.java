package com.tibame.tga105.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tibame.tga105.service.ReservationsService;
import com.tibame.tga105.vo.ReservationsVO;
import com.tibame.tga105.vo.UserVO;

@RestController
@RequestMapping("/ReservationsController")
public class ReservationsController {
	@Autowired
	private ReservationsService reservationsService;

	@GetMapping("/ava")
	public Map<String, String> getAva(@RequestParam("date") Date date, @RequestParam("timeSlot") String timeSlot) {
		Map<String, String> map = new HashMap<String, String>();
		String result = "操作錯誤";
		if (date != null && timeSlot != null) {
			result = reservationsService.getAva(date, timeSlot);
		}
		map.put("message", result);
		return map;
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<List<ReservationsVO>> getReservations(@RequestParam("date") Date date, @RequestParam("timeSlot") String timeSlot) {
		if (date != null && timeSlot != null) {
			List<ReservationsVO> list = reservationsService.getByDate(date, timeSlot);
			if (list.size()!=0) {
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public Map<String, String> add(@RequestBody ReservationsVO reservationsVO, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		String result = "操作錯誤";

		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO != null && userVO.getFouls() >= 2) {
			result = "您已遭到停權，請聯絡場館處理復權事宜";
		} else if (userVO != null && reservationsVO != null) {
			reservationsVO.setUserVO(userVO);
			result = reservationsService.add(reservationsVO);
		}
		map.put("message", result);
		return map;
	}

	@GetMapping
	public ResponseEntity<List<ReservationsVO>> getByUser(HttpSession session) {
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		if (userVO != null) {
			return ResponseEntity.ok(reservationsService.getByUser(userVO));
		}
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping
	public Map<String, String> remove(@RequestParam("id") String id) {
		Map<String, String> map = new HashMap<String, String>();
		String result = "操作錯誤";

		if (id != null) {
			result = reservationsService.remove(Integer.parseInt(id));
		}
		map.put("message", result);
		return map;
	}
	
	@PutMapping
	public Map<String, String> changeStatus(@RequestParam("id") String id, @RequestParam("status") String status){
		Map<String, String> map = new HashMap<String, String>();
		String result = "操作錯誤";
		
		if(id!=null&&status!=null) {
			result = reservationsService.changeStatus(Integer.parseInt(id), status);
		}
		
		map.put("message", result);
		return map;
	}

}
