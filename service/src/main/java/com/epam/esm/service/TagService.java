package com.epam.esm.service;

import com.epam.esm.model.TagDto;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Find all tags.
     *
     * @return the list
     */
    List<TagDto> findAll();

    /**
     * Find tag by id.
     *
     * @param idTag the id tag
     * @return the tag dto
     */
    TagDto findById(int idTag);

    /**
     * Add tag to Db.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     */
    TagDto add(TagDto tagDto);

    /**
     * Delete tag by id from Db.
     *
     * @param idTag the id tag
     */
    void deleteById(int idTag);

    /**
     * Find tags by names in list.
     *
     * @param tagsDto the tags dto
     * @return the list
     */
    List<TagDto> findByRangeNames(List<TagDto> tagsDto);
}