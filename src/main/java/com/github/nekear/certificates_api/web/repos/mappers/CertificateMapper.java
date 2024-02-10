package com.github.nekear.certificates_api.web.repos.mappers;

import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.entities.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Arrays;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        var certificate = Certificate.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getDouble("price"))
                .duration(rs.getInt("duration"))
                .createdAt(rs.getTimestamp("created_at").toInstant().atZone(ZoneOffset.UTC))
                .updatedAt(rs.getTimestamp("updated_at").toInstant().atZone(ZoneOffset.UTC))
                .build();

        if(rs.getString("tags") != null)
            certificate.setTags(
                    Arrays.stream(rs.getString("tags").split("\\?"))
                            .map(tagStr -> {
                                String[] tagData = tagStr.split("#");
                                return Tag.builder()
                                        .id(Long.parseLong(tagData[0]))
                                        .name(tagData[1])
                                        .build();
                            }).toList()
            );

        return certificate;
    }
}
