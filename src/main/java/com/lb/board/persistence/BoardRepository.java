package com.lb.board.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lb.board.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
	@Query("SELECT b FROM Board b")
	Page<Board> getBoardList(Pageable pageable);

	// 강의명 검색
	Page<Board> findBylectureNameContaining(String lectureName, Pageable pageable);

	// 교수명 검색
	Page<Board> findByprofessorNameContaining(String professorName, Pageable pageable);

	// 강의구분 검색
	Page<Board> findBylectureDivideContaining(String lectureDivide, Pageable pageable);
}