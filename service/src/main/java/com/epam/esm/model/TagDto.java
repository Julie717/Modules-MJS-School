package com.epam.esm.model;

import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.validator.ValidationGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto implements Serializable {
    @NotNull(groups = ValidationGroup.PutValidation.class)
    @Positive(groups = ValidationGroup.PutValidation.class)
    Long id;

    @NotBlank
    @Size(min = 2, max = 45, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String name;
}