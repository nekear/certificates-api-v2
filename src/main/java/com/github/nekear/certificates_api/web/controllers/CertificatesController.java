package com.github.nekear.certificates_api.web.controllers;

import com.github.nekear.certificates_api.web.dtos.certificates.CertificateMutationDTO;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificatesFilter;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificatesSortCategories;
import com.github.nekear.certificates_api.web.dtos.general.FilterRequest;
import com.github.nekear.certificates_api.web.dtos.general.FilterResponse;
import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.services.CertificatesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/certificates")
public class CertificatesController {
    private final CertificatesService certificatesService;

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public FilterResponse<Certificate> getCertificates(@ModelAttribute Optional<FilterRequest<CertificatesFilter, CertificatesSortCategories>> searchConfig) {
        System.out.println(searchConfig);

        return certificatesService.getCertificates(searchConfig.orElse(null));
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
