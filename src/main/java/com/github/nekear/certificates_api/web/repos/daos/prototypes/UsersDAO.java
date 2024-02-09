package com.github.nekear.certificates_api.web.repos.daos.prototypes;

import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.entities.User;

public interface UsersDAO {
    long create(String username, String password, Role role);

    boolean existsByUsername(String username);

    User findByUsername(String username);
}
