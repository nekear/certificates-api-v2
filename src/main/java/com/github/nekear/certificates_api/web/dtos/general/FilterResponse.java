package com.github.nekear.certificates_api.web.dtos.general;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterResponse<T> {
    private List<T> payload;

    private PaginationResponse pagination;

    public static <T> FilterResponse<T> of(Page<T> page) {
        return new FilterResponse<>(page.getContent(), new PaginationResponse(page.getTotalElements()));
    }
}
