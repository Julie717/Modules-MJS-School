package com.epam.esm.dao;

public class SqlQuery {
    public static final String SELECT_TAG_BY_ID = "SELECT id_tag, name " +
            "FROM tag " +
            "WHERE (id_tag = ?)";
    public static final String SELECT_TAG_BY_NAME = "SELECT id_tag, name " +
            "FROM tag " +
            "WHERE name = ?";
    public static final String SELECT_TAG_BY_PART_NAME = "SELECT id_tag, name " +
            "FROM tag " +
            "WHERE name LIKE CONCAT('%', ?, '%')";
    public static final String SELECT_ALL_TAG = "SELECT id_tag, name FROM tag";
    public static final String ADD_TAG = "INSERT INTO tag (name) VALUES(?)";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id_tag = ?";

    private SqlQuery() {
    }
}