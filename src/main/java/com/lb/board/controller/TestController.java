package com.lb.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lb.board.domain.Board;
import com.lb.dto.ResponseDto;

@RestController
public class TestController {

	@GetMapping(value = "/message")
	// @ResponseBody
	public String testByResponseBody1() {
		String message = "안녕하세요. 화면에 출력됨";
		return message;
	}

	@GetMapping(value = "/members")
	// @ResponseBody
	public Map<Integer, Object> testByResponseBody2() {
		Map<Integer, Object> members = new HashMap<>();

		for (int i = 1; i <= 20; i++) {
			Map<String, Object> member = new HashMap<>();
			member.put("idx", i);
			member.put("nickname", i + "길동");
			member.put("height", i + 20);
			member.put("weight", i + 30);
			members.put(i, member);
		}
		return members;
	}

	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody Board board) {
		System.out.println("TestController : save 호출");
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}
}
