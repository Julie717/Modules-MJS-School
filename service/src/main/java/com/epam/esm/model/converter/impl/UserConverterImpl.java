package com.epam.esm.model.converter.impl;

import com.epam.esm.model.*;
import com.epam.esm.model.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverterImpl implements CommonConverter<User, UserDto> {
    private final PurchaseConverterImpl purchaseConverter;

    public UserConverterImpl(PurchaseConverterImpl purchaseConverter) {
        this.purchaseConverter = purchaseConverter;
    }

    @Override
    public UserDto convertTo(User entity) {
        List<PurchaseRequestDto> purchases = null;
        if (entity.getPurchases() != null) {
            purchases = purchaseConverter.convertTo(entity.getPurchases());
        }
        return new UserDto(entity.getId(), entity.getName(), entity.getSurname(),purchases);
    }

    @Override
    public User convertFrom(UserDto entity) {
        List<Purchase> purchases = null;
        if (entity.getPurchases() != null) {
            purchases = purchaseConverter.convertFrom(entity.getPurchases());
        }
        return new User(entity.getId(), entity.getName(), entity.getSurname(), purchases);
    }

    @Override
    public List<UserDto> convertTo(List<User> entities) {
        List<UserDto> usersDto = new ArrayList<>();
        entities.forEach(t -> usersDto.add(convertTo(t)));
        return usersDto;
    }

    @Override
    public List<User> convertFrom(List<UserDto> entities) {
        List<User> users = new ArrayList<>();
        entities.forEach(t -> users.add(convertFrom(t)));
        return users;
    }
}