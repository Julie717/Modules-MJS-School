package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = PersistenceConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllTest() {
        Pageable pageable = PageRequest.of(0, 3);

        int actualAmountOfUsers = userRepository.findAll(pageable).getContent().size();

        int expectedAmountOfUsers = 3;
        assertEquals(expectedAmountOfUsers, actualAmountOfUsers);
    }

    @Test
    void findByIdTest() {
        Long id = 2L;

        String actualSurname = userRepository.findById(id).get().getSurname();

        String expectedSurname = "Petrov";
        assertEquals(expectedSurname, actualSurname);
    }

    @Test
    void findBySurnameTest() {
        String surname = "ov";
        Pageable pageable = PageRequest.of(0, 3);

        int actualAmountOfUsers = userRepository.findBySurnameLike(surname, pageable).size();

        int expectedAmountOfUsers = 3;
        assertEquals(expectedAmountOfUsers, actualAmountOfUsers);
    }
}