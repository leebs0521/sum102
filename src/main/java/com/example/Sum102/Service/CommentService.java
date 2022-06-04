package com.example.Sum102.Service;

import com.example.Sum102.Domain.Comment;
import com.example.Sum102.Domain.Product;
import com.example.Sum102.Repository.CommentRepository;
import com.example.Sum102.Repository.ProductRepository;

import java.sql.Timestamp;
import java.util.List;

public class CommentService {
    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findComments(Long pid) {
        return commentRepository.findAll(pid);
    }

    // 댓글 추가
    public void addComment(Comment comment){
        commentRepository.save(comment);
    }

    public List<Comment> test1(){
        return commentRepository.test1();
    }
}
