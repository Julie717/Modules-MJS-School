package com.epam.esm.querybuilder;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;

/**
 * The interface PredicateBuilder builder.
 */
public interface PredicateBuilder {
    /**
     * Build predicate for search by parameters.
     *
     * @param value             is the value of parameter that is used for searching
     * @param nameForPredicate is the parameter name for creating predicates
     * @return the Predicate
     */
    Predicate build(String value, StringPath nameForPredicate) ;
}