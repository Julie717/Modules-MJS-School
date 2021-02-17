package com.epam.esm.model.converter.impl;

import com.epam.esm.model.Purchase;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.model.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseResponseConverterImpl implements CommonConverter<Purchase, PurchaseResponseDto> {
    @Override
    public PurchaseResponseDto convertTo(Purchase entity) {
        PurchaseResponseDto purchase = null;
        if (entity != null) {
            Long idUser = entity.getUser().getId();
            List<Long> idGiftCertificates = entity.getGiftCertificates().stream().map(g -> g.getId())
                    .collect(Collectors.toList());
            purchase = new PurchaseResponseDto(entity.getId(), entity.getCost(), entity.getPurchaseDate(), idUser,
                    idGiftCertificates);
        }
        return purchase;
    }

    @Override
    public Purchase convertFrom(PurchaseResponseDto entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PurchaseResponseDto> convertTo(List<Purchase> entities) {
        List<PurchaseResponseDto> purchasesDto = new ArrayList<>();
        entities.forEach(t -> purchasesDto.add(convertTo(t)));
        return purchasesDto;
    }

    @Override
    public List<Purchase> convertFrom(List<PurchaseResponseDto> entities) {
        throw new UnsupportedOperationException();
    }
}