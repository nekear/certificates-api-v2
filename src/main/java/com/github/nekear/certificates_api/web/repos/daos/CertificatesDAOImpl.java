package com.github.nekear.certificates_api.web.repos.daos;


import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.entities.Tag;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.CertificatesDAO;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.TagsDAO;
import com.github.nekear.certificates_api.web.repos.mappers.CertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CertificatesDAOImpl implements CertificatesDAO {
    private final JdbcTemplate jdbcTemplate;

    private final TagsDAO tagsDAO;

    @Override
    public Page<Certificate> findAll(String mainSearch, String tagsSearch, Pageable pageable, long userId) {
        var GET_SQL = "SELECT *, glueTags(certificates.id) as tags FROM certificates ";

        List<Object> queryParams = new LinkedList<>();

        StringJoiner joiner = new StringJoiner(" AND ");
        if (mainSearch != null || tagsSearch != null || userId != -1) {
            GET_SQL += "WHERE ";

            if (mainSearch != null) {
                joiner.add("(certificates.name LIKE ? OR certificates.description LIKE ?)");
                queryParams.add("%" + mainSearch + "%");
                queryParams.add("%" + mainSearch + "%");
            }

            if (tagsSearch != null) {
                joiner.add("countConnectedTagsWithName(certificates.id, ?) > 0");
                queryParams.add("%" + tagsSearch + "%");
            }

            // Filtering by user_id
            if (userId != -1) {
                joiner.add("user_id = ?");
                queryParams.add(userId);
            }

            GET_SQL += joiner + " ";
        }


        // Ordering
        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort().stream()
                    .map(order -> "certificates." + order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", "));
            GET_SQL += " ORDER BY " + orderBy;
        }

        // Pagination
        if (pageable.isPaged()) {
            GET_SQL += " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();
        }

        // Counting total elements for PageImpl
        var COUNT_SQL = "SELECT COUNT(*) FROM certificates ";
        if (mainSearch != null || tagsSearch != null || userId != -1) {
            COUNT_SQL += "WHERE " + joiner;
        }
        long totalElements = jdbcTemplate.queryForObject(COUNT_SQL, queryParams.toArray(), Long.class);

        // Executing the main query
        List<Certificate> content = jdbcTemplate.query(GET_SQL, queryParams.toArray(), new CertificateMapper());

        return new PageImpl<>(content, pageable, totalElements);
    }


    @Override
    public Optional<Certificate> findById(long id, long userId) {
        var GET_SQL = "SELECT *, glueTags(certificates.id) as tags FROM certificates WHERE id = ?";

        try {
            if (userId != -1)
                return Optional.of(jdbcTemplate.queryForObject(GET_SQL + " AND user_id = ?", new Object[]{id, userId}, new CertificateMapper()));

            return Optional.of(jdbcTemplate.queryForObject(GET_SQL, new Object[]{id}, new CertificateMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Long createOne(Certificate certificate) {
        var INSERT_SQL = "INSERT INTO certificates (name, description, price, duration, user_id) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var stmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);

            int index = 1;

            stmt.setString(index++, certificate.getName());
            stmt.setString(index++, certificate.getDescription());
            stmt.setDouble(index++, certificate.getPrice());
            stmt.setInt(index++, certificate.getDuration());
            stmt.setLong(index++, certificate.getUser().getId());

            return stmt;
        }, keyHolder);

        var ceritificateId = keyHolder.getKey().longValue();

        List<Tag> tags = certificate.getTags();

        if (tags != null) {
            // Creating new tags
            tags.stream().filter(x -> x.getId() == null).forEach(x -> {
                var tagId = tagsDAO.createOne(x);
                x.setId(tagId);
            });

            // Connecting tags to the certificate
            tags.forEach(x -> connectTag(ceritificateId, x.getId()));
        }

        return ceritificateId;
    }

    @Override
    @Transactional
    public void updateOne(Certificate certificate) {
        var UPDATE_SQL = "UPDATE certificates SET name = ?, description = ?, price = ?, duration = ? WHERE id = ?";

        jdbcTemplate.update(UPDATE_SQL, certificate.getName(), certificate.getDescription(), certificate.getPrice(), certificate.getDuration(), certificate.getId());

        // Deleting all connections to tags
        jdbcTemplate.update("DELETE FROM certificates_to_tags WHERE certificate_id = ?", certificate.getId());

        // Reconnecting tags
        List<Tag> tags = certificate.getTags();

        if (tags != null) {
            // Creating new tags (without filtering on id == null, since trigger will remove all available connections and tags)
            tags.forEach(x -> {
                var tagId = tagsDAO.createOne(x);
                x.setId(tagId);
            });

            // Connecting tags to the certificate
            tags.forEach(x -> connectTag(certificate.getId(), x.getId()));
        }
    }


    @Override
    public void connectTag(Long certificateId, Long tagId) {
        jdbcTemplate.update("INSERT INTO certificates_to_tags (certificate_id, tag_id) VALUES (?, ?)", certificateId, tagId);
    }

    @Override
    @Transactional
    public boolean deleteOne(long id, long userId) {
        if (userId != -1)
            return jdbcTemplate.update("DELETE FROM certificates WHERE id = ? AND user_id = ?", id, userId) > 0;

        return jdbcTemplate.update("DELETE FROM certificates WHERE id = ?", id) > 0;
    }

    @Override
    public boolean existsById(long id) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM certificates WHERE id = ?", new Object[]{id}, Integer.class) > 0;
    }
}
