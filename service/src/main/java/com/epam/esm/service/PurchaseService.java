package com.epam.esm.service;

import com.epam.esm.model.PurchaseRequestDto;

import java.util.List;

public interface PurchaseService {
    List<PurchaseRequestDto> findAll();
    PurchaseRequestDto findById(Long id);
    PurchaseRequestDto makePurchase(PurchaseRequestDto purchase);
}