package com.lb.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board")
@Entity
public class Comment {
	// 댓글 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Cseq;

	// 작성자
	@Column(updatable = false)
	private String writer;

	// 내용
	private String Ccontent;

	// 등록일
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createDate = new Date();

	// 게시글 번호를 가져오기 위한 연관매핑 객체
	@ManyToOne
	@JoinColumn(name = "BOARD_SEQ", nullable = false)
	private Board board;
}
