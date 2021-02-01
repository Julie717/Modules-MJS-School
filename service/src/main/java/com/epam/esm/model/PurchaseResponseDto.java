package com.epam.esm.model;

import com.epam.esm.dao.ColumnName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseResponseDto extends PurchaseRequestDto implements Serializable {
    Long id;

    @NotNull
    @Positive
    @Digits(integer = 5, fraction = 2)
    BigDecimal cost;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    Timestamp purchaseDate;

    public PurchaseResponseDto( Long id, @NotNull @Positive @Digits(integer = 5, fraction = 2) BigDecimal cost,
                                Timestamp purchaseDate,@NotNull Long idUser,
                                @NotEmpty @NotNull List<Long> idGiftCertificates
                              ) {
        super(idUser, idGiftCertificates);
        this.id = id;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

//  Long idUser;
 //   List<Long> idGiftCertificates;
}