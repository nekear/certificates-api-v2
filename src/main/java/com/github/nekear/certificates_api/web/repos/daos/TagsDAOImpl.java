package com.github.nekear.certificates_api.web.repos.daos;

import com.github.nekear.certificates_api.web.entities.Tag;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.TagsDAO;
import com.github.nekear.certificates_api.web.repos.mappers.TagsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagsDAOImpl implements TagsDAO {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public long createOne(Tag tag) {
        var tagInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tags")
                .usingGeneratedKeyColumns("id");

        return tagInsert.executeAndReturnKey(Map.of("name", tag.getName())).longValue();
    }

    @Override
    public Optional<Tag> findById(long id) {
        var GET_SQL = "SELECT * FROM tags WHERE id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(GET_SQL, new Long[]{id}, new TagsMapper()));
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("SELECT * FROM tags", new TagsMapper());
    }
}
