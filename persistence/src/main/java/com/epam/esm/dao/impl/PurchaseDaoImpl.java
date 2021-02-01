package com.epam.esm.dao.impl;

import com.epam.esm.dao.Queries;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {
    private final EntityManager entityManager;

    @Autowired
    public PurchaseDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Purchase> findAll() {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_PURCHASES, Purchase.class);
        return query.getResultList();
    }

    @Override
    public Optional<Purchase> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Purchase.class, id));
    }

    @Override
    public Purchase add(Purchase entity) {
   //     System.out.println(entity);
        entityManager.persist(entity);
        Long id = (Long) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
        entity.setId(id);
        return entity;
    }

    @Override
    public void delete(Purchase entity) {
        throw new UnsupportedOperationException();
    }
}