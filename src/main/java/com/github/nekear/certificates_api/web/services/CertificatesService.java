package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.repos.prototypes.CertificatesDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificatesService {
    CertificatesDAO certificatesDAO;

    public CertificatesService(CertificatesDAO certificatesDAO) {
        this.certificatesDAO = certificatesDAO;
    }

    public List<Certificate> getCertificates() {
        return certificatesDAO.getCertificates();
    }
}
