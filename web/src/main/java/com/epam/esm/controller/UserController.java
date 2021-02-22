package com.epam.esm.controller;

import com.epam.esm.model.CustomUserDetails;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.model.UserResponseDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.HateoasLinkBuilder;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponseDto> findAllUsers(@Valid @NotNull Pagination pagination,
                                              @Size(min = 1, max = 50) String surname) {
        List<UserResponseDto> users;
        if (surname == null || surname.isEmpty()) {
            users = userService.findAll(pagination);
        } else {
            users = userService.findBySurname(pagination, surname);
        }
        HateoasLinkBuilder.buildUsersLink(users);
        return users;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public UserResponseDto findById(@PathVariable @Positive Long id, SecurityContextHolderAwareRequestWrapper requestWrapper) {
        if (!requestWrapper.isUserInRole(ROLE_ADMIN)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=(UsernamePasswordAuthenticationToken)requestWrapper.getUserPrincipal();
            CustomUserDetails userDetails=(CustomUserDetails)usernamePasswordAuthenticationToken.getPrincipal();
            if (!userDetails.getId().equals(id)){
                throw new AccessDeniedException(ErrorMessageReader.ACCESS_DENIED);
            }
        }
        UserResponseDto user = userService.findById(id);
        HateoasLinkBuilder.buildUserLink(user);
        return user;
    }
}