package com.epam.esm.service;

import com.epam.esm.model.UserRequestDto;
import com.epam.esm.model.UserResponseDto;
import com.epam.esm.util.Pagination;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Find all users.
     *
     * @param pagination contains limit and offset for search
     * @return the list of user DTO
     */
    List<UserResponseDto> findAll(Pagination pagination);

    /**
     * Find user by id.
     *
     * @param id is the id of user
     * @return the user DTO
     */
    UserResponseDto findById(Long id);

    /**
     * Find users by surname.
     *
     * @param pagination contains limit and offset for search
     * @param surname    is the user's surname
     * @return the list of users
     */
    List<UserResponseDto> findBySurname(Pagination pagination, String surname);

    UserResponseDto findByLogin(String login);

    UserResponseDto add(UserRequestDto userDto);
}