package com.epam.esm.util;

public class ErrorMessageReader {

    public static final String GIFT_CERTIFICATE_ALREADY_EXISTS = "certificate.already_exist";
    public static final String GIFT_CERTIFICATE_NOT_FOUND = "certificate.not_found";
    public static final String GIFT_CERTIFICATE_NOT_FOUND_BY_PARAMS = "certificate.not_found_by_params";
    public static final String GIFT_CERTIFICATE_INCORRECT_PARAMS = "certificate.incorrect_params";
    public static final String GIFT_CERTIFICATE_WITH_TAG_NOT_FOUND = "certificate_with_tag.not_found";
    public static final String TAG_NOT_FOUND = "tag.not_found";
    public static final String TAG_ALREADY_EXISTS = "tag.already_exist";
    public static final String INCORRECT_VALUE = "incorrect_value";
    public static final String GIFT_CERTIFICATE_INCORRECT_NAME_SIZE = "certificate.incorrect_name_size";
    public static final String GIFT_CERTIFICATE_INCORRECT_DESCRIPTION_SIZE = "certificate.incorrect_description_size";
    public static final String TAG_INCORRECT_NAME_SIZE = "tag.incorrect_name_size";
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";

    private ErrorMessageReader() {
    }
}
