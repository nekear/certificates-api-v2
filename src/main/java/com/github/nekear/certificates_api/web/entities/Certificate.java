package com.github.nekear.certificates_api.web.entities;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class Certificate {
    private Long id;
    private Integer duration;
    private Double price;
    private String name, description;
    private ZonedDateTime createdAt, updatedAt;
}