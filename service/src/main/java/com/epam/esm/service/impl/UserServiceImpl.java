package com.epam.esm.service.impl;

import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.*;
import com.epam.esm.model.converter.impl.UserRequestConverterImpl;
import com.epam.esm.model.converter.impl.UserResponseConverterImpl;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRequestConverterImpl userRequestConverter;
    private final UserResponseConverterImpl userResponseConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> findAll(Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage());
        List<User> users = userRepository.findAll(pageable).getContent();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.RESOURCES_NOT_FOUND, User.class.getSimpleName());
        }
        return userResponseConverter.convertTo(users);
    }

    @Override
    public UserResponseDto findById(Long id, Long idUser, String role) {
        if (Role.valueOf(role) == Role.ROLE_USER && !id.equals(idUser)) {
            throw new AccessDeniedException(ErrorMessageReader.ACCESS_DENIED);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        User.class.getSimpleName()));
        return userResponseConverter.convertTo(user);
    }

    @Override
    public List<UserResponseDto> findBySurname(Pagination pagination, String surname) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage());
        List<User> users = userRepository.findBySurnameLike(surname, pageable);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.RESOURCES_NOT_FOUND, User.class.getSimpleName());
        }
        return userResponseConverter.convertTo(users);
    }

    @Override
    @Transactional
    public UserResponseDto add(UserRequestDto userDto) {
        Optional<User> userFoundByLogin = userRepository.findByLogin(userDto.getLogin());
        if (userFoundByLogin.isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.USER_ALREADY_EXISTS, userDto.getLogin());
        }
        User user = userRequestConverter.convertFrom(userDto);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userResponseConverter.convertTo(userRepository.save(user));
    }
}