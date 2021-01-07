package com.epam.esm.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.StringJoiner;

public class TagDto implements Serializable {
    private int idTag;

    @NotBlank
    @Size(min = 2, max = 3, message = "Too long name")
    private String name;

    public TagDto() {
    }

    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDto)) return false;

        TagDto tagDto = (TagDto) o;

        if (idTag != tagDto.idTag) return false;
        return name != null ? name.equals(tagDto.name) : tagDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = idTag;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TagDto.class.getSimpleName() + "[", "]")
                .add("idTag=" + idTag)
                .add("name='" + name + "'")
                .toString();
    }
}