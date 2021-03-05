package com.lb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lb.board.domain.Member;
import com.lb.board.domain.Role;
import com.lb.board.persistence.MemberRepository;

@Controller
public class SecurityController {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/system/joinForm")
	public void joinFormView() {
	}

	@PostMapping("/system/joinForm")
	public String joinForm(Member member) {
		String password = member.getPassword();
		member.setPassword(encoder.encode(password));
		member.setRole(Role.ROLE_MEMBER);
		member.setEnabled(true);
		memberRepository.save(member);
		return "redirect:/";
	}

	@GetMapping("/index")
	public void login() {
	}

	@GetMapping("/system/accessDenied")
	public void accessDenied() {
	}

	@GetMapping("/system/logout")
	public void logout() {
	}

	@GetMapping("/admin/adminPage")
	public void admin() {
	}
}
