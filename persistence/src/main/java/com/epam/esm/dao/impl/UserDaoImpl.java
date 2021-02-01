package com.epam.esm.dao.impl;

import com.epam.esm.dao.Queries;
import com.epam.esm.dao.UserDao;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_USERS, User.class);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }


    @Override
    public Optional<User> findByName(String name) {
        Optional<User> user;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_USER_BY_NAME);
            query.setParameter(1, name);
            user = Optional.ofNullable((User) query.getSingleResult());
        } catch (NoResultException ex) {
            user = Optional.empty();
        }
        return user;
    }

    @Override
    public User add(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException();
    }
}