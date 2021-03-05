package com.lb.board.service;

import com.lb.board.domain.Comment;
import com.lb.board.domain.Member;

public interface CommentService {
	void insertComment(String Ccontent, Long seq, Member member);

	void updateComment(Comment comment);

	int deleteComment(Long Cseq, Long seq, Member member);
}
