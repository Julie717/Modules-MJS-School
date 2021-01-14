package com.epam.esm.dao.extractor;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagResultSetExtractor implements ResultSetExtractor<Tag> {
    @Override
    public Tag extractData(ResultSet rs) throws SQLException, DataAccessException {
        Tag tag = null;
        if (rs.next()) {
            tag = extractOneTag(rs);
        }
        return tag;
    }

    public static Tag extractOneTag(ResultSet rs) throws SQLException, DataAccessException {
        Tag tag = new Tag();
        tag.setIdTag(rs.getInt(ColumnName.TAG_ID));
        tag.setNameTag(rs.getString(ColumnName.TAG_NAME));
        return tag;
    }
}