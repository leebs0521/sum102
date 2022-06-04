package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAll(Long pid);
    Comment save(Comment comment);

    List<Comment> test1();
}
