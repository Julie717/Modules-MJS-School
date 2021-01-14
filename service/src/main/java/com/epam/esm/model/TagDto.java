package com.epam.esm.model;

import com.epam.esm.util.ErrorMessageReader;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDto implements Serializable {
    Integer idTag;

    @NotBlank
    @Size(min = 2, max = 45, message = ErrorMessageReader.TAG_INCORRECT_NAME_SIZE)
    String nameTag;
}