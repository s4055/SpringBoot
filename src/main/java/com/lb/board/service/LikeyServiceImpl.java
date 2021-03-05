package com.lb.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lb.board.domain.Board;
import com.lb.board.domain.Likey;
import com.lb.board.domain.Member;
import com.lb.board.persistence.BoardRepository;
import com.lb.board.persistence.LikeyRepository;
import com.lb.board.security.SecurityUser;

@Service
public class LikeyServiceImpl implements LikeyService {
	@Autowired
	BoardRepository boardRepository;

	@Autowired
	private LikeyRepository likeyRepository;

	public int AddLike(Long seq) {
		int resultMessage = -1;

		// 게시글에 대한 정보
		Board findBoard = new Board();
		findBoard = boardRepository.findById(seq).get();
		System.out.println(findBoard);

		// 로그인한 사용자 정보를 Object타입의 authentication로 저장
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// SecurityUser로 형변환 후 getMember()를 통해 Member객체를 member 저장
		Member member = ((SecurityUser) authentication.getPrincipal()).getMember();

		/* 구현 */
		// 작성자
		String writer = findBoard.getMember().getName();
		// 사용자(현재 로그인한 사람)
		String user = member.getName();

		// 작성자 == 사용자
		if (writer.equals(user)) {
			System.out.println("본인이 작성한 게시글은 추천할 수 없습니다.");
			resultMessage = 1;
		}
		// 작성자 != 사용자
		else {
			// findByBoardAndMember 통해 추천을 할 수 있는지 확인
			if (likeyRepository.findByBoardAndMember(findBoard, member).isEmpty()) {
				// 가능 : Likey 테이블 삽입, Board 테이블의 likeCnt 증가

				// Board 테이블 likeCnt 증가
				Long likeCnt = findBoard.getLikeCnt();
				findBoard.setLikeCnt(++likeCnt);
				boardRepository.save(findBoard);

				// Likey 삽입
				Likey likey = new Likey();
				likey.setBoard(findBoard);
				likey.setMember(member);
				likeyRepository.save(likey);

				resultMessage = 2;
			} else {
				// 불가능 : 추천할 수 없습니다.
				System.out.println("이미 추천하여 할 수 없습니다.");
				resultMessage = 3;
			}
		}
		
		// 조회수를 맞추기 위한 작업
		Long cnt = findBoard.getCnt();
		findBoard.setCnt(--cnt);
		boardRepository.save(findBoard);

		return resultMessage;
	}
}
