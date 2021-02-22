package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.User;
import com.epam.esm.model.UserRequestDto;
import com.epam.esm.model.UserResponseDto;
import com.epam.esm.model.UserRole;
import com.epam.esm.model.converter.impl.UserRequestConverterImpl;
import com.epam.esm.model.converter.impl.UserResponseConverterImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final UserRole DEFAULT_ROLE=new UserRole(1L,"USER");
    private final UserDao userDao;
    private final UserRequestConverterImpl userRequestConverter;
    private final UserResponseConverterImpl userResponseConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> findAll(Pagination pagination) {
        List<User> users = userDao.findAll(pagination.getLimit(), pagination.getOffset());
        return userResponseConverter.convertTo(users);
    }

    @Override
    public UserResponseDto findById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        User.class.getSimpleName()));
        return userResponseConverter.convertTo(user);
    }

    @Override
    public List<UserResponseDto> findBySurname(Pagination pagination, String surname) {
        List<User> users = userDao.findBySurname(surname, pagination.getLimit(), pagination.getOffset());
        return userResponseConverter.convertTo(users);
    }

    @Override
    public UserResponseDto findByLogin(String login) {
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, login));
        return userResponseConverter.convertTo(user);
    }

    @Override
    @Transactional
    public UserResponseDto add(UserRequestDto userDto) {
        Optional<User> userFoundByLogin = userDao.findByLogin(userDto.getLogin());
        if(userFoundByLogin.isPresent()){
            throw new ResourceAlreadyExistsException(ErrorMessageReader.USER_ALREADY_EXISTS,userDto.getLogin());
        }
        User user=userRequestConverter.convertFrom(userDto);
        user.setUserRole(DEFAULT_ROLE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userResponseConverter.convertTo(userDao.add(user));
    }
}