package com.epam.esm.dao.extractor;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListGiftCertificateWithTagResultSetExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate giftCertificate;
        List<Tag> tags;
        Long id;
        if (rs.next()) {
            tags = new ArrayList<>();
            giftCertificate = GiftCertificateResultSetExtractor.extractOneGiftCertificate(rs);
            id = giftCertificate.getId();
            GiftCertificateWithTagResultSetExtractor.addTagToList(rs, tags);
            while (rs.next()) {
                if (id != rs.getInt(ColumnName.GIFT_CERTIFICATE_ID)) {
                    giftCertificate.setTags(tags);
                    giftCertificates.add(giftCertificate);
                    giftCertificate = GiftCertificateResultSetExtractor.extractOneGiftCertificate(rs);
                    id = giftCertificate.getId();
                    tags = new ArrayList<>();
                }
                GiftCertificateWithTagResultSetExtractor.addTagToList(rs, tags);
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}
