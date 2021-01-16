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
     * @param nameTag the name tag
     * @return the optional
     */
    Optional<Tag> findTagByName(String nameTag);

    /**
     * Find tags by names where tagRangeNames is a string that includes tag names
     * in round brackets separated by comma
     *
     * @param tagRangeNames the tag range names
     * @return the list
     */
    List<Tag> findTagByNameInRange(String tagRangeNames);

    /**
     * Delete tag from gift certificate.
     *
     * @param idTag the id tag
     * @return the boolean
     */
    boolean deleteFromGiftCertificateTag(int idTag);
}