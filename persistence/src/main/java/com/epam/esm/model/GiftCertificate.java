package com.epam.esm.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificate {
    int idGiftCertificate;
    String nameGiftCertificate;
    String description;
    BigDecimal price;
    int duration;
    Timestamp createDate;
    Timestamp lastUpdateDate;
    List<Tag> tags;
}