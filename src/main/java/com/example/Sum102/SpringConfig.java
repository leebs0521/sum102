package com.example.Sum102;

import com.example.Sum102.Domain.User;
import com.example.Sum102.Service.UserService;
import com.example.Sum102.repository.JdbcUserRepository;
import com.example.Sum102.repository.UserRepository;
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
    public UserRepository userRepository(){
        return new JdbcUserRepository(dataSource);
    }
}