package com.github.nekear.certificates_api.web.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeResponse {
    private String username;
    private String role;
}
