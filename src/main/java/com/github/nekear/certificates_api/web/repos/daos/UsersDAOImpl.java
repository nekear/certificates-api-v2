package com.github.nekear.certificates_api.web.repos.daos;

import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.entities.User;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.UsersDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsersDAOImpl implements UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public long create(String username, String password, Role role) {
        return 0L;
    }

    @Override
    public boolean existsByUsername(String username) {
        // TODO: implement
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
