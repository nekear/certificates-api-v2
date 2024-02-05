package com.github.nekear.certificates_api.web.repos.mappers;

import com.github.nekear.certificates_api.web.entities.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getDouble("price"))
                .duration(rs.getInt("duration"))
                .createdAt(rs.getTimestamp("created_at").toInstant().atZone(ZoneOffset.UTC))
                .updatedAt(rs.getTimestamp("updated_at").toInstant().atZone(ZoneOffset.UTC))
                .build();
    }
}
