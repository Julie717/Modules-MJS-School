package com.epam.esm.dao.extractor;

import com.epam.esm.model.GiftCertificate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListGiftCertificateResultSetExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate giftCertificate;
        while (rs.next()) {
            giftCertificate = GiftCertificateResultSetExtractor.extractOneGiftCertificate(rs);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}