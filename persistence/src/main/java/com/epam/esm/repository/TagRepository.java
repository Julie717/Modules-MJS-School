package com.epam.esm.repository;

import com.epam.esm.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag repository.
 */
@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    /**
     * Find tag by name.
     *
     * @param name is the name of tag
     * @return the optional value of tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Find tags by names where names is the list of tag's names that should be found
     *
     * @param names is the tag list of tag's names
     * @return the list of tags
     */
    List<Tag> findByNameIn(List<String> names);

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     *
     * @param pageable is an element of pagination that consists of the number of pages and
     *                 the amount of record on each page.
     * @return the list
     */
    @Query(value = "SELECT id_tag, name_tag "
            + "FROM user_tags "
            + "WHERE count_tag= "
            + "    (SELECT MAX(count_tag) "
            + "     FROM user_tags n"   //this is predefined view
            + "     WHERE  id_user IN ( "
            + "                       SELECT id_user "
            + "                       FROM user_purchases_cost "  //this is predefined view
            + "                       WHERE full_sum = ( "
            + "                                         SELECT MAX(full_sum)  "
            + "                                         FROM user_purchases_cost "  //this is predefined view
            + "                                         ) "
            + "                       ) "
            + " )", nativeQuery = true)
    List<Tag> findTopTag(Pageable pageable);

    /**
     * Delete tag from gift certificate.
     *
     * @param id is the id of tag
     * @return the boolean value ("true" if tag was deleted)
     */
    @Modifying
    @Query(value = "DELETE FROM gift_certificate_tag WHERE id_tag = :id", nativeQuery = true)
    void deleteTagFromGiftCertificates(@Param("id") Long id);
}