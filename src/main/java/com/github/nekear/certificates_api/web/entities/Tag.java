package com.github.nekear.certificates_api.web.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag {
    private Long id;
    private String name;
}
