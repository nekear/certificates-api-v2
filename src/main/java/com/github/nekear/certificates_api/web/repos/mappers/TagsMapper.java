package com.github.nekear.certificates_api.web.repos.mappers;

import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.entities.Tag;
import com.github.nekear.certificates_api.web.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagsMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
