package com.epam.esm.service.impl;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.model.User;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.converter.impl.PurchaseResponseConverterImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
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
    private final UserDao userDao;
    private final PurchaseResponseConverterImpl purchaseResponseConverter;
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateConverterImpl giftCertificateConverter;

    @Override
    public List<PurchaseResponseDto> findAll(Pagination pagination) {
        List<Purchase> purchases = purchaseDao.findAll(pagination.getLimit(), pagination.getOffset());
        return purchaseResponseConverter.convertTo(purchases);
    }

    @Override
    public PurchaseResponseDto findById(Long id) {
        Purchase purchase = purchaseDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Purchase.class.getSimpleName()));
        return purchaseResponseConverter.convertTo(purchase);
    }

    @Override
    @Transactional
    public PurchaseResponseDto makePurchase(PurchaseRequestDto purchaseRequestDto) {
        List<Long> idGiftCertificates = purchaseRequestDto.getIdGiftCertificates();
        Long idUser = purchaseRequestDto.getIdUser();
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        idGiftCertificates.stream().map(giftCertificateService::findById)
                .forEach(g -> giftCertificates.add(giftCertificateConverter.convertFrom(g)));
        User user = userDao.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, idUser,
                        User.class.getSimpleName()));
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setGiftCertificates(giftCertificates);
        purchase.setCost(countCost(giftCertificates));
        purchase.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now()));
        purchase = purchaseDao.add(purchase);
        return purchaseResponseConverter.convertTo(purchase);
    }

    private BigDecimal countCost(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}