package com.epam.esm.dao.extractor;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.model.GiftCertificate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateResultSetExtractor implements ResultSetExtractor<GiftCertificate> {
    @Override
    public GiftCertificate extractData(ResultSet rs) throws SQLException, DataAccessException {
        return rs.next() ? extractOneGiftCertificate(rs) : null;
    }

    public static GiftCertificate extractOneGiftCertificate(ResultSet rs) throws SQLException, DataAccessException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(ColumnName.GIFT_CERTIFICATE_ID));
        giftCertificate.setName(rs.getString(ColumnName.GIFT_CERTIFICATE_NAME));
        giftCertificate.setDescription(rs.getString(ColumnName.GIFT_CERTIFICATE_DESCRIPTION));
        giftCertificate.setPrice(rs.getBigDecimal(ColumnName.GIFT_CERTIFICATE_PRICE));
        giftCertificate.setDuration(rs.getInt(ColumnName.GIFT_CERTIFICATE_DURATION));
        giftCertificate.setCreateDate(rs.getTimestamp(ColumnName.GIFT_CERTIFICATE_CREATE_DATE));
        giftCertificate.setLastUpdateDate(rs.getTimestamp(ColumnName.GIFT_CERTIFICATE_LAST_UPDATE_DATE));
        return giftCertificate;
    }
}