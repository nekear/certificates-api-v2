package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.exceptions.FlowException;
import com.github.nekear.certificates_api.utils.UtilsManager;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificateMutationDTO;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificatesFilterRequest;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificatesSortCategories;
import com.github.nekear.certificates_api.web.dtos.general.FilterResponse;
import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.CertificatesDAO;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Flow;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificatesService {
    private final CertificatesDAO certificatesDAO;
    private final AuthService authService;


    public FilterResponse<Certificate> getCertificates(CertificatesFilterRequest searchConfig) {
        var user = authService.getCurrentUser();

        String mainSearch = null, tagsSearch = null;
        Pageable pageable = Pageable.unpaged();

        if (searchConfig != null) {
            if(searchConfig.getFilters() != null) {
                var tag = searchConfig.getFilters().getTags();
                var main = searchConfig.getFilters().getMain();

                if (main != null && !main.isEmpty())
                    mainSearch = UtilsManager.clean(main);

                if (tag != null && !tag.isEmpty()) {
                    tagsSearch = UtilsManager.clean(tag);
                }
            }

            var orderBy = searchConfig.getSorting(Map.of(CertificatesSortCategories.date.toString(), "created_at"));
            pageable = searchConfig.formPageable(orderBy);
        }

        var certificatesPage = certificatesDAO.findAll(
                mainSearch,
                tagsSearch,
                pageable,
                user.getRole() == Role.ADMIN ? -1 : user.getId()
        );

        return FilterResponse.of(certificatesPage);
    }

    public Optional<Certificate> updateCertificate(long id, CertificateMutationDTO certificate) {
        var user = authService.getCurrentUser();

        if(!this.existsById(id))
            throw new FlowException("Certificate not found", HttpStatus.NOT_FOUND);

        var updatedCertificate = Certificate.builder()
                .id(id)
                .name(certificate.name())
                .description(certificate.description())
                .price(certificate.price())
                .duration(certificate.duration())
                .tags(certificate.tags())
                .build();

        certificatesDAO.updateOne(updatedCertificate);

        return certificatesDAO.findById(id, user.getRole() == Role.ADMIN ? -1 : user.getId());
    }

    public Optional<Certificate> createCertificate(CertificateMutationDTO certificate) {
        // Getting current user
        var currentUser = authService.getCurrentUser();

        var newCertificate = Certificate.builder()
                .name(certificate.name())
                .description(certificate.description())
                .price(certificate.price())
                .duration(certificate.duration())
                .tags(certificate.tags())
                .user(currentUser)
                .build();

        var generatedId = certificatesDAO.createOne(newCertificate);

        return certificatesDAO.findById(generatedId, currentUser.getId());
    }

    public boolean existsById(long id) {
        return certificatesDAO.existsById(id);
    }

    public Optional<Certificate> getCertificateById(long id) {
        var user = authService.getCurrentUser();

        return certificatesDAO.findById(id, user.getRole() == Role.ADMIN ? -1 : user.getId());
    }

    public boolean deleteCertificate(long id) {
        var user = authService.getCurrentUser();

        if(!this.existsById(id))
            throw new FlowException("Certificate not found", HttpStatus.NOT_FOUND);

        return certificatesDAO.deleteOne(id, user.getRole() == Role.ADMIN ? -1 : user.getId());
    }
}
