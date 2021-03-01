package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate repository.
 */
@Repository
public interface GiftCertificateRepository extends PagingAndSortingRepository<GiftCertificate, Long>,
        QuerydslPredicateExecutor<GiftCertificate> {
    /**
     * Find gift certificate by name in Db.
     *
     * @param name is the name of gift certificate
     * @return the optional of gift certificate
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Find gift certificates by tag id.
     *
     * @param idTag  is  the id of tag
     * @param pageable  is an element of pagination that consists of the number of pages and
     *                  the amount of record on each page.
     * @return the list of gift certificates that was found
     */
    @Query(value = "FROM GiftCertificate g JOIN FETCH g.tags t WHERE t.id = :id")
    List<GiftCertificate> findByIdTag(@Param("id") Long idTag, Pageable pageable);

    /**
     * Find gift certificate with id = idGiftCertificate and tag id = idTag.
     *
     * @param idGiftCertificate is the id of gift certificate
     * @param idTag             is the id of tag
     * @return the optional value of gift certificate
     */
    @Query(value = "FROM GiftCertificate g JOIN FETCH g.tags t WHERE g.id = :idGiftCertificate AND t.id = :idTag")
    Optional<GiftCertificate> findByIdTagInGiftCertificate(@Param("idGiftCertificate") Long idGiftCertificate,
                                                           @Param("idTag") Long idTag);
}