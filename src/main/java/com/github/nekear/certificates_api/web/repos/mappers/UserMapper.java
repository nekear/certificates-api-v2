package com.github.nekear.certificates_api.web.repos.mappers;

import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .role(Role.values()[rs.getInt("role")])
                .build();

    }
}
