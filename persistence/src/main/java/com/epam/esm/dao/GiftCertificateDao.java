package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends CommonDao<GiftCertificate> {
    /**
     * Find gift certificate by name in Db.
     *
     * @param nameGiftCertificate the name gift certificate
     * @return the optional
     */
    Optional<GiftCertificate> findByName(String nameGiftCertificate);

    /**
     * Find gift certificates by parameters in Db.
     *
     * @param queryLastPart the query last part
     * @return the list
     */
    List<GiftCertificate> findByParameters(String queryLastPart);

    List<GiftCertificate> findByTagId(Long idTag);
    Optional<GiftCertificate> findByTagIdInGiftCertificate(Long idGiftCertificate, Long idTag);

    /**
     * Update parameter of a gift certificate in Db.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Delete tag from gift certificate.
     *
     * @param idGiftCertificate the id gift certificate
     * @param idTag the id tag
     * @return the boolean
     */
    void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag);
}