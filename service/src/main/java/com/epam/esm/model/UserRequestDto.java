package com.epam.esm.model;

import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.validator.ValidationGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestDto extends RepresentationModel<UserRequestDto> implements Serializable {
    @NotBlank
    @Pattern(regexp ="\\p{Alpha}[\\w\\-.]{4,20}", message = ErrorMessageReader.USER_INCORRECT_LOGIN)
    String login;

    @NotBlank
    @Pattern(regexp ="(?=.*\\d)(?=.*\\p{Upper})[\\w\\p{Punct}]{8,20}", message = ErrorMessageReader.USER_INCORRECT_PASSWORD)
    String password;

    @NotBlank(groups = {ValidationGroup.CreateValidation.class})
    @Size(min = 2, max = 20, message = ErrorMessageReader.USER_INCORRECT_NAME_SIZE)
    String name;

    @NotBlank(groups = {ValidationGroup.CreateValidation.class})
    @Size(min = 2, max = 50, message = ErrorMessageReader.USER_INCORRECT_SURNAME_SIZE)
    String surname;
}