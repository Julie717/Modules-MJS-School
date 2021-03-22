package com.epam.esm.controller;

import com.epam.esm.model.UserRequestDto;
import com.epam.esm.model.UserResponseDto;
import com.epam.esm.security.impl.AuthServiceImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.util.AuthenticationResponse;
import com.epam.esm.validator.ValidationGroup;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * The type Authentication controller is used to receive REST API requests
 * with mapping "/" for providing user's authentication.
 */
@RestController
@AllArgsConstructor
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@Validated
public class AuthenticationController {
    private final UserService userService;
    private final AuthServiceImpl authService;

    /**
     * Sign up is used to sign up for application and receive access as User.
     *
     * @param user is the main user's parameters that are required for registration
     * @return the user response dto
     */
    @PostMapping(value = "/signUp")
    @PreAuthorize("permitAll()")
    public UserResponseDto signUp(@Validated(ValidationGroup.CreateValidation.class)
                                  @RequestBody UserRequestDto user) {
        return userService.add(user);
    }

    /**
     * Sign in authentication response.
     *
     * @param userRequestDto is the main user's parameters that are required for authentication
     * @return the authentication response
     */
    @PostMapping(value = "/signIn")
    @PreAuthorize("permitAll()")
    public AuthenticationResponse signIn(@Validated(ValidationGroup.SignIn.class)
                                         @RequestBody UserRequestDto userRequestDto) {
        return new AuthenticationResponse(authService.signIn(userRequestDto));
    }
}