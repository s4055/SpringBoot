package com.lb.board.persistence;

import org.springframework.data.repository.CrudRepository;

import com.lb.board.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}