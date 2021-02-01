package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.*;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.converter.impl.PurchaseConverterImpl;
import com.epam.esm.model.converter.impl.UserConverterImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ErrorMessageReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseDao purchaseDao;
    private final PurchaseConverterImpl purchaseConverter;
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateConverterImpl giftCertificateConverter;
    private final UserService userService;
    private final UserConverterImpl userConverter;

    @Override
    public List<PurchaseRequestDto> findAll() {
        List<Purchase> purchases = purchaseDao.findAll();
        return purchaseConverter.convertTo(purchases);
    }

    @Override
    public PurchaseRequestDto findById(Long id) {
        Purchase purchase = purchaseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Purchase.class.getSimpleName()));
        return purchaseConverter.convertTo(purchase);
    }

    @Override
    @Transactional
    public PurchaseRequestDto makePurchase(PurchaseRequestDto purchaseRequestDto) {
        List<Long> idGiftCertificates = purchaseRequestDto.getIdGiftCertificates();
        Long idUser = purchaseRequestDto.getIdUser();
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        idGiftCertificates.stream().map(id -> giftCertificateService.findById(id))
                .forEach(g -> giftCertificates.add(giftCertificateConverter.convertFrom(g)));
        User user = userConverter.convertFrom(userService.findById(idUser));
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setGiftCertificates(giftCertificates);
        purchase.setCost(countCost(giftCertificates));
        purchase.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now()));
        purchaseDao.add(purchase);
        return purchaseConverter.convertTo(purchase);
    }

    private BigDecimal countCost(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}