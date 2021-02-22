package com.epam.esm.util;

public class ErrorMessageReader {

    public static final String GIFT_CERTIFICATE_ALREADY_EXISTS = "certificate.already_exist";
    public static final String RESOURCE_NOT_FOUND = "resource.not_found";
    public static final String RESOURCES_NOT_FOUND = "resources.not_found";
    public static final String GIFT_CERTIFICATE_NOT_FOUND_BY_PARAMS = "certificate.not_found_by_params";
    public static final String GIFT_CERTIFICATE_INCORRECT_PARAMS = "certificate.incorrect_params";
    public static final String GIFT_CERTIFICATES_WITH_TAG_NOT_FOUND = "certificates_with_tag.not_found";
    public static final String GIFT_CERTIFICATES_USED = "certificate.used";
    public static final String TAG_ALREADY_EXISTS = "tag.already_exist";
    public static final String TAG_ALREADY_EXISTS_IN_GIFT_CERTIFICATE = "tag.already_exist_in_gift_certificate";
    public static final String INCORRECT_VALUE = "incorrect_value";
    public static final String GIFT_CERTIFICATE_INCORRECT_NAME_SIZE = "certificate.incorrect_name_size";
    public static final String GIFT_CERTIFICATE_INCORRECT_DESCRIPTION_SIZE = "certificate.incorrect_description_size";
    public static final String TAG_INCORRECT_NAME_SIZE = "tag.incorrect_name_size";
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";
    public static final String INCORRECT_REQUEST = "incorrect_request";
    public static final String METHOD_NOT_ALLOWED = "method_not_allowed";
    public static final String BAD_REQUEST_PARAM_ABSENT = "param_absent";
    public static final String TAG_IN_GIFT_CERTIFICATE_NOT_FOUND = "tag.not_found_in_gift_certificate";
    public static final String DUPLICATE_VALUE = "duplicate_value";
    public static final String USER_INCORRECT_LOGIN = "user.incorrect_login";
    public static final String USER_INCORRECT_PASSWORD = "user.incorrect_password";
    public static final String USER_INCORRECT_NAME_SIZE = "user.incorrect_name_size";
    public static final String USER_INCORRECT_SURNAME_SIZE = "user.incorrect_surname_size";
    public static final String USER_ALREADY_EXISTS = "user.already_exist";
    public static final String ACCESS_DENIED="access_denied";

    private ErrorMessageReader() {
    }
}