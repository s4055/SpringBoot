package com.lb.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lb.board.domain.Board;
import com.lb.board.persistence.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public void insertBoard(Board board) {
		boardRepository.save(board);
	}

	public void updateBoard(Board board) {
		Board findBoard = boardRepository.findById(board.getSeq()).get();

		Long cnt = findBoard.getCnt();

		// 강의명
		findBoard.setLectureName(board.getLectureName());
		// 교수명
		findBoard.setProfessorName(board.getProfessorName());
		// 수강연도
		findBoard.setLectureYear(board.getLectureYear());
		// 수강학기
		findBoard.setSemesterDivide(board.getSemesterDivide());
		// 강의구분
		findBoard.setLectureDivide(board.getLectureDivide());
		// 제목
		findBoard.setTitle(board.getTitle());
		// 내용
		findBoard.setContent(board.getContent());
		// 평가
		findBoard.setScore(board.getScore());
		// 조회수
		findBoard.setCnt(cnt);

		boardRepository.save(findBoard);
	}

	public void deleteBoard(Board board) {
		boardRepository.deleteById(board.getSeq());
	}

	public Board getBoard(Board board) {
		return boardRepository.findById(board.getSeq()).get();
	}

	public Page<Board> getBoardList(String searchType, String searchText, Pageable pageable) {
		if (searchType.equals("lectureName")) {
			// 강의명
			return boardRepository.findBylectureNameContaining(searchText, pageable);
		} else if (searchType.equals("professorName")) {
			// 교수명
			return boardRepository.findByprofessorNameContaining(searchText, pageable);
		} else if (searchType.equals("lectureDivide")) {
			// 강의구분
			return boardRepository.findBylectureDivideContaining(searchText, pageable);
		}
		return boardRepository.getBoardList(pageable);
	}
}
