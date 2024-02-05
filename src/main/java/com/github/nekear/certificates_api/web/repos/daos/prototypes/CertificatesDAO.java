package com.github.nekear.certificates_api.web.repos.daos.prototypes;

import com.github.nekear.certificates_api.web.entities.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificatesDAO {
    List<Certificate> getAll();

    Optional<Certificate> getById(long id);

    Long createOne(Certificate certificate);
}
