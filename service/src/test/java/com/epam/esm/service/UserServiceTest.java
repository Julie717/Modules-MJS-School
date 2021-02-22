package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.User;
import com.epam.esm.model.UserResponseDto;
import com.epam.esm.model.converter.impl.PurchaseResponseConverterImpl;
import com.epam.esm.model.converter.impl.UserResponseConverterImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.util.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Spy
    private final PurchaseResponseConverterImpl purchaseResponseConverter = new PurchaseResponseConverterImpl();

    @Spy
    private final UserResponseConverterImpl userConverter = new UserResponseConverterImpl(purchaseResponseConverter);

    @Test
    void findAllTest() {
        List<User> users = new ArrayList<>();
        Mockito.when(userDao.findAll(anyInt(), anyInt())).thenReturn(users);
        Pagination pagination = new Pagination(10, 2);
        List<UserResponseDto> expected = new ArrayList<>();

        List<UserResponseDto> actual = userService.findAll(pagination);

        verify(userConverter).convertTo(users);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestPositive() {
        Long idUser = 5L;
        User user = new User();
        user.setId(idUser);
        user.setName("Ivan");
        user.setSurname("Ivanov");
        Mockito.when(userDao.findById(idUser)).thenReturn(Optional.of(user));
        UserResponseDto expected = new UserResponseDto(idUser, "Ivan", "Ivanov", null);

        UserResponseDto actual = userService.findById(idUser);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(userDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(id));
    }

    @Test
    void findBySurnameTestPositive() {
        List<UserResponseDto> expected = new ArrayList<>();
        expected.add(new UserResponseDto(5L, "Ivan", "Ivanov", null));
        expected.add(new UserResponseDto(12L, "Nick", "Sidorov", null));
        Pagination pagination = new Pagination(10, 2);
        String surname = "ov";
        List<User> users = new ArrayList<>();
        users.add(new User(5L, "Ivan", "Ivanov", null));
        users.add(new User(12L, "Nick", "Sidorov", null));
        Mockito.when(userDao.findBySurname(surname, 10, 2)).thenReturn(users);

        List<UserResponseDto> actual = userService.findBySurname(pagination, surname);

        assertEquals(expected, actual);
    }

    @Test
    void findBySurnameTestEmptyList() {
        Pagination pagination = new Pagination(10, 2);
        String surname = "ov";
        Mockito.when(userDao.findBySurname(surname, 10, 2)).thenReturn(new ArrayList<>());
        List<UserResponseDto> expected = new ArrayList<>();

        List<UserResponseDto> actual = userService.findBySurname(pagination, surname);

        assertEquals(expected, actual);
    }
}