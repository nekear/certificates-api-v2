package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.web.entities.User;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.UsersDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UsersDAO usersDAO;

    public User create(User user) {
        if (usersDAO.existsByUsername(user.getUsername()))
            throw new RuntimeException("User already exists");

        var userId = usersDAO.create(user.getUsername(), user.getPassword(), user.getRole());

        user.setId(userId);

        return user;
    }

    public User findByUsername(String username) {
        return usersDAO.findByUsername(username);
    }
}
