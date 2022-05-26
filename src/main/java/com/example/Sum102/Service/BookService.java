package com.example.Sum102.Service;

import com.example.Sum102.Domain.Books;
import com.example.Sum102.Repository.BookRepository;

import java.util.List;

public class BookService {
    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Books> findBooks() {
        return bookRepository.findAll();
    }

}
