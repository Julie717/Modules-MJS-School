package com.epam.esm.model.converter.impl;

import com.epam.esm.model.User;
import com.epam.esm.model.converter.CommonConverter;
import com.epam.esm.model.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class CustomUserDetailsConverterImpl implements CommonConverter<User, CustomUserDetails> {
    @Override
    public CustomUserDetails convertTo(User entity) {
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(entity.getId());
        userDetails.setLogin(entity.getLogin());
        userDetails.setPassword(entity.getPassword());
        userDetails.setGrantedAuthorities(Collections.singletonList(new SimpleGrantedAuthority(entity.getRole().name())));
        return userDetails;
    }

    @Override
    public User convertFrom(CustomUserDetails entity) {
        return null;
    }

    @Override
    public List<CustomUserDetails> convertTo(List<User> entities) {
        return null;
    }

    @Override
    public List<User> convertFrom(List<CustomUserDetails> entities) {
        return null;
    }
}
