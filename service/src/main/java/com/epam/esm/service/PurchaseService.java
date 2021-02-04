package com.epam.esm.service;

import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.util.Pagination;

import java.util.List;

public interface PurchaseService {
    List<PurchaseResponseDto> findAll(Pagination pagination);
    PurchaseResponseDto findById(Long id);
    PurchaseResponseDto makePurchase(PurchaseRequestDto purchase);
}