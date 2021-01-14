package com.epam.esm.model.converter.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateConverterImpl implements CommonConverter<GiftCertificate, GiftCertificateDto> {
    @Override
    public GiftCertificateDto convertTo(GiftCertificate entity) {
        return new GiftCertificateDto(entity.getIdGiftCertificate(),
                entity.getNameGiftCertificate(), entity.getDescription(), entity.getPrice(), entity.getDuration(),
                entity.getCreateDate(), entity.getLastUpdateDate(),entity.getTags());
    }

    @Override
    public GiftCertificate convertFrom(GiftCertificateDto entity) {
        GiftCertificate giftCertificate = new GiftCertificate();
        if (entity.getIdGiftCertificate() != null) {
            giftCertificate.setIdGiftCertificate(entity.getIdGiftCertificate());
        }
        giftCertificate.setNameGiftCertificate(entity.getNameGiftCertificate());
        giftCertificate.setDescription(entity.getDescription());
        giftCertificate.setPrice(entity.getPrice());
        giftCertificate.setDuration(entity.getDuration());
        return giftCertificate;
    }

    @Override
    public List<GiftCertificateDto> convertTo(List<GiftCertificate> entities) {
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        entities.forEach(g -> giftCertificatesDto.add(convertTo(g)));
        return giftCertificatesDto;
    }
}