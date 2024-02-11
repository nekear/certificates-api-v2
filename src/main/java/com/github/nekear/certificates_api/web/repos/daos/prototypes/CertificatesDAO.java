package com.github.nekear.certificates_api.web.repos.daos.prototypes;

import com.github.nekear.certificates_api.web.entities.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CertificatesDAO {
    Page<Certificate> findAll(String mainSearch, String tagsSearch, Pageable pageable, long userId);

    Optional<Certificate> findById(long id, long userId);

    Long createOne(Certificate certificate);

    void updateOne(Certificate certificate);

    void connectTag(Long certificateId, Long tagId);

    boolean deleteOne(long id, long userId);

    boolean existsById(long id);
}
