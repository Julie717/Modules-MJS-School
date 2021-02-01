package com.epam.esm.querybuilder;

import com.epam.esm.model.TagDto;

import java.util.List;
import java.util.stream.Collectors;

public class TagSearchBuilder {
    private final static char OPEN_ROUND_BRACKET = '(';
    private final static char CLOSE_ROUND_BRACKET = ')';
    private final static char OPEN_SQUARE_BRACKET = '[';
    private final static char CLOSE_SQUARE_BRACKET = ']';
    private final static char HATCH = '\'';

    public static String buildQueryToSearchTags(List<TagDto> tagsDto) {
        String endQueryPart = tagsDto.stream().map(tag -> HATCH + tag.getName() + HATCH)
                .collect(Collectors.toList()).toString()
                .replace(OPEN_SQUARE_BRACKET, OPEN_ROUND_BRACKET)
                .replace(CLOSE_SQUARE_BRACKET, CLOSE_ROUND_BRACKET);
        return endQueryPart;
    }
}