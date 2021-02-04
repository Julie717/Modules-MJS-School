package com.epam.esm.service;

import com.epam.esm.model.UserDto;
import com.epam.esm.util.Pagination;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(Pagination pagination);

    UserDto findById(Long id);

    List<UserDto> findBySurname(Pagination pagination, String surname);
}