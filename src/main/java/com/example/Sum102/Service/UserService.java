package com.example.Sum102.Service;

import com.example.Sum102.Domain.User;
import com.example.Sum102.Repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Boolean addUser(User user){
        if(userRepository.checkUser(user)) {
            userRepository.save(user);
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean login(User user){
        return userRepository.loginCheck(user);
    }
}
