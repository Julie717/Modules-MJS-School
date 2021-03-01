package com.epam.esm.model.converter.impl;

import com.epam.esm.model.User;
import com.epam.esm.model.UserResponseDto;
import com.epam.esm.model.converter.CommonConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class UserResponseConverterImpl implements CommonConverter<User, UserResponseDto> {
    @Override
    public UserResponseDto convertTo(User entity) {
        return new UserResponseDto(entity.getId(), entity.getLogin(), entity.getName(), entity.getSurname());
    }

    @Override
    public User convertFrom(UserResponseDto entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserResponseDto> convertTo(List<User> entities) {
        List<UserResponseDto> usersDto = new ArrayList<>();
        entities.forEach(t -> usersDto.add(convertTo(t)));
        return usersDto;
    }

    @Override
    public List<User> convertFrom(List<UserResponseDto> entities) {
        throw new UnsupportedOperationException();
    }
}