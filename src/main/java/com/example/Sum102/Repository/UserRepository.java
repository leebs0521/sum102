package com.example.Sum102.Repository;

import com.example.Sum102.Domain.User;

public interface UserRepository {
    User save(User user);

    User loginCheck(User user);
}

