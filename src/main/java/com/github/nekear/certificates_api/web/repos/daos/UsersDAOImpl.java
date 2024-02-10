package com.github.nekear.certificates_api.web.repos.daos;

import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.entities.User;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.UsersDAO;
import com.github.nekear.certificates_api.web.repos.mappers.CertificateMapper;
import com.github.nekear.certificates_api.web.repos.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsersDAOImpl implements UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public long createOne(User user) {
        var INSERT_SQL = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var stmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);

            int index = 1;

            stmt.setString(index++, user.getUsername());
            stmt.setString(index++, user.getPassword());
            stmt.setDouble(index++, user.getRole().ordinal());

            return stmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public boolean existsByUsername(String username) {
        var GET_SQL = "SELECT COUNT(*) FROM users WHERE username = ?";

        return jdbcTemplate.queryForObject(GET_SQL, new Object[]{username}, Integer.class) > 0;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        var GET_SQL = "SELECT * FROM users WHERE username = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(GET_SQL, new String[]{username}, new UserMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
