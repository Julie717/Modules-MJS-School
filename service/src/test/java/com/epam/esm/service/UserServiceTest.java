package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.model.UserResponseDto;
import com.epam.esm.model.converter.impl.UserResponseConverterImpl;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.util.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final User USER;
    private static final UserResponseDto USER_RESPONSE_DTO;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private final UserResponseConverterImpl userConverter = new UserResponseConverterImpl();

    static {
        USER = new User(20L, "ivanov_i", "", "Ivan", "Ivanov", Role.ROLE_USER, null);
        USER_RESPONSE_DTO = new UserResponseDto(20L, "ivanov_i", "Ivan", "Ivanov");
    }

    @Test
    void findAllTestPositive() {
        List<User> users = new ArrayList<>();
        users.add(USER);
        Mockito.when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(users));
        Pagination pagination = new Pagination(10, 2);
        List<UserResponseDto> expected = new ArrayList<>();
        expected.add(USER_RESPONSE_DTO);

        List<UserResponseDto> actual = userService.findAll(pagination);

        verify(userConverter).convertTo(users);
        assertEquals(expected, actual);
    }

    @Test
    void findAllTestNegative() {
        Mockito.when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
        Pagination pagination = new Pagination(10, 2);

        assertThrows(ResourceNotFoundException.class, () -> userService.findAll(pagination));
    }

    @Test
    void findByIdTestPositive() {
        Long idUser = 5L;
        User user = new User();
        user.setId(idUser);
        user.setLogin("ivanov_123");
        user.setName("Ivan");
        user.setSurname("Ivanov");
        Mockito.when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        UserResponseDto expected = new UserResponseDto(idUser, "ivanov_123", "Ivan", "Ivanov");

        UserResponseDto actual = userService.findById(idUser, idUser, "ROLE_USER");

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(id, id, "ROLE_USER"));
    }

    @Test
    void findBySurnameTestPositive() {
        List<UserResponseDto> expected = new ArrayList<>();
        expected.add(USER_RESPONSE_DTO);
        Pagination pagination = new Pagination(10, 2);
        String surname = "ov";
        List<User> users = new ArrayList<>();
        users.add(USER);
        Pageable pageable = PageRequest.of(10, 2);
        Mockito.when(userRepository.findBySurnameLike(surname, pageable)).thenReturn(users);

        List<UserResponseDto> actual = userService.findBySurname(pagination, surname);

        assertEquals(expected, actual);
    }

    @Test
    void findBySurnameTestNegative() {
        Pagination pagination = new Pagination(10, 2);
        String surname = "ov";
        Pageable pageable = PageRequest.of(10, 2);
        Mockito.when(userRepository.findBySurnameLike(surname, pageable)).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> userService.findBySurname(pagination, surname));
    }
}