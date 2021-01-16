package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends CommonDao<GiftCertificate> {
    Optional<GiftCertificate> findGiftCertificateByName(String nameGiftCertificate);

    List<GiftCertificate> findByParameters(String queryLastPart);

    GiftCertificate update(GiftCertificate giftCertificate);

    Optional<GiftCertificate> findGiftCertificateWithTags(int idGiftCertificate);

    Optional<GiftCertificate> findGiftCertificateWithTagsByTagName(int idGiftCertificate, String nameTag);

    void addTagToGiftCertificate(int idGiftCertificate, int idTag);

    Boolean isGiftCertificateWithTagExist(int idGiftCertificate, int idTag);

    List<GiftCertificate> findAllWithTags();

    boolean deleteFromGiftCertificateTag(int idGiftCertificate);
}