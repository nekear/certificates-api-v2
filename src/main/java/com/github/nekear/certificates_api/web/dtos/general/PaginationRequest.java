package com.github.nekear.certificates_api.web.dtos.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationRequest {
    private int askedPage;
    private int elementsPerPage;
}
