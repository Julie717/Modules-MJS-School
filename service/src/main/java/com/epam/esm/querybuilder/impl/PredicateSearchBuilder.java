package com.epam.esm.querybuilder.impl;

import com.epam.esm.querybuilder.PredicateBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;

public class PredicateSearchBuilder implements PredicateBuilder {
    @Override
    public Predicate build(String value, StringPath nameForPredicate) {
        return nameForPredicate.containsIgnoreCase(value);
    }
}