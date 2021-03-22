package com.epam.esm.querybuilder;

import com.epam.esm.model.QGiftCertificate;
import com.epam.esm.querybuilder.impl.PredicateSearchBuilder;
import com.epam.esm.querybuilder.impl.PredicateSearchTagBuilder;
import com.epam.esm.validator.CommonValidator;
import com.epam.esm.validator.impl.GiftCertificateDescriptionValidator;
import com.epam.esm.validator.impl.GiftCertificateNameValidator;
import com.epam.esm.validator.impl.GiftCertificateSortValidator;
import com.epam.esm.validator.impl.TagNameValidator;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum GiftCertificateSearchParameterName {
    NAME("nameGiftCertificate", QGiftCertificate.giftCertificate.name,
            new GiftCertificateNameValidator(), new PredicateSearchBuilder()),
    DESCRIPTION("description", QGiftCertificate.giftCertificate.description,
            new GiftCertificateDescriptionValidator(), new PredicateSearchBuilder()),
    SORT("sort", null, new GiftCertificateSortValidator(), null),
    NAME_TAG("nameTag",QGiftCertificate.giftCertificate.tags.any().name,
            new TagNameValidator(), new PredicateSearchTagBuilder());

    private final String searchName;
    private final StringPath nameForPredicate;
    private final CommonValidator validator;
    private final PredicateBuilder predicateBuilder;

    public static Optional<GiftCertificateSearchParameterName> getSearchParameterName(String name) {
        GiftCertificateSearchParameterName[] parameterNames = GiftCertificateSearchParameterName.values();
        Optional<GiftCertificateSearchParameterName> resultParameterName = Arrays.stream(parameterNames)
                .filter(o -> o.getSearchName().equalsIgnoreCase(name)).findAny();
        return resultParameterName;
    }
}