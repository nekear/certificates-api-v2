package com.github.nekear.certificates_api.web.controllers;

import com.github.nekear.certificates_api.web.dtos.certificates.CertificateMutationDTO;
import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.services.CertificatesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificatesController {
    private final CertificatesService certificatesService;

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public List<Certificate> getCertificates() {
        return certificatesService.getCertificates();
    }

    @PostMapping
    public Certificate createCertificate(@RequestBody CertificateMutationDTO certificate) {
        // TODO: handle the case when certificate is not created
        return certificatesService.createCertificate(certificate).orElse(null);
    }

    @GetMapping("/{id}")
    public Certificate getCertificateById(@PathVariable("id") int id) {
        // TODO: thrown an exception if certificate is not found
        return certificatesService.getCertificateById(id).orElse(null);
    }
}
