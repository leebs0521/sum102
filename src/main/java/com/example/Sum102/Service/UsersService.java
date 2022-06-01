package com.example.Sum102.Service;

import com.example.Sum102.Domain.Users;
import com.example.Sum102.Repository.UsersRepository;

public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public Boolean addUser(Users users){
        if(usersRepository.checkUser(users)) {
            usersRepository.save(users);
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean login(Users users){
        return usersRepository.loginCheck(users);
    }
}
