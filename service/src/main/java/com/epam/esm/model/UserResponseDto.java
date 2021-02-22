package com.epam.esm.model;

import com.epam.esm.util.ErrorMessageReader;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto extends RepresentationModel<UserResponseDto> implements Serializable {
    Long id;

    @NotBlank
    @Pattern(regexp ="\\p{Alpha}[\\w\\-.]{4,20}", message = ErrorMessageReader.USER_INCORRECT_LOGIN)
    String login;

    @NotBlank
    @Size(min = 2, max = 20, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String name;

    @NotBlank
    @Size(min = 2, max = 50, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String surname;
}
