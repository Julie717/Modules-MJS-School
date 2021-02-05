package com.epam.esm.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    void findAllTest() {
        Integer limit = 10;
        Integer offset = 0;

        int actualAmountOfUsers = userDao.findAll(limit, offset).size();

        int expectedAmountOfUsers = 3;
        assertEquals(expectedAmountOfUsers, actualAmountOfUsers);
    }

    @Test
    void findByIdTest() {
        Long id = 2L;

        String actualSurname = userDao.findById(id).get().getSurname();

        String expectedSurname = "Petrov";
        assertEquals(expectedSurname, actualSurname);
    }

    @Test
    void findBySurnameTest() {
        String surname = "ov";
        Integer limit = 10;
        Integer offset = 0;

        int actualAmountOfUsers = userDao.findBySurname(surname, limit, offset).size();

        int expectedAmountOfUsers = 3;
        assertEquals(expectedAmountOfUsers, actualAmountOfUsers);
    }
}