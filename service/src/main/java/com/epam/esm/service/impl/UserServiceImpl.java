package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.User;
import com.epam.esm.model.UserDto;
import com.epam.esm.model.converter.impl.UserConverterImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ErrorMessageReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserConverterImpl userConverter;

    public UserServiceImpl(UserDao userDao, UserConverterImpl userConverter) {
        this.userDao = userDao;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return userConverter.convertTo(users);
    }

    @Override
    public UserDto findById(Long id) {
        User User = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        User.class.getSimpleName()));
        return userConverter.convertTo(User);
    }
}