package com.example.Sum102.Controller;

import com.example.Sum102.Domain.Books;
import com.example.Sum102.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){ this.bookService = bookService; }

    @GetMapping(value = "/list")
    public String list(Model model) {
        System.out.println(" books mapping  ");
        List<Books> books = bookService.findBooks();
        System.out.println(" books mapping12313123  ");
        model.addAttribute("books", books);
        System.out.println(" books mapping 123123123123123 ");
        return "list1";
    }
}
