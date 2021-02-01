package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.Queries;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final EntityManager entityManager;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll() {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_GIFT_CERTIFICATES, GiftCertificate.class);
        return query.getResultList();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> giftCertificate;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_GIFT_CERTIFICATE_BY_NAME);
            query.setParameter(1, name);
            giftCertificate = Optional.ofNullable((GiftCertificate) query.getSingleResult());
        } catch (NoResultException ex) {
            giftCertificate = Optional.empty();
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findByParameters(String queryLastPart) {
        Query query = entityManager.createQuery(queryLastPart, GiftCertificate.class);
        return (List<GiftCertificate>) query.getResultStream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificate> findByTagId(Long idTag) {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_GIFT_CERTIFICATES_BY_TAG_ID, GiftCertificate.class);
        query.setParameter(1, idTag);
        return (List<GiftCertificate>) query.getResultStream().distinct().collect(Collectors.toList());
    }

    @Override
    public Optional<GiftCertificate> findByTagIdInGiftCertificate(Long idGiftCertificate, Long idTag) {
        Optional<GiftCertificate> giftCertificate;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_GIFT_CERTIFICATE_BY_TAG_ID);
            query.setParameter(1, idGiftCertificate);
            query.setParameter(2, idTag);
            giftCertificate = Optional.ofNullable((GiftCertificate) query.getSingleResult());
        } catch (NoResultException ex) {
            giftCertificate = Optional.empty();
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        Long id = (Long) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(giftCertificate);
        giftCertificate.setId(id);
        return giftCertificate;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    @Override
    public void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, idGiftCertificate);
        Tag tag = giftCertificate.getTags().stream().filter(t -> t.getId().equals(idTag)).findFirst().get();
        giftCertificate.getTags().remove(tag);
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }
}