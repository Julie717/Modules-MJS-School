package com.epam.esm.extractor;

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
            tag = new Tag();
            tag.setIdTag(rs.getInt(ColumnName.TAG_ID));
            tag.setName(rs.getString(ColumnName.TAG_NAME));
        }
        return tag;
    }
}