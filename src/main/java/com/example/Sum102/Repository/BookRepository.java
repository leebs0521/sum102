package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Books;

import java.util.List;

public interface BookRepository {
    List<Books> findAll();
}
