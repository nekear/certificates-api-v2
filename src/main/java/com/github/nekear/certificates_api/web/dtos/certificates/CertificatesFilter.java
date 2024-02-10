package com.github.nekear.certificates_api.web.dtos.certificates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificatesFilter {
    // Searching by name and description
    private String main;

    // Searching by tags
    private String tags;
}
