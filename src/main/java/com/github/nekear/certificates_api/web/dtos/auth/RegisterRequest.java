package com.github.nekear.certificates_api.web.dtos.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
