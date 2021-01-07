package com.epam.esm.extractor;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListTagResultSetExtractor implements ResultSetExtractor<List<Tag>> {
    @Override
    public List<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Tag> tags = new ArrayList<>();
        Tag tag;
        while (rs.next()) {
            tag = new Tag();
            tag.setIdTag(rs.getInt(ColumnName.TAG_ID));
            tag.setName(rs.getString(ColumnName.TAG_NAME));
            tags.add(tag);
        }
        return tags;
    }
}