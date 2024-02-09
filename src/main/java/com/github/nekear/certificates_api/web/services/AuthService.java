package com.github.nekear.certificates_api.web.services;

import com.github.nekear.certificates_api.web.dtos.auth.JwtAuthResponse;
import com.github.nekear.certificates_api.web.dtos.auth.RegisterRequest;
import com.github.nekear.certificates_api.web.entities.Role;
import com.github.nekear.certificates_api.web.entities.User;
import com.github.nekear.certificates_api.web.utils.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class AuthService {
    public final UserService userService;
    public final JwtManager jwtManager;

    public JwtAuthResponse register(RegisterRequest r) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var user = User.builder()
                .username(r.username)
                .password(r.password)
                .role(Role.USER)
                .build();

        userService.create(user);

        var jwt = jwtManager.generateToken(user);
        return new JwtAuthResponse(jwt);
    }
}
