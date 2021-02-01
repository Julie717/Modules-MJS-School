package com.epam.esm.dao.impl;

import com.epam.esm.dao.Queries;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private final EntityManager entityManager;

    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> findAll() {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_TAG, Tag.class);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }


    @Override
    public Optional<Tag> findTagByName(String name) {
        Optional<Tag> tag;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_TAG_BY_NAME);
            query.setParameter(1, name);
            tag = Optional.ofNullable((Tag) query.getSingleResult());
        } catch (NoResultException ex) {
            tag = Optional.empty();
        }
        return tag;
    }

    @Override
    public List<Tag> findTagByNameInRange(List<String> tagRangeNames) {
        Query query = entityManager.createQuery(Queries.SELECT_TAG_BY_NAME_IN_RANGE, Tag.class)
                .setParameter(1, tagRangeNames);
        return query.getResultList();
    }

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        entityManager.flush();
        Long id = (Long) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(tag);
        tag.setId(id);
        return tag;
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public boolean deleteTagFromGiftCertificates(Long id) {
        return entityManager.createNativeQuery(Queries.DELETE_TAGS_FROM_GIFT_CERTIFICATE_TAG)
                .setParameter(1, id)
                .executeUpdate() > 0;
    }
}