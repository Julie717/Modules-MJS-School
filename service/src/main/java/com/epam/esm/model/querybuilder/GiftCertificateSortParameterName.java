package com.epam.esm.model.querybuilder;

import com.epam.esm.dao.ColumnName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public enum GiftCertificateSortParameterName {
    NAME("name", ColumnName.GIFT_CERTIFICATE_NAME),
    CREATE_DATE("createDate", ColumnName.GIFT_CERTIFICATE_CREATE_DATE);

    private final String parameterName;
    private final String parameterNameInDb;

    public static Optional<GiftCertificateSortParameterName> getSortParameterName(String name) {
        GiftCertificateSortParameterName[] sortParameterNames = GiftCertificateSortParameterName.values();
        Optional<GiftCertificateSortParameterName> sortParameterName = Arrays.stream(sortParameterNames)
                .filter(o -> o.getParameterName().equalsIgnoreCase(name)).findAny();
        return sortParameterName;
    }
}