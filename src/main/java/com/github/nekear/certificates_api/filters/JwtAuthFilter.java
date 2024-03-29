package com.github.nekear.certificates_api.filters;

import com.github.nekear.certificates_api.utils.JwtManager;
import com.github.nekear.certificates_api.web.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    public static final String HEADER_NAME = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtManager jwtManager;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Getting Authorization header
        var authHeader = request.getHeader(HEADER_NAME);

        // Skipping requests without Authorization header or with invalid format
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Cutting off Bearer prefix
            var token = StringUtils.substring(authHeader, BEARER_PREFIX.length());

            // Getting username from the JWT token
            var username = jwtManager.extractUsername(token);

            // If the username is not empty and the user is not authenticated, then authenticate the user
            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Getting user by the username
                var user = userService.findByUsername(username);

                if (jwtManager.isTokenValid(token, user)) {
                    // Creating empty auth context
                    var ctx = SecurityContextHolder.createEmptyContext();

                    // Creating a new auth token with `null` credentials for more security
                    var authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

                    // Enhancing the auth token with the request details for more details during the authentication process
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    ctx.setAuthentication(authToken);

                    SecurityContextHolder.setContext(ctx);
                }
            }

            filterChain.doFilter(request, response);
        }catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }
}
