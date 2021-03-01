package com.epam.esm.util;

import org.springframework.util.ObjectUtils;

import java.util.Map;

public class PaginationParser {
    private static final String PAGE = "page";
    private static final String PER_PAGE = "perPage";

    public static Pagination parsePagination(Map<String, String> parameters) {
        Integer perPage = Integer.parseInt(parameters.get(PER_PAGE));
        parameters.remove(PER_PAGE);
        String pageParam = parameters.get(PAGE);
        int page = 0;
        if (!ObjectUtils.isEmpty(pageParam)) {
            page = Integer.parseInt(pageParam);
        }
        parameters.remove(PAGE);
        return new Pagination(page, perPage);
    }
}