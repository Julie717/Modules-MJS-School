package com.epam.esm.dao.impl;

import com.epam.esm.dao.Queries;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.model.Purchase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class PurchaseDaoImpl implements PurchaseDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Purchase> findAll(Integer limit, Integer offset) {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_PURCHASES, Purchase.class)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Optional<Purchase> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Purchase.class, id));
    }

    @Override
    public List<Purchase> findByIdGiftCertificate(Long idGiftCertificate) {
        Query query = entityManager.createQuery(Queries.SELECT_PURCHASE_BY_ID_GIFT_CERTIFICATE, Purchase.class);
        query.setParameter(1, idGiftCertificate);
        return query.getResultList();
    }

    @Override
    public List<Purchase> findByIdUser(Long idUser, Integer limit, Integer offset) {
        Query query = entityManager.createQuery(Queries.SELECT_PURCHASE_BY_ID_USER, Purchase.class)
         .setFirstResult(offset).setMaxResults(limit);
        query.setParameter(1, idUser);
        return query.getResultList();
    }

    @Override
    public Purchase add(Purchase entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(Purchase entity) {
        throw new UnsupportedOperationException();
    }
}