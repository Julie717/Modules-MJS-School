package com.epam.esm.security.impl;

import com.epam.esm.model.UserRequestDto;
import com.epam.esm.security.AuthService;
import com.epam.esm.security.JwtProvider;
import com.epam.esm.util.ErrorMessageReader;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public String signIn(UserRequestDto userRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequestDto.getLogin(), userRequestDto.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new AccessDeniedException(ErrorMessageReader.INCORRECT_LOGIN_OR_PASSWORD);
        }
        return jwtProvider.generateToken(userRequestDto.getLogin());
    }
}