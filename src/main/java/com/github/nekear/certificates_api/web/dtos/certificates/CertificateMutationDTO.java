package com.github.nekear.certificates_api.web.dtos.certificates;

public record CertificateMutationDTO(
        String name,
        String description,
        Double price,
        Integer duration
) {
}
