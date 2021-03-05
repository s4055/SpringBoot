package com.lb.board.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "boardList")
@Entity
public class Member {
	// 아이디
	@Id
	@Column(name = "MEMBER_ID")
	private String username;

	// 패스워드
	private String password;

	// 이름
	private String name;

	// 권한
	@Enumerated(EnumType.STRING)
	private Role role;

	// 사용가능한 계정
	private boolean enabled;

	// 일대다 관계에서 다에 해당하는 Board를 저장하기 위한 List
	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
	private List<Board> boardList = new ArrayList<Board>();
}
