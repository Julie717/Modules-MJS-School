package com.epam.esm.dao.extractor;

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
            tag = TagResultSetExtractor.extractOneTag(rs);
            tags.add(tag);
        }
        return tags;
    }
}