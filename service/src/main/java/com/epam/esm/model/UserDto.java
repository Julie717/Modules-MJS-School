package com.epam.esm.model;

import com.epam.esm.util.ErrorMessageReader;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    Long id;

    @NotBlank
    @Size(min = 2, max = 20, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String name;

    @NotBlank
    @Size(min = 2, max = 50, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String surname;

    @Valid
    List<PurchaseRequestDto> purchases;
}
