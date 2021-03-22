package com.epam.esm.repository;

import com.epam.esm.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    /**
     * Find user by login.
     *
     * @param login is the the login of user
     * @return the optional of user
     */
    Optional<User> findByLogin(String login);

    /**
     * Find user by surname.
     *
     * @param surname  is the surname of user
     * @param pageable is an element of pagination that consists of the number of pages and
     *                 the amount of record on each page.
     * @return the list
     */
    @Query(value = "FROM User u WHERE u.surname LIKE %:surname%")
    List<User> findBySurnameLike(@Param("surname") String surname, Pageable pageable);
}