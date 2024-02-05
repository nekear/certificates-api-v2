package com.github.nekear.certificates_api.web.repos.daos;


import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.CertificatesDAO;
import com.github.nekear.certificates_api.web.repos.mappers.CertificateMapper;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificatesDAOImpl implements CertificatesDAO {
    private final JdbcTemplate jdbcTemplate;

    public CertificatesDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> getAll() {
        return jdbcTemplate.query("SELECT * FROM certificates", new CertificateMapper());
    }

    @Override
    public Optional<Certificate> getById(long id) {
        var GET_SQL = "SELECT * FROM certificates WHERE id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(GET_SQL, new Object[]{id}, new CertificateMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long createOne(Certificate certificate) {
        var INSERT_SQL = "INSERT INTO certificates (name, description, price, duration) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var stmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);

            int index = 1;

            stmt.setString(index++, certificate.getName());
            stmt.setString(index++, certificate.getDescription());
            stmt.setDouble(index++, certificate.getPrice());
            stmt.setInt(index++, certificate.getDuration());

            return stmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
