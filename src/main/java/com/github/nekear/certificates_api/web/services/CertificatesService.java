package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.web.dtos.certificates.CertificateMutationDTO;
import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.CertificatesDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificatesService {
    CertificatesDAO certificatesDAO;

    public CertificatesService(CertificatesDAO certificatesDAO) {
        this.certificatesDAO = certificatesDAO;
    }

    public List<Certificate> getCertificates() {
        return certificatesDAO.getAll();
    }

    public Optional<Certificate> getCertificateById(long id) {
        return certificatesDAO.getById(id);
    }

    public Optional<Certificate> createCertificate(CertificateMutationDTO certificate) {
        var newCertificate = Certificate.builder()
                .name(certificate.name())
                .description(certificate.description())
                .price(certificate.price())
                .duration(certificate.duration())
                .build();

        var generatedId = certificatesDAO.createOne(newCertificate);

        return certificatesDAO.getById(generatedId);
    }
}
