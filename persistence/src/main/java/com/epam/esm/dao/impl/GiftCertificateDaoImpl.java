package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQuery;
import com.epam.esm.dao.extractor.GiftCertificateResultSetExtractor;
import com.epam.esm.dao.extractor.GiftCertificateWithTagResultSetExtractor;
import com.epam.esm.dao.extractor.ListGiftCertificateResultSetExtractor;
import com.epam.esm.dao.extractor.ListGiftCertificateWithTagResultSetExtractor;
import com.epam.esm.model.GiftCertificate;
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

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                new GiftCertificateResultSetExtractor(), id));
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SqlQuery.SELECT_ALL_GIFT_CERTIFICATES, new ListGiftCertificateResultSetExtractor());
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificateByName(String nameGiftCertificate) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_GIFT_CERTIFICATE_BY_NAME,
                new GiftCertificateResultSetExtractor(), nameGiftCertificate));
    }

    @Override
    public List<GiftCertificate> findByParameters(String queryLastPart) {
        String sqlQuery = SqlQuery.SELECT_ALL_GIFT_CERTIFICATES + queryLastPart;
        System.out.println(sqlQuery);
        return jdbcTemplate.query(sqlQuery, new ListGiftCertificateResultSetExtractor());
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
                new GiftCertificateWithTagResultSetExtractor(), idGiftCertificate));
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificateWithTagsByTagName(int idGiftCertificate, String nameTag) {
        return Optional.ofNullable(jdbcTemplate.query(SqlQuery.SELECT_GIFT_CERTIFICATE_BY_ID_WITH_TAGS_BY_TAG_NAME,
                new GiftCertificateWithTagResultSetExtractor(), idGiftCertificate, nameTag));
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
                new ListGiftCertificateWithTagResultSetExtractor());
    }
}