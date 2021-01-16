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
    Optional<GiftCertificate> findGiftCertificateByName(String nameGiftCertificate);

    /**
     * Find gift certificates by parameters in Db.
     *
     * @param queryLastPart the query last part
     * @return the list
     */
    List<GiftCertificate> findByParameters(String queryLastPart);

    /**
     * Update parameter of a gift certificate in Db.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Find gift certificate in Db including tags.
     *
     * @param idGiftCertificate the id gift certificate
     * @return the optional
     */
    Optional<GiftCertificate> findGiftCertificateWithTags(int idGiftCertificate);

    /**
     * Find gift certificate in Db including tags that have a nameTag.
     *
     * @param idGiftCertificate the id gift certificate
     * @param nameTag           the name tag
     * @return the optional
     */
    Optional<GiftCertificate> findGiftCertificateWithTagsByTagName(int idGiftCertificate, String nameTag);

    /**
     * Add tag to gift certificate.
     *
     * @param idGiftCertificate the id gift certificate
     * @param idTag             the id tag
     */
    void addTagToGiftCertificate(int idGiftCertificate, int idTag);

    /**
     * Check existence of gift certificate with current tag.
     *
     * @param idGiftCertificate the id gift certificate
     * @param idTag             the id tag
     * @return the boolean
     */
    Boolean isGiftCertificateWithTagExist(int idGiftCertificate, int idTag);

    /**
     * Find all gift certificates in Db including tags.
     *
     * @return the list
     */
    List<GiftCertificate> findAllWithTags();

    /**
     * Delete tag from gift certificate.
     *
     * @param idGiftCertificate the id gift certificate
     * @return the boolean
     */
    boolean deleteFromGiftCertificateTag(int idGiftCertificate);
}