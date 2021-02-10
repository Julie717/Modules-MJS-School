package com.epam.esm.dao.impl;

import com.epam.esm.dao.Queries;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
       /* Optional<Purchase> purchase;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_PURCHASE_BY_ID);
            query.setParameter(1, id);
            purchase = Optional.ofNullable((Purchase) query.getSingleResult());
        } catch (NoResultException ex) {
            purchase = Optional.empty();
        }
        return purchase;
*/
       return Optional.ofNullable(entityManager.find(Purchase.class, id));
    }

    @Override
    public List<Purchase> findByIdGiftCertificate(Long idGiftCertificate) {
        Query query = entityManager.createQuery(Queries.SELECT_PURCHASE_BY_ID_GIFT_CERTIFICATE, Purchase.class);
        query.setParameter(1,idGiftCertificate);
        return query.getResultList();
    }

    @Override
    public Purchase add(Purchase entity) {
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