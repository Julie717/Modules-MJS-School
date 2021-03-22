package com.epam.esm.security;

import com.epam.esm.model.UserRequestDto;

/**
 * The interface Auth service.
 */
public interface AuthService {
    /**
     * SignIn is used to receive a token during authentication.
     *
     * @param userRequestDto is the user, that contains the main information
     *                       for authentication (login and password)
     * @return token in String format
     */
    String signIn(UserRequestDto userRequestDto);
}