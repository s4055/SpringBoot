package com.lb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lb.board.security.SecurityUser;
import com.lb.board.service.CommentService;
import com.lb.dto.ResponseDto;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("/insertComment/{seq}")
	public ResponseDto<Integer> insertComment(@RequestBody String Ccontent, @PathVariable Long seq,
			@AuthenticationPrincipal SecurityUser principal) {
		// @PathVariable : 경로에 지정한 값을 변수에 저장
		// 큰따옴표 제거를 위해 substring
		commentService.insertComment(Ccontent.substring(1, Ccontent.length() - 1), seq, principal.getMember());
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}

	@PostMapping("/updateComment")
	public void updateCommnet() {
	}

	@PostMapping("/deleteComment/{seq}")
	public ResponseDto<Integer> deleteComment(@RequestBody Long Cseq, @PathVariable Long seq, @AuthenticationPrincipal SecurityUser principal) {
		int rs = commentService.deleteComment(Cseq, seq, principal.getMember());
		return new ResponseDto<Integer>(HttpStatus.OK, rs);
	}
}
