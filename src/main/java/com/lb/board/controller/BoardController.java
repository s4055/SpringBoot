package com.lb.board.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lb.board.domain.Board;
import com.lb.board.domain.Member;
import com.lb.board.security.SecurityUser;
import com.lb.board.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("/getBoardList")
	public String getBoardList(Model model, Board board,
			@PageableDefault(sort = "seq", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText,
			@RequestParam(required = false, defaultValue = "") String searchType) {

		Page<Board> boardList = boardService.getBoardList(searchType, searchText, pageable);

		int startPage = Math.max(1, boardList.getPageable().getPageNumber() - 2);
		int endPage = Math.min(boardList.getTotalPages(), boardList.getPageable().getPageNumber() + 2);
		
//		// 게시글이 없다면
//		if(boardList.getTotalElements() >= 0 && boardList.getTotalElements() <= 10) {
//			startPage = 1;
//			endPage =  1;
//		}
		
		// 보여지는 페이지 수 5개로 고정
		if ((boardList.getTotalPages() > 4) && (boardList.getPageable().getPageNumber() < 3)) {
			endPage = 5;
		}

//		System.out.println("boardList.getPageable().getPageNumber() : " + boardList.getPageable().getPageNumber());
//		System.out.println("boardList.getTotalPages() : " + boardList.getTotalPages());
//		System.out.println("boardList.getTotalElements() : " + boardList.getTotalElements());
//		System.out.println("startPage : " + startPage);
//		System.out.println("endPage : " + endPage);

		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("boardList", boardList);
		return "board/getBoardList";
	}

	@GetMapping("/getBoard")
	public String getBoard(Board board, Model model) {
		// 게시글 번호에 해당하는 Board 객체를 가져옴
		Board boardservice = boardService.getBoard(board);

		// 조회수 늘리기
		Long OldCnt = boardservice.getCnt();
		boardservice.setCnt(++OldCnt);
		boardService.updateBoard(boardservice);

		model.addAttribute("board", boardservice);
		model.addAttribute("commentList", boardservice.getCommentList());
		return "board/getBoard";
	}

	@GetMapping("/getBoardModify")
	public String getBoardModify(Board board, Model model, @AuthenticationPrincipal SecurityUser principal,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		Board findBoard = boardService.getBoard(board);
		Member member = principal.getMember();

		if (findBoard.getMember().getName().equals(member.getName())) {
			model.addAttribute("board", boardService.getBoard(board));
			return "board/getBoardModify";
		} else {
			out.println("<script>alert('본인 게시글 외에는 수정하실 수 없습니다.'); history.go(-1);</script>");
			out.flush();
			return "board/getBoard?seq=" + board.getSeq();
		}
	}

	@GetMapping("/insertBoard")
	public String insertBoardView() {
		return "board/insertBoard";
	}

	@PostMapping("/insertBoard")
	public String insertBoard(Board board, @AuthenticationPrincipal SecurityUser principal,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		// 강의명 교수명 수강연도 수강학기 강의구분 제목 내용 평가
		if (board.getLectureName().isEmpty() || board.getProfessorName().isEmpty() || board.getLectureYear().isEmpty()
				|| board.getSemesterDivide().isEmpty() || board.getLectureDivide().isEmpty()
				|| board.getTitle().isEmpty() || board.getContent().isEmpty() || board.getScore().isEmpty()) {
			out.println("<script>alert('빈칸이 있습니다.\\n다시 입력해주세요.');</script>");
			out.flush();
			return "board/insertBoard";
		}

		if (!checkNumberOrStr(board.getScore())) {
			out.println("<script>alert('평가는 문자가 아닌 숫자를 입력해야합니다.\\n다시 입력해주세요.');</script>");
			out.flush();
			return "board/insertBoard";
		}

		if (Double.parseDouble(board.getScore()) > 5) {
			out.println("<script>alert('평가가 잘못 입력되었습니다.\\n다시 입력해주세요.');</script>");
			out.flush();
			return "board/insertBoard";
		}

		board.setMember(principal.getMember());
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}

	@PostMapping("/updateBoard")
	public String updateBoard(Board board, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		// 강의명 교수명 수강연도 수강학기 강의구분 제목 내용 평가
		if (board.getLectureName().isEmpty() || board.getProfessorName().isEmpty() || board.getLectureYear().isEmpty()
				|| board.getSemesterDivide().isEmpty() || board.getLectureDivide().isEmpty()
				|| board.getTitle().isEmpty() || board.getContent().isEmpty() || board.getScore().isEmpty()) {
			out.println("<script>alert('빈칸이 있습니다.\\n다시 입력해주세요.'); history.go(-1);</script>");
			out.flush();
			return "board/getBoardModify?seq=" + board.getSeq();
		}

		if (!checkNumberOrStr(board.getScore())) {
			out.println("<script>alert('평가는 문자가 아닌 숫자를 입력해야합니다.\\n다시 입력해주세요.'); history.go(-1);</script>");
			out.flush();
			return "board/getBoardModify?seq=" + board.getSeq();
		}

		if (Double.parseDouble(board.getScore()) > 5) {
			out.println("<script>alert('평가가 잘못 입력되었습니다.\\n다시 입력해주세요.'); history.go(-1);</script>");
			out.flush();
			return "board/getBoardModify?seq=" + board.getSeq();
		}

		boardService.updateBoard(board);
		return "forward:getBoardList";
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}

	public boolean checkNumberOrStr(String str) {
		// 숫자 : true, 그 외 : false
		boolean check = true;
		
		if(str.length() == 3) {
			if(Character.isDigit(str.charAt(0)) == true && Character.isDigit(str.charAt(1)) == false && Character.isDigit(str.charAt(2)) == true){
				check = true;
			}
			else {
				check = false;
			}
		}
		else {
			check = false;
		}
		return check;
	}
}
