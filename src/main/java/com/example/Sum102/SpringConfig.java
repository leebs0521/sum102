package com.example.Sum102;

import com.example.Sum102.Repository.BookRepository;
import com.example.Sum102.Repository.JdbcBookRepository;
import com.example.Sum102.Service.BookService;
import com.example.Sum102.Service.UserService;
import com.example.Sum102.Repository.JdbcUserRepository;
import com.example.Sum102.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public BookService bookService() { return new BookService(bookRepository());}

    @Bean
    public BookRepository bookRepository() { return new JdbcBookRepository(dataSource);}
    @Bean
    public UserRepository userRepository(){
        return new JdbcUserRepository(dataSource);
    }
}