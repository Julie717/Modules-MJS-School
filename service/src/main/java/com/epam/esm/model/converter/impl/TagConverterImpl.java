package com.epam.esm.model.converter.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverterImpl implements CommonConverter<Tag, TagDto> {
    @Override
    public TagDto convertTo(Tag entity) {
        return new TagDto(entity.getIdTag(), entity.getNameTag());
    }

    @Override
    public Tag convertFrom(TagDto entity) {
        Tag tag = new Tag();
        tag.setNameTag(entity.getNameTag());
        if (entity.getIdTag() != null) {
            tag.setIdTag(entity.getIdTag());
        }
        return tag;
    }

    @Override
    public List<TagDto> convertTo(List<Tag> entities) {
        List<TagDto> tagsDto = new ArrayList<>();
        entities.forEach(t -> tagsDto.add(convertTo(t)));
        return tagsDto;
    }
}