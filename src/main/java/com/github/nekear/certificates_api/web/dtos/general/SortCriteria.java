package com.github.nekear.certificates_api.web.dtos.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@Builder
@ToString
public class SortCriteria<T> {
    private T column;
    private Sort.Direction direction;
}
