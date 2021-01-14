package com.epam.esm.querybuilder;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.querybuilder.impl.PartQuerySearchBuilder;
import com.epam.esm.querybuilder.impl.PartQuerySortBuilder;
import com.epam.esm.validator.CommonValidator;
import com.epam.esm.validator.impl.GiftCertificateDescriptionValidator;
import com.epam.esm.validator.impl.GiftCertificateNameValidator;
import com.epam.esm.validator.impl.GiftCertificateSortValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum GiftCertificateSearchParameterName {
    NAME("nameGiftCertificate", ColumnName.GIFT_CERTIFICATE_NAME, new GiftCertificateNameValidator(), new PartQuerySearchBuilder()),
    DESCRIPTION("description", ColumnName.GIFT_CERTIFICATE_DESCRIPTION, new GiftCertificateDescriptionValidator(), new PartQuerySearchBuilder()),
    SORT("sort", null, new GiftCertificateSortValidator(), new PartQuerySortBuilder());

    private final String parameterName;
    private final String parameterNameInDb;
    private final CommonValidator validator;
    private final PartQueryBuilder partQueryBuilder;

    public static Optional<GiftCertificateSearchParameterName> getSearchParameterName(String name) {
        GiftCertificateSearchParameterName[] parameterNames = GiftCertificateSearchParameterName.values();
        Optional<GiftCertificateSearchParameterName> resultParameterName = Arrays.stream(parameterNames)
                .filter(o -> o.getParameterName().equalsIgnoreCase(name)).findAny();
        return resultParameterName;
    }
}