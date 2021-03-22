package com.epam.esm.security;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.CustomUserDetails;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.model.converter.impl.CustomUserDetailsConverterImpl;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    private static final User USER;
    private static final CustomUserDetails USER_RESPONSE;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private final CustomUserDetailsConverterImpl customUserDetailsConverter = new CustomUserDetailsConverterImpl();

    static {
        USER = new User(20L, "ivanov_i", "", "Ivan", "Ivanov", Role.ROLE_USER, null);
        Collection<SimpleGrantedAuthority> grantedAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        USER_RESPONSE = new CustomUserDetails(20L, "ivanov_i", "", grantedAuthorities);
    }

    @Test
    void loadUserByUsernameTestPositive() {
        String login = "ivanov_i";
        Mockito.when(userRepository.findByLogin(login)).thenReturn(Optional.of(USER));
        CustomUserDetails expected = USER_RESPONSE;

        CustomUserDetails actual = customUserDetailsService.loadUserByUsername(login);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        String login = "ivanov_i";
        Mockito.when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(login));
    }
}