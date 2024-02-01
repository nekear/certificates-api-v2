package com.github.nekear.certificates_api.web.repos.prototypes;

import com.github.nekear.certificates_api.web.entities.Certificate;

import java.util.List;

public interface CertificatesDAO {
    List<Certificate> getCertificates();
}
