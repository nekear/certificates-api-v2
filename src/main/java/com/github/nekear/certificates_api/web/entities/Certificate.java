package com.github.nekear.certificates_api.web.entities;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Data
public class Certificate {
    private Long id;
    private Integer duration;
    private Double price;
    private String name, description;
    private List<Tag> tags;
    private ZonedDateTime createdAt, updatedAt;
    private User user;
}