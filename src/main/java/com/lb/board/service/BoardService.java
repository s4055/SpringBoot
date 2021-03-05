package com.lb.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lb.board.domain.Board;

public interface BoardService {
	void insertBoard(Board board);

	void updateBoard(Board board);

	void deleteBoard(Board board);

	Board getBoard(Board board);

	Page<Board> getBoardList(String searchType, String searchText, Pageable pageable);	
}
