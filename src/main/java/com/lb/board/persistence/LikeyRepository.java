package com.lb.board.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lb.board.domain.Board;
import com.lb.board.domain.Likey;
import com.lb.board.domain.Member;

public interface LikeyRepository extends CrudRepository<Likey, Long> {
	// 해당 게시글에 좋아요한 사용자 찾기
	List<Likey> findByBoardAndMember(Board board, Member member);
}
