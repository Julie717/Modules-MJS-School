package com.epam.esm.model;

import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.validator.ValidationGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateDto implements Serializable {
    Integer idGiftCertificate;

    @NotBlank(groups = ValidationGroup.CreateValidation.class)
    @Size(min = 2, max = 45, message = ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_NAME_SIZE)
    String nameGiftCertificate;

    @NotBlank(groups = ValidationGroup.CreateValidation.class)
    @Size(min = 1, max = 1000, message = ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_DESCRIPTION_SIZE)
    String description;

    @NotNull(groups = ValidationGroup.CreateValidation.class)
    @Positive
    @Digits(integer = 5, fraction = 2)
    BigDecimal price;

    @NotNull(groups = ValidationGroup.CreateValidation.class)
    @Positive
    @Min(1)
    Integer duration;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    Timestamp createDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    Timestamp lastUpdateDate;

    List<TagDto> tags;
}