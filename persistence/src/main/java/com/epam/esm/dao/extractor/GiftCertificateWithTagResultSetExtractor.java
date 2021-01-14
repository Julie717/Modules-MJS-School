package com.epam.esm.dao.extractor;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateWithTagResultSetExtractor implements ResultSetExtractor<GiftCertificate> {
    @Override
    public GiftCertificate extractData(ResultSet rs) throws SQLException, DataAccessException {
        GiftCertificate giftCertificate = null;
        if (rs.next()) {
            giftCertificate = GiftCertificateResultSetExtractor.extractOneGiftCertificate(rs);
            List<Tag> tags = new ArrayList<>();
            addTagToList(rs, tags);
            while (rs.next()) {
                addTagToList(rs, tags);
            }
            giftCertificate.setTags(tags);
        }
        return giftCertificate;
    }

    public static void addTagToList(ResultSet rs, List<Tag> tags) throws SQLException {
        Tag tag = TagResultSetExtractor.extractOneTag(rs);
        if (tag.getNameTag() != null) {
            tags.add(tag);
        }
    }
}