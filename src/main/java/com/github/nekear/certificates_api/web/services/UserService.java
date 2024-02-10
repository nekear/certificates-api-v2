package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.web.entities.User;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.UsersDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UsersDAO usersDAO;

    public User create(User user) {
        // Checking for the user existence
        if (usersDAO.existsByUsername(user.getUsername()))
            throw new RuntimeException("User already exists");

        // Registering the user and getting the newly created user id back
        var userId = usersDAO.createOne(user);

        user.setId(userId);

        return user;
    }

    public User findByUsername(String username) {
        return usersDAO.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
