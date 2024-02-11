package com.github.nekear.certificates_api.web.dtos.general;

import lombok.*;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SortCriteria {
    private String column;
    private Sort.Direction direction;
}
