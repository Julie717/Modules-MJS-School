package com.epam.esm.repository;

import com.epam.esm.model.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Purchase repository.
 */
@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long> {
    /**
     * Find purchases by id of user.
     *
     * @param idUser   is the id of user
     * @param pageable is an element of pagination that consists of the number of pages and
     *                 the amount of record on each page.
     * @return the list of purchases
     */
    @Query(value = "FROM Purchase p JOIN FETCH p.giftCertificates g JOIN FETCH p.user u WHERE u.id = :id")
    List<Purchase> findByIdUser(@Param("id") Long idUser, Pageable pageable);

    /**
     * Find purchases by id of gift certificate.
     *
     * @param idGiftCertificate is the id of gift certificate
     * @return the list of purchases
     */
    @Query(value = "FROM Purchase p JOIN FETCH p.giftCertificates g WHERE g.id = :id")
    List<Purchase> findByIdGiftCertificate(@Param("id") Long idGiftCertificate);
}