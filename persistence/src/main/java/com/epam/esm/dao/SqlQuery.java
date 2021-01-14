package com.epam.esm.dao;

public class SqlQuery {
    public static final String SELECT_TAG_BY_ID = "SELECT id_tag, name_tag " +
            "FROM tag " +
            "WHERE (id_tag = ?)";
    public static final String SELECT_TAG_BY_NAME = "SELECT id_tag, name_tag " +
            "FROM tag " +
            "WHERE name_tag = ?";
    public static final String SELECT_ALL_TAG = "SELECT id_tag, name_tag FROM tag";
    public static final String ADD_TAG = "INSERT INTO tag (name_tag) VALUES(?)";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id_tag = ?";
    public static final String SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT id_gift_certificate, name_gift_certificate, description, " +
            "price, duration, create_date, last_update_date FROM gift_certificate " +
            "WHERE (id_gift_certificate = ?)";
    public static final String SELECT_GIFT_CERTIFICATE_BY_NAME = "SELECT id_gift_certificate, name_gift_certificate, description, " +
            "price, duration, create_date, last_update_date FROM gift_certificate " +
            "WHERE (name_gift_certificate = ?)";
    public static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT id_gift_certificate, name_gift_certificate, description, " +
            "price, duration, create_date, last_update_date FROM gift_certificate";
    public static final String ADD_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name_gift_certificate, description, " +
            "price, duration, create_date, last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    public static final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate " +
            "WHERE id_gift_certificate = ?";
    public static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET "
            + "name_gift_certificate = ?, description = ?, price = ?, duration = ?, create_date = ?, "
            + "last_update_date = ? WHERE id_gift_certificate = ?";
    public static final String SELECT_GIFT_CERTIFICATE_BY_ID_WITH_TAGS = "SELECT gift_certificate.id_gift_certificate, " +
            "name_gift_certificate, description, price, duration, create_date, last_update_date, " +
            "tag.id_tag, name_tag FROM gift_certificate LEFT JOIN gift_certificate_tag " +
            "ON (gift_certificate.id_gift_certificate = gift_certificate_tag.id_gift_certificate) " +
            "LEFT JOIN tag ON (gift_certificate_tag.id_tag = tag.id_tag) " +
            "WHERE (gift_certificate.id_gift_certificate = ?)";
    public static final String SELECT_GIFT_CERTIFICATES_WITH_TAGS = "SELECT gift_certificate.id_gift_certificate, " +
            "name_gift_certificate, description, price, duration, create_date, last_update_date, " +
            "tag.id_tag, name_tag FROM gift_certificate LEFT JOIN gift_certificate_tag " +
            "ON (gift_certificate.id_gift_certificate = gift_certificate_tag.id_gift_certificate) " +
            "LEFT JOIN tag ON (gift_certificate_tag.id_tag = tag.id_tag)";
    public static final String SELECT_GIFT_CERTIFICATE_BY_ID_WITH_TAGS_BY_TAG_NAME = "SELECT gift_certificate.id_gift_certificate, " +
            "name_gift_certificate, description, price, duration, create_date, last_update_date, " +
            "tag.id_tag, name_tag FROM gift_certificate LEFT JOIN gift_certificate_tag " +
            "ON (gift_certificate.id_gift_certificate = gift_certificate_tag.id_gift_certificate) " +
            "LEFT JOIN tag ON (gift_certificate_tag.id_tag = tag.id_tag) " +
            "WHERE (gift_certificate.id_gift_certificate = ? AND name_tag LIKE CONCAT('%', ?, '%'))";
    public static final String SELECT_TAG_BY_NAME_IN_RANGE = "SELECT id_tag, name_tag " +
            "FROM tag " +
            "WHERE name_tag IN ";
    public static final String ADD_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO gift_certificate_tag " +
            "(id_gift_certificate, id_tag) " +
            "VALUES(?, ?)";
    public static final String GIFT_CERTIFICATE_ID_WITH_TAG_ID = "SELECT id_gift_certificate,id_tag " +
            "FROM gift_certificate_tag " +
            "WHERE (id_gift_certificate = ? AND id_tag = ?)";

    private SqlQuery() {
    }
}