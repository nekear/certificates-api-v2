package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.web.dtos.auth.JwtAuthResponse;
import com.github.nekear.certificates_api.web.dtos.auth.LoginRequest;
import com.github.nekear.certificates_api.web.dtos.auth.RegisterRequest;
import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.entities.User;
import com.github.nekear.certificates_api.utils.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtManager jwtManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthResponse register(RegisterRequest r) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var user = User.builder()
                .username(r.getUsername())
                .password(passwordEncoder.encode(r.getPassword()))
                .role(Role.USER)
                .build();

        userService.create(user);

        var jwt = jwtManager.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

    public JwtAuthResponse login(LoginRequest r) throws NoSuchAlgorithmException, InvalidKeySpecException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(r.getUsername(), r.getPassword())
        );

        var user = userService.findByUsername(r.getUsername());

        var jwt = jwtManager.generateToken(user);

        return new JwtAuthResponse(jwt);
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.findByUsername(username);
    }
}
