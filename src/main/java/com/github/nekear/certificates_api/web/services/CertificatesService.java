package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.utils.UtilsManager;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificateMutationDTO;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificatesFilterRequest;
import com.github.nekear.certificates_api.web.dtos.certificates.CertificatesSortCategories;
import com.github.nekear.certificates_api.web.dtos.general.FilterResponse;
import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.repos.daos.prototypes.CertificatesDAO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CertificatesService {
    CertificatesDAO certificatesDAO;

    public CertificatesService(CertificatesDAO certificatesDAO) {
        this.certificatesDAO = certificatesDAO;
    }

    public FilterResponse<Certificate> getCertificates(CertificatesFilterRequest searchConfig) {
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
                pageable
        );

        return FilterResponse.of(certificatesPage);
    }

    public Optional<Certificate> getCertificateById(long id) {
        return certificatesDAO.findById(id);
    }

    public Optional<Certificate> createCertificate(CertificateMutationDTO certificate) {
        var newCertificate = Certificate.builder()
                .name(certificate.name())
                .description(certificate.description())
                .price(certificate.price())
                .duration(certificate.duration())
                .tags(certificate.tags())
                .build();

        var generatedId = certificatesDAO.createOne(newCertificate);

        return certificatesDAO.findById(generatedId);
    }

    public boolean deleteCertificate(long id) {
        return certificatesDAO.deleteOne(id);
    }
}
