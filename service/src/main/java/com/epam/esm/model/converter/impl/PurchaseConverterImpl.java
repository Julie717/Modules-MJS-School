package com.epam.esm.model.converter.impl;

import com.epam.esm.model.*;
import com.epam.esm.model.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseConverterImpl implements CommonConverter<Purchase, PurchaseRequestDto> {
    @Override
    public PurchaseRequestDto convertTo(Purchase entity) {
        Long idUser = entity.getUser().getId();
        List<Long> idGiftCertificates = entity.getGiftCertificates().stream().map(g -> g.getId())
                .collect(Collectors.toList());
        return new PurchaseResponseDto(entity.getId(), entity.getCost(), entity.getPurchaseDate(), idUser,
                idGiftCertificates);
    }

    @Override
    public Purchase convertFrom(PurchaseRequestDto entity) {
        User user = new User();
        user.setId(entity.getIdUser());
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        entity.getIdGiftCertificates().stream().map(g -> {
                    GiftCertificate giftCertificate = new GiftCertificate();
                    giftCertificate.setId(g);
                    return giftCertificate;
                }
        ).collect(Collectors.toList());
        Purchase purchase=new Purchase();
        purchase.setUser(user);
        purchase.setGiftCertificates(giftCertificates);
        return purchase;
       /* User user = new User();
        user.setId(entity.getIdUser());
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        entity.getIdGiftCertificates().stream().map(g -> {
                    GiftCertificate giftCertificate = new GiftCertificate();
                    giftCertificate.setId(g);
                    return giftCertificate;
                }
        ).collect(Collectors.toList());
        return new Purchase(entity.getId(), entity.getCost(), entity.getPurchaseDate(), user,
                giftCertificates);*/
    }

    @Override
    public List<PurchaseRequestDto> convertTo(List<Purchase> entities) {
        List<PurchaseRequestDto> purchasesDto = new ArrayList<>();
        entities.forEach(t -> purchasesDto.add(convertTo(t)));
        return purchasesDto;
    }

    @Override
    public List<Purchase> convertFrom(List<PurchaseRequestDto> entities) {
        List<Purchase> purchases = new ArrayList<>();
        entities.forEach(t -> purchases.add(convertFrom(t)));
        return purchases;
    }
}