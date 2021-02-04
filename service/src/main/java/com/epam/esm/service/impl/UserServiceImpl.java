package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.User;
import com.epam.esm.model.UserDto;
import com.epam.esm.model.converter.impl.UserConverterImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserConverterImpl userConverter;

    @Override
    public List<UserDto> findAll(Pagination pagination) {
        List<User> users = userDao.findAll(pagination.getLimit(), pagination.getOffset());
        return userConverter.convertTo(users);
    }

    @Override
    public UserDto findById(Long id) {
        User User = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        User.class.getSimpleName()));
        return userConverter.convertTo(User);
    }

    @Override
    public List<UserDto> findBySurname(Pagination pagination, String surname) {
        List<User> users = userDao.findBySurname(surname, pagination.getLimit(), pagination.getOffset());
        return userConverter.convertTo(users);
    }
}