package com.epam.esm.service;

import com.epam.esm.model.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> findAll();

    TagDto findById(int idTag);

    TagDto add(TagDto tagDto);

    void deleteById(int idTag);

    List<TagDto> findByRangeNames(List<TagDto> tagsDto);
}