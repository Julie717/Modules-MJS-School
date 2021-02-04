package com.epam.esm.service;

import com.epam.esm.model.TagDto;
import com.epam.esm.util.Pagination;

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
    List<TagDto> findAll(Pagination pagination);

    /**
     * Find tag by id.
     *
     * @param id the id tag
     * @return the tag dto
     */
    TagDto findById(Long id);
    List<TagDto> findTopTag(Pagination pagination);
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
     * @param id the id tag
     */
    void deleteById(Long id);

    /**
     * Find tags by names in list.
     *
     * @param tagsDto the tags dto
     * @return the list
     */
    List<TagDto> findByRangeNames(List<TagDto> tagsDto);
}