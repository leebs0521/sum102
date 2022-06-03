package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAll(Long pid);
    Long save(Comment comment);
}
