package com.github.nekear.certificates_api.web.controllers;

import com.github.nekear.certificates_api.web.dtos.auth.JwtAuthResponse;
import com.github.nekear.certificates_api.web.dtos.auth.LoginRequest;
import com.github.nekear.certificates_api.web.dtos.auth.RegisterRequest;
import com.github.nekear.certificates_api.web.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest credentials) {
        return credentials.toString();
    }

    @PostMapping("/register")
    public JwtAuthResponse register(@RequestBody RegisterRequest r) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return authService.register(r);
    }
}
