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
     * Find gift certificate by id.
     *
     * @param id the id gift certificate
     * @return the gift certificate dto
     */
    GiftCertificateDto findById(Long id);

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
     * @param id the id gift certificate
     */
    void deleteById(Long id);


    List<GiftCertificateDto> findByParameters(Map<String, String> parameters);

    List<GiftCertificateDto> findByTagId(Long idTag);

    GiftCertificateDto findGiftCertificateByTagId(Long idGiftCertificate, Long idTag);

    /**
     * Update gift certificate parameters.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto patchGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Add tags to gift certificate.
     *
     * @param id      the id gift certificate
     * @param tagsDto the tags dto
     * @return the gift certificate dto
     */
    GiftCertificateDto addTagsToGiftCertificate(Long id, List<TagDto> tagsDto);

    /**
     * Delete tag from gift certificate.
     *
     * @param idGiftCertificate the id gift certificate
     * @param idTag             the id tag
     */
    void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag);
}
