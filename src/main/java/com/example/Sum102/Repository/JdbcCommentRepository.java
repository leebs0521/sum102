package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Comment;

import java.util.List;

public class JdbcCommentRepository implements CommentRepository{

    @Override
    public List<Comment> findAll(Long pid) {
        // 동열이형
        return null;
    }

    @Override
    public Long save(Comment comment) {
        // 원영이형
        return null;

    }
}
