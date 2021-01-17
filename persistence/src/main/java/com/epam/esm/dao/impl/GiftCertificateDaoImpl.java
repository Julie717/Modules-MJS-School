package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQuery;
import com.epam.esm.dao.extractor.GiftCertificateResultSetExtractor;
import com.epam.esm.dao.extractor.GiftCertificateWithTagResultSetExtractor;
import com.epam.esm.dao.extractor.ListGiftCertificateResultSetExtractor;
import com.epam.esm.dao.extractor.ListGiftCertificateWithTagResultSetExtractor;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateResultSetExtractor giftCertificateExtractor;
    private final GiftCertificateWithTagResultSetExtractor giftCertificateWithTagExtractor;
    private final ListGiftCertificateResultSetExtractor listGiftCertificateExtractor;
    private final ListGiftCertificateWithTagResultSetExtractor listGiftCertificateWithTagExtractor;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateResultSetExtractor
            giftCertificateExtractor, GiftCertificateWithTagResultSetExtractor
                                          giftCertificateWithTagExtractor, ListGiftCertificateResultSetExtractor
                                          listGiftCertificateExtractor, ListGiftCertificateWithTagResultSetExtractor listGiftCertificateWithTagExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateExtractor = giftCertificateExtractor;
        this.giftCertificateWithTagExtractor = giftCertificateWithTagExtractor;
        this.listGiftCertificateExtractor = listGiftCertificateExtractor;
        this.listGiftCertificateWithTagExtractor = listGiftCertificateWithTagExtractor;
    }

    @Override
    public GiftCertificate add(GiftCertificate entity) {
        KeyHolder key = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.ADD_GIFT_CERTIFICATE,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getNameGiftCertificate());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBigDecimal(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getDuration());
            preparedStatement.setTimestamp(5, entity.getCreateDate());
            preparedStatement.setTimestamp(6, entity.getLastUpdateDate());
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator, key);
        Number id = key.getKey();
        if (id != null) {
            entity.setIdGiftCertificate(id.intValue());
        }
        return entity;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(SqlQuery.DELETE_GIFT_CERTIFICATE_BY_ID, id) > 0;
    }

    @Override
    public Optional<GiftCertificate> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_GIFT_CERTIFICATE_BY_ID,
                giftCertificateExtractor, id));
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SqlQuery.SELECT_ALL_GIFT_CERTIFICATES, listGiftCertificateExtractor);
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificateByName(String nameGiftCertificate) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_GIFT_CERTIFICATE_BY_NAME,
                giftCertificateExtractor, nameGiftCertificate));
    }

    @Override
    public List<GiftCertificate> findByParameters(String queryLastPart) {
        String sqlQuery = SqlQuery.SELECT_ALL_GIFT_CERTIFICATES + queryLastPart;
        return jdbcTemplate.query(sqlQuery, listGiftCertificateExtractor);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SqlQuery.UPDATE_GIFT_CERTIFICATE, giftCertificate.getNameGiftCertificate(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getIdGiftCertificate());
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificateWithTags(int idGiftCertificate) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_GIFT_CERTIFICATE_BY_ID_WITH_TAGS,
                giftCertificateWithTagExtractor, idGiftCertificate));
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificateWithTagsByTagName(int idGiftCertificate, String nameTag) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_GIFT_CERTIFICATE_BY_ID_WITH_TAGS_BY_TAG_NAME,
                giftCertificateWithTagExtractor, idGiftCertificate, nameTag));
    }

    @Override
    public void addTagToGiftCertificate(int idGiftCertificate, int idTag) {
        jdbcTemplate.update(SqlQuery.ADD_TAG_TO_GIFT_CERTIFICATE, idGiftCertificate, idTag);
    }

    @Override
    public Boolean isGiftCertificateWithTagExist(int idGiftCertificate, int idTag) {
        ResultSetExtractor<Boolean> resultSetExtractor = rs -> rs.next();
        return jdbcTemplate.query(SqlQuery.GIFT_CERTIFICATE_ID_WITH_TAG_ID, resultSetExtractor, idGiftCertificate, idTag);
    }

    @Override
    public List<GiftCertificate> findAllWithTags() {
        return jdbcTemplate.query(SqlQuery.SELECT_GIFT_CERTIFICATES_WITH_TAGS,
                listGiftCertificateWithTagExtractor);
    }

    @Override
    public boolean deleteFromGiftCertificateTags(int idGiftCertificate) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAGS_FROM_GIFT_CERTIFICATE, idGiftCertificate) > 0;
    }

    @Override
    public boolean deleteFromGiftCertificateTag(int idGiftCertificate, int idTag) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAG_FROM_GIFT_CERTIFICATE, idGiftCertificate, idTag) > 0;
    }
}