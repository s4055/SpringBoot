package com.lb.board.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "member", "commentList" })
@Entity
public class Board {
	// 게시글 번호
	@Id
	@GeneratedValue
	@Column(name = "BOARD_SEQ")
	private Long seq;

	// 강의명
	private String lectureName;

	// 교수명
	private String professorName;

	// 수강연도
	private String lectureYear;

	// 수강학기
	private String semesterDivide;

	// 강의구분
	private String lectureDivide;

	// 제목
	private String title;

	// 내용
	private String content;

	// 등록일
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createDate = new Date();

	// 평가
	private String score;

	// 추천
	private Long likeCnt = 0L;

	// 조회수
	private Long cnt = 0L;

	// 작성자를 가져오기 위한 연관매핑 객체
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	private Member member;

	// 일대다 관계에서 다에 해당하는 Comment를 저장하기 위한 List
	// 댓글을 뿌려줄 때 내림차순
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
	@OrderBy("Cseq desc")
	private List<Comment> commentList = new ArrayList<Comment>();

	public void setMember(Member member) {
		this.member = member;
		member.getBoardList().add(this);
	}
}
