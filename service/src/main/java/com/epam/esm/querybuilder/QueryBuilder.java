package com.epam.esm.querybuilder;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryBuilder {
    private final static String COMMA = ",";
    private final static char SIGN_MINUS = '-';
    private final static String SORT = "SORT";

    public static Predicate buildSearch(Map<String, String> parameters) {
        List<Predicate> predicates = new ArrayList<>();
        parameters.keySet().stream().filter(p -> GiftCertificateSearchParameterName
                .getSearchParameterName(p).isPresent())
                .map(p -> GiftCertificateSearchParameterName
                .getSearchParameterName(p).get())
                .filter(p -> p.getPredicateBuilder() != null)
                .forEach(p -> predicates.add(p.getPredicateBuilder()
                        .build(parameters.get(p.getSearchName()), p.getNameForPredicate())));
        return ExpressionUtils.allOf(predicates);
    }

    public static Sort buildSort(Map<String, String> parameters) {
        String sortParametersString = parameters.get(GiftCertificateSearchParameterName.valueOf(SORT).getSearchName());
        Sort sort = Sort.unsorted();
        if (!ObjectUtils.isEmpty(sortParametersString)) {
            String[] sortParameters = parameters.get(GiftCertificateSearchParameterName.valueOf(SORT).getSearchName())
                    .split(COMMA);
            List<Sort.Order> orders = sortAsc(sortParameters);
            orders.addAll(sortDesc(sortParameters));
            sort = Sort.by(orders);
        }
        return sort;
    }

    private static List<Sort.Order> sortAsc(String[] sortParameters) {
        return Arrays.stream(sortParameters).map(String::trim)
                .filter(p -> p.charAt(0) != SIGN_MINUS).map(s -> Sort.Order.asc(
                        GiftCertificateSortParameterName
                                .getSortParameterName(s).get().getFieldNameInClass()))
                .collect(Collectors.toList());
    }

    private static List<Sort.Order> sortDesc(String[] sortParameters) {
        return Arrays.stream(sortParameters).map(String::trim)
                .filter(p -> p.charAt(0) == SIGN_MINUS).map(s -> Sort.Order.desc(
                        GiftCertificateSortParameterName
                                .getSortParameterName(s.substring(1)).get().getFieldNameInClass()))
                .collect(Collectors.toList());
    }
}