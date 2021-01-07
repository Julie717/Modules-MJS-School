package com.epam.esm.dao.impl;

import com.epam.esm.dao.SqlQuery;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import com.epam.esm.extractor.ListTagResultSetExtractor;
import com.epam.esm.extractor.TagResultSetExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;

    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tag> findTagByName(String nameTag) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_NAME, new TagResultSetExtractor(), nameTag));
    }

    @Override
    public List<Tag> findTagByPartName(String nameTag) {
        return jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_PART_NAME, new ListTagResultSetExtractor(), nameTag);
    }

    @Override
    public boolean add(Tag entity) {
        return jdbcTemplate.update(SqlQuery.ADD_TAG, entity.getName()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAG_BY_ID, id) > 0;
    }

    @Override
    public Optional<Tag> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_ID, new TagResultSetExtractor(), id));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SqlQuery.SELECT_ALL_TAG, new ListTagResultSetExtractor());
    }
}