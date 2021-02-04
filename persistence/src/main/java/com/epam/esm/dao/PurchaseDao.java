package com.epam.esm.dao;

import com.epam.esm.model.Purchase;

import java.util.List;

public interface PurchaseDao extends CommonDao<Purchase> {
    List<Purchase> findByIdGiftCertificate(Long idGiftCertificate);
}