package com.lb.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lb.board.domain.Comment;
import com.lb.board.domain.Member;
import com.lb.board.persistence.BoardRepository;
import com.lb.board.persistence.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private CommentRepository commentRepository;

	public void insertComment(String Ccontent, Long seq, Member member) {
		Comment comment = new Comment();
		comment.setWriter(member.getName());
		comment.setCcontent(Ccontent);
		comment.setBoard(boardRepository.findById(seq).get());
		commentRepository.save(comment);

		// 조회수를 맞추기 위한 작업
		Long cnt = boardRepository.findById(seq).get().getCnt();
		boardRepository.findById(seq).get().setCnt(--cnt);
		boardRepository.save(boardRepository.findById(seq).get());
	}

	public void updateComment(Comment comment) {
		Comment findComment = commentRepository.findById(comment.getCseq()).get();

		findComment.setCcontent(comment.getCcontent());
		commentRepository.save(findComment);
	}

	public int deleteComment(Long Cseq, Long seq, Member member) {
		// 조회수를 맞추기 위한 작업
		Long cnt = boardRepository.findById(seq).get().getCnt();
		boardRepository.findById(seq).get().setCnt(--cnt);
		boardRepository.save(boardRepository.findById(seq).get());

		// 댓글 작성자와 사용자가 같은 사람인지 비교
		if (commentRepository.findById(Cseq).get().getWriter().equals(member.getName())) {
			// 댓글 작성자와 사용자가 같은 경우 : 삭제
			System.out.println("댓글을 삭제합니다.");
			commentRepository.deleteById(Cseq);
			return 1;
		} else {
			// 댓글 작성자와 사용자가 다른 경우 : 삭제 X
			System.out.println("댓글을 삭제가 불가능합니다.");
			return 0;
		}
	}
}
