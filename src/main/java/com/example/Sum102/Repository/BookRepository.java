package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Books;

import java.util.List;

public interface BookRepository {
    List<Books> findAll(); // 전체 목록 보기
    Books save(Books book); // 도서 추가
}
