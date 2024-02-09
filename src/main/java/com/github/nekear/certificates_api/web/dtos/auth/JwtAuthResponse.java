package com.github.nekear.certificates_api.web.dtos.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
}
