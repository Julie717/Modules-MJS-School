package com.epam.esm.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Pagination {
    private static final Integer DEFAULT_PAGE = 0;

    @PositiveOrZero
    @Max(value = 2000000)
    Integer page;

    @NotNull
    @Positive
    @Max(value = 2000000)
    Integer perPage;

    public Integer getPage() {
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        return page;
    }
}