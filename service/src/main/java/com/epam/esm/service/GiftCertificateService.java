package com.epam.esm.service;

import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.TagDto;

import java.util.List;
import java.util.Map;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {
    /**
     * Find all gift certificates.
     *
     * @return the list
     */
    List<GiftCertificateDto> findAll();

    /**
     * Find all gift certificates including appropriate tags.
     *
     * @return the list
     */
    List<GiftCertificateDto> findAllWithTags();

    /**
     * Find gift certificate by id.
     *
     * @param idGiftCertificate the id gift certificate
     * @return the gift certificate dto
     */
    GiftCertificateDto findById(int idGiftCertificate);

    /**
     * Add gift certificate to db.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto add(GiftCertificateDto giftCertificateDto);

    /**
     * Delete gift certificate by id from Db.
     *
     * @param idGiftCertificate the id gift certificate
     */
    void deleteById(int idGiftCertificate);

    /**
     * Find gift certificates by parameters.
     *
     * @param parameters the parameters
     * @return the list
     */
    List<GiftCertificateDto> findByParameters(Map<String, String> parameters);

    /**
     * Update gift certificate parameters.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Find gift certificate including appropriate tags.
     *
     * @param idGiftCertificate the id gift certificate
     * @return the gift certificate dto
     */
    GiftCertificateDto findGiftCertificateWithTags(int idGiftCertificate);

    /**
     * Find gift certificate including appropriate tags with nameTag.
     *
     * @param idGiftCertificate the id gift certificate
     * @param nameTag           the name tag
     * @return the gift certificate dto
     */
    GiftCertificateDto findGiftCertificateWithTagsByTagName(int idGiftCertificate, String nameTag);

    /**
     * Add tags to gift certificate.
     *
     * @param idGiftCertificate the id gift certificate
     * @param tagsDto           the tags dto
     * @return the gift certificate dto
     */
    GiftCertificateDto addTagsToGiftCertificate(int idGiftCertificate, List<TagDto> tagsDto);
}
