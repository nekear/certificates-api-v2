package com.github.nekear.certificates_api.web.controllers;

import com.github.nekear.certificates_api.exceptions.FlowException;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificateMutationDTO;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificatesFilterRequest;
import com.github.nekear.certificates_api.web.dtos.general.FilterResponse;
import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.services.CertificatesService;
import org.intellij.lang.annotations.Flow;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/certificates")
public class CertificatesController {
    private final CertificatesService certificatesService;

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public FilterResponse<Certificate> getCertificates(@ModelAttribute Optional<CertificatesFilterRequest> searchConfig) {
        return certificatesService.getCertificates(searchConfig.orElse(null));
    }

    @PostMapping
    public Certificate createCertificate(@RequestBody CertificateMutationDTO certificate) {
        return certificatesService.createCertificate(certificate)
                .orElseThrow(() -> new FlowException("Unable to create certificate", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/{id}")
    public Certificate getCertificateById(@PathVariable("id") long id) {
        return certificatesService.getCertificateById(id)
                .orElseThrow(() -> new FlowException("Certificate not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable("id") long id) {
        if(!certificatesService.deleteCertificate(id))
            throw new FlowException("Unable to delete certificate", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
