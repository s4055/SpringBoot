package com.lb.board.persistence;

import org.springframework.data.repository.CrudRepository;

import com.lb.board.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}