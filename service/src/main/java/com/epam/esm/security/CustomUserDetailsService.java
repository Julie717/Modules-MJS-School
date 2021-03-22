package com.epam.esm.security;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.CustomUserDetails;
import com.epam.esm.model.User;
import com.epam.esm.model.converter.impl.CustomUserDetailsConverterImpl;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.util.ErrorMessageReader;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomUserDetailsConverterImpl customUserDetailsConverter;

    @Override
    public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, login));
        return customUserDetailsConverter.convertTo(user);
    }
}