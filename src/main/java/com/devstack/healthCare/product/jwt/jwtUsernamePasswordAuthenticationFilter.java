package com.devstack.healthCare.product.jwt;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class jwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final JwtConfig jwtConfig;

    private final SecretKey secretKey;


    public jwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationRequest
                    usernamePasswordAuthenticationRequest = new ObjectMapper().readValue(
                            request.getInputStream(),
                    UsernamePasswordAuthenticationRequest.class);

            Authentication  authentication = new UsernamePasswordAuthenticationToken(
                    usernamePasswordAuthenticationRequest.getUsername(),
                    usernamePasswordAuthenticationRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);

            return authenticate;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
