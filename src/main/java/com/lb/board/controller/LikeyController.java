package com.lb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lb.board.service.LikeyService;
import com.lb.dto.ResponseDto;

@RestController
public class LikeyController {
	@Autowired
	LikeyService likeyService;

	@RequestMapping("/AddLike")
	public ResponseDto<Integer> AddLike(@RequestBody Long seq) {
		// 추천수 증가
		int rs = likeyService.AddLike(seq);
		return new ResponseDto<Integer>(HttpStatus.OK, rs);
	}
}
