package com.epam.esm.dao;

import com.epam.esm.model.User;

import java.util.List;

public interface UserDao extends CommonDao<User> {
    List<User> findBySurname(String surname, Integer limit, Integer offset);
}