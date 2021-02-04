package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao extends CommonDao<Tag> {
    /**
     * Find tag by name.
     *
     * @param name the name tag
     * @return the optional
     */
    Optional<Tag> findTagByName(String name);

    /**
     * Find tags by names where tagRangeNames is a string that includes tag names
     * in round brackets separated by comma
     *
     * @param tagRangeNames the tag range names
     * @return the list
     */
    List<Tag> findTagByNameInRange(List<String> tagRangeNames);
    List<Tag> findTopTag(Integer limit, Integer offset);
    /**
     * Delete tag from gift certificate.
     *
     * @param id the id tag
     * @return the boolean
     */
    boolean deleteTagFromGiftCertificates(Long id);
}