package com.epam.esm.dao.impl;

import com.epam.esm.dao.SqlQuery;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import com.epam.esm.dao.extractor.ListTagResultSetExtractor;
import com.epam.esm.dao.extractor.TagResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;
    private final TagResultSetExtractor tagExtractor;
    private final ListTagResultSetExtractor listTagExtractor;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagResultSetExtractor tagExtractor,
                      ListTagResultSetExtractor listTagExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagExtractor = tagExtractor;
        this.listTagExtractor = listTagExtractor;
    }

    @Override
    public Optional<Tag> findTagByName(String nameTag) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_NAME, tagExtractor, nameTag));
    }

    @Override
    public Tag add(Tag entity) {
        KeyHolder key = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.ADD_TAG,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getNameTag());
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator, key);
        Number id = key.getKey();
        if (id != null) {
            entity.setIdTag(id.intValue());
        }
        return entity;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAG_BY_ID, id) > 0;
    }

    @Override
    public Optional<Tag> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_ID, tagExtractor, id));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SqlQuery.SELECT_ALL_TAG, listTagExtractor);
    }

    @Override
    public List<Tag> findTagByNameInRange(String tagRangeNames) {
        return jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_NAME_IN_RANGE + tagRangeNames,
                listTagExtractor);
    }

    @Override
    public boolean deleteFromGiftCertificateTag(int idTag) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAGS_FROM_GIFT_CERTIFICATE_TAG, idTag) > 0;
    }
}