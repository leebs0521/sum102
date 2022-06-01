package com.example.Sum102.Repository;

import com.example.Sum102.Domain.Users;

public interface UsersRepository {
    Users save(Users users);

    Boolean checkUser(Users users);
    Boolean loginCheck(Users users);
}

