package com.github.nekear.certificates_api.web.dtos.auth;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}
