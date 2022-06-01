package com.example.Sum102;

import com.example.Sum102.Repository.ProductRepository;
import com.example.Sum102.Repository.JdbcProductRepository;
import com.example.Sum102.Service.ProductService;
import com.example.Sum102.Service.UsersService;
import com.example.Sum102.Repository.JdbcUsersRepository;
import com.example.Sum102.Repository.UsersRepository;
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
    public UsersService userService(){
        return new UsersService(userRepository());
    }

    @Bean
    public ProductService bookService() { return new ProductService(bookRepository());}

    @Bean
    public ProductRepository bookRepository() { return new JdbcProductRepository(dataSource);}
    @Bean
    public UsersRepository userRepository(){
        return new JdbcUsersRepository(dataSource);
    }
}