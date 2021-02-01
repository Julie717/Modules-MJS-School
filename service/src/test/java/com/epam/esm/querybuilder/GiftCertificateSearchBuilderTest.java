package com.epam.esm.querybuilder;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSearchBuilderTest {

 /*   @Test
    public void buildQueryTestFull() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        parameters.put("description", "beautiful");
        parameters.put("sort", "nameGiftCertificate,-createDate");
        String expected = " WHERE name_gift_certificate LIKE '%gift%' AND description LIKE '%beautiful%' " +
                "ORDER BY name_gift_certificate ASC, create_date DESC";

        String actual = GiftCertificateSearchBuilder.buildQuery(parameters);

        assertEquals(expected, actual);
    }

    @Test
    public void buildQueryTestOnlySort() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("sort", "createDate,-nameGiftCertificate");
        String expected = " ORDER BY create_date ASC, name_gift_certificate DESC";

        String actual = GiftCertificateSearchBuilder.buildQuery(parameters);

        assertEquals(expected, actual);
    }

    @Test
    public void buildQueryTestOnlySearch() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("description", "beautiful gift");
        String expected = " WHERE description LIKE '%beautiful gift%' ";

        String actual = GiftCertificateSearchBuilder.buildQuery(parameters);

        assertEquals(expected, actual);
    }*/
}