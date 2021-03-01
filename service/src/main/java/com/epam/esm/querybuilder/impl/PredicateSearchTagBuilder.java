package com.epam.esm.querybuilder.impl;

import com.epam.esm.querybuilder.PredicateBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredicateSearchTagBuilder implements PredicateBuilder {
    private final static String COMMA = ",";

    @Override
    public Predicate build(String value, StringPath nameForPredicate) {
        String[] searchParameters = value.split(COMMA);
        List<Predicate> predicates = new ArrayList<>();
        Arrays.stream(searchParameters)
                .forEach(p -> predicates.add(
                        nameForPredicate.containsIgnoreCase(p)));
        return ExpressionUtils.anyOf(predicates);
    }
}