package com.lb;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.lb.board.domain.Board;
import com.lb.board.domain.Comment;
import com.lb.board.domain.Member;
import com.lb.board.domain.Role;
import com.lb.board.persistence.BoardRepository;
import com.lb.board.persistence.CommentRepository;
import com.lb.board.persistence.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	public void testInsert() {
		// Member 테스트 등록
		Member m1 = new Member();
		m1.setUsername("member");
		m1.setPassword(encoder.encode("member123"));
		m1.setName("둘리");
		m1.setRole(Role.ROLE_MEMBER);
		m1.setEnabled(true);
		memberRepository.save(m1);

		Member m2 = new Member();
		m2.setUsername("admin");
		m2.setPassword(encoder.encode("admin123"));
		m2.setName("도우너");
		m2.setRole(Role.ROLE_ADMIN);
		m2.setEnabled(true);
		memberRepository.save(m2);

		// Board 태스트 등록
		// 게시글 번호, 강의명, 교수명, 수강연도, 수강학기,
		// 강의구분, 제목, 내용, 평가, 추천, 조회수, 생성날짜
		for (int i = 0; i <= 20; i++) {
			Board board = new Board();
			board.setMember(m1);
			board.setLectureName("자바 " + i);
			board.setProfessorName("홍길동 " + i);
			board.setLectureYear("2006년");
			board.setSemesterDivide("1학기");
			board.setLectureDivide("필수 전공");
			board.setTitle("제목 " + i);
			board.setContent("내용 " + i);
			board.setScore("4.0/5.0");
			boardRepository.save(board);
		}
		
		for (int i = 0; i <= 27; i++) {
			Board board = new Board();
			board.setMember(m1);
			board.setLectureName("C " + i);
			board.setProfessorName("나길동 " + i);
			board.setLectureYear("2012년");
			board.setSemesterDivide("1학기");
			board.setLectureDivide("선택 전공");
			board.setTitle("제목 " + i);
			board.setContent("내용 " + i);
			board.setScore("2.9/5.0");
			boardRepository.save(board);
		}

		for (int i = 0; i <= 21; i++) {
			Board board = new Board();
			board.setMember(m2);
			board.setLectureName("SQL " + i);
			board.setProfessorName("나한길 " + i);
			board.setLectureYear("2003년");
			board.setSemesterDivide("1학기");
			board.setLectureDivide("필수 교양");
			board.setTitle("제목 " + i);
			board.setContent("내용 " + i);
			board.setScore("3.4/5.0");
			boardRepository.save(board);
		}
		
		for (int i = 0; i <= 29; i++) {
			Board board = new Board();
			board.setMember(m2);
			board.setLectureName("JSP " + i);
			board.setProfessorName("고길동 " + i);
			board.setLectureYear("2015년");
			board.setSemesterDivide("1학기");
			board.setLectureDivide("선택 교양");
			board.setTitle("제목 " + i);
			board.setContent("내용 " + i);
			board.setScore("4.8/5.0");
			boardRepository.save(board);
		}

		// Comment 테스트 등록
		Random rand = new Random();
		//Long idx = 1L;
		for (int i = 0; i < m1.getBoardList().size(); i++) {
			for (int j = 0; j < rand.nextInt(2) + 1; j++) {
				Comment comment = new Comment();
				//comment.setCseq(idx++);
				comment.setBoard(m1.getBoardList().get(i));
				comment.setWriter("ㅇㅇㅇ");
				comment.setCcontent("댓글 " + j);
				commentRepository.save(comment);
			}
		}

		for (int i = 0; i < m2.getBoardList().size(); i++) {
			for (int j = 0; j < rand.nextInt(2) + 1; j++) {
				Comment comment = new Comment();
				//comment.setCseq(idx++);
				comment.setBoard(m2.getBoardList().get(i));
				comment.setWriter("ㅋㅋ");
				comment.setCcontent("댓글 " + j);
				commentRepository.save(comment);
			}
		}
		
		// 좋아요
		/*Board board = new Board();
		for(int i = 0; i < 1; i++) {
			Like like = new Like();
			like.setBoard(board);
			like.setMember(m1);
		}
		
		for(int i = 0; i < 1; i++) {
			Like like = new Like();
			like.setBoard(board);
			like.setMember(m2);
		}*/
	}

	// @Test
	public void testGetBoard() {
		// 상세 화면
		Board board = boardRepository.findById(1L).get();

		System.out.println("[ " + board.getSeq() + "번 게시 글 상세 정보 ]");
		System.out.println("강의명\t" + board.getLectureName());
		System.out.println("교수명\t" + board.getProfessorName());
		System.out.println("수강연도\t" + board.getLectureYear());
		System.out.println("수강학기\t" + board.getSemesterDivide());
		System.out.println("강의구분\t" + board.getLectureDivide());
		System.out.println("작성자\t" + board.getMember().getName());
		System.out.println("제목\t" + board.getTitle());
		System.out.println("내용\t" + board.getContent());
		System.out.println("평가\t" + board.getScore());
		System.out.println("추천\t" + board.getLikeCnt());
		System.out.println("등록일\t" + board.getCreateDate());
		System.out.println("조회수\t" + board.getCnt());
	}

	// @Test
	public void testGetBoardList() {
		// 목록 화면
		Member member = memberRepository.findById("member").get();

		System.out.println("[ " + member.getName() + "가 등록한 게시글 ]");
		for (Board board : member.getBoardList()) {
			System.out.println("---> " + board.toString());
		}
	}
}
