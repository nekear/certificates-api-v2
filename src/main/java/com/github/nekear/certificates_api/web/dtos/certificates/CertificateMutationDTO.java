package com.github.nekear.certificates_api.web.dtos.certificates;

import com.github.nekear.certificates_api.web.entities.Tag;

import java.util.List;

public record CertificateMutationDTO(
        String name,
        String description,
        Double price,
        Integer duration,
        List<Tag> tags
) {
}
