package com.epam.esm.dao.impl;

import com.epam.esm.dao.Queries;
import com.epam.esm.dao.UserDao;
import com.epam.esm.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
@Log4j2
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_USERS, User.class)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> user;
        try {
            TypedQuery<User> query = entityManager.createQuery(Queries.SELECT_USER_BY_LOGIN, User.class);
            query.setParameter(1, login);
            user = Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException ex) {
            log.log(Level.ERROR, ex.getMessage());
            user = Optional.empty();
        }
        return user;
    }


    @Override
    public List<User> findBySurname(String surname, Integer limit, Integer offset) {
        Query query = entityManager.createQuery(Queries.SELECT_USER_BY_SURNAME, User.class)
                .setParameter(1, surname)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public User add(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException();
    }
}