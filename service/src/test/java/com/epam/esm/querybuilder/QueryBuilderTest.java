package com.epam.esm.querybuilder;

import com.epam.esm.model.QGiftCertificate;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryBuilderTest {

    @Test
    public void buildSearchTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        parameters.put("description", "beautiful");
        parameters.put("sort", "nameGiftCertificate,-createDate");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QGiftCertificate.giftCertificate.name.containsIgnoreCase("gift"));
        predicates.add(QGiftCertificate.giftCertificate.description.containsIgnoreCase("beautiful"));
        Predicate expected = ExpressionUtils.allOf(predicates);

        Predicate actual = QueryBuilder.buildSearch(parameters);

        assertEquals(expected, actual);
    }

    @Test
    public void buildSortTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("sort", "createDate,-nameGiftCertificate");
        Sort expected = Sort.by(
                Sort.Order.asc("createDate"),
                Sort.Order.desc("name")
        );

        Sort actual = QueryBuilder.buildSort(parameters);

        assertEquals(expected, actual);
    }

    @Test
    public void buildSortEmptyTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        parameters.put("description", "beautiful");
        Sort expected = Sort.unsorted();

        Sort actual = QueryBuilder.buildSort(parameters);

        assertEquals(expected, actual);
    }

    @Test
    public void buildSearchNameTagTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameTag", "gift,skating");
        parameters.put("description", "wonderful");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QGiftCertificate.giftCertificate.tags.any().name.containsIgnoreCase("gift"));
        predicates.add(QGiftCertificate.giftCertificate.tags.any().name.containsIgnoreCase("skating"));
        Predicate expected = ExpressionUtils.anyOf(predicates);
        predicates.clear();
        predicates.add(QGiftCertificate.giftCertificate.description.containsIgnoreCase("wonderful"));
        predicates.add(expected);
        expected = ExpressionUtils.allOf(predicates);

        Predicate actual = QueryBuilder.buildSearch(parameters);

        assertEquals(expected, actual);
    }
}