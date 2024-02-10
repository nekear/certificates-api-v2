package com.github.nekear.certificates_api.web.dtos.general;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationResponse {
    private long totalElements;
}