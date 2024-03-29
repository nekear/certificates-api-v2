package com.github.nekear.certificates_api.web.repos.daos.prototypes;

import com.github.nekear.certificates_api.web.entities.User;

import java.util.Optional;

public interface UsersDAO {
    long createOne(User user);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
