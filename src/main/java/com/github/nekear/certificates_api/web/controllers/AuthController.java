package com.github.nekear.certificates_api.web.controllers;

import com.github.nekear.certificates_api.web.dtos.auth.JwtAuthResponse;
import com.github.nekear.certificates_api.web.dtos.auth.LoginRequest;
import com.github.nekear.certificates_api.web.dtos.auth.MeResponse;
import com.github.nekear.certificates_api.web.dtos.auth.RegisterRequest;
import com.github.nekear.certificates_api.web.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtAuthResponse login(@RequestBody LoginRequest credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return authService.login(credentials);
    }

    @PostMapping("/register")
    public JwtAuthResponse register(@RequestBody RegisterRequest r) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return authService.register(r);
    }

    @GetMapping("/me")
    public MeResponse me() {
        var user = authService.getCurrentUser();

        return MeResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
