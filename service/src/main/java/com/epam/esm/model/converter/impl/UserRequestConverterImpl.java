package com.epam.esm.model.converter.impl;

import com.epam.esm.model.User;
import com.epam.esm.model.UserRequestDto;
import com.epam.esm.model.converter.CommonConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class UserRequestConverterImpl implements CommonConverter<User, UserRequestDto> {

    @Override
    public UserRequestDto convertTo(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User convertFrom(UserRequestDto entity) {
        User user = new User();
        user.setLogin(entity.getLogin());
        user.setPassword(entity.getPassword());
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        return user;
    }

    @Override
    public List<UserRequestDto> convertTo(List<User> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> convertFrom(List<UserRequestDto> entities) {
        List<User> users = new ArrayList<>();
        entities.forEach(t -> users.add(convertFrom(t)));
        return users;
    }
}