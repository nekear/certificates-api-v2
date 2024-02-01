package com.github.nekear.certificates_api.web.controllers;

import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.services.CertificatesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class CertificatesController {
    private final CertificatesService certificatesService;

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public List<Certificate> getCertificates() {
        return certificatesService.getCertificates();
    }
}
