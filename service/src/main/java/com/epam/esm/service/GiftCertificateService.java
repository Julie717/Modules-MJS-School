package com.epam.esm.service;

import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.TagDto;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    List<GiftCertificateDto> findAll();

    List<GiftCertificateDto> findAllWithTags();

    GiftCertificateDto findById(int idGiftCertificate);

    GiftCertificateDto add(GiftCertificateDto giftCertificateDto);

    void deleteById(int idGiftCertificate);

    List<GiftCertificateDto> findByParameters(Map<String, String> parameters);

    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto findGiftCertificateWithTags(int idGiftCertificate);

    GiftCertificateDto findGiftCertificateWithTagsByTagName(int idGiftCertificate, String nameTag);

    GiftCertificateDto addTagsToGiftCertificate(int idGiftCertificate, List<TagDto> tagsDto);
}
