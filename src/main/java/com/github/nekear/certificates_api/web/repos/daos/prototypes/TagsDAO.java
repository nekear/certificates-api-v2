package com.github.nekear.certificates_api.web.repos.daos.prototypes;

import com.github.nekear.certificates_api.web.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface TagsDAO {
    long createOne(Tag tag);

    Optional<Tag> findById(long id);

    List<Tag> findAll();
}
