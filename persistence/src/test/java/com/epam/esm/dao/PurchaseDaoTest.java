package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PurchaseDaoTest {
    @Autowired
    private PurchaseDao purchaseDao;

    @Test
    void findAllTest() {
        Integer limit = 10;
        Integer offset = 0;

        int actualAmountOfPurchases = purchaseDao.findAll(limit, offset).size();

        int expectedAmountOfPurchases = 7;
        assertEquals(expectedAmountOfPurchases, actualAmountOfPurchases);
    }

    @Test
    void findByIdTest() {
        Long id = 3L;

        BigDecimal actualCost = purchaseDao.findById(id).get().getCost();

        BigDecimal expectedCost = BigDecimal.valueOf(20).setScale(2);
        assertEquals(expectedCost, actualCost);
    }

    @Test
    void findByIdGiftCertificateTest() {
        Long id = 4L;

        int actualAmountOfPurchases = purchaseDao.findByIdGiftCertificate(id).size();

        int expectedAmountOfPurchases = 3;
        assertEquals(expectedAmountOfPurchases, actualAmountOfPurchases);
    }

    @Test
    @Transactional
    void addTest() {
        Purchase purchase = new Purchase();
        User user = new User();
        user.setId(2L);
        purchase.setUser(user);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(2L);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        purchase.setGiftCertificates(giftCertificates);
        purchase.setCost(BigDecimal.valueOf(80));
        purchase.setPurchaseDate(Timestamp.valueOf("2021-01-12 11:34:18"));

        Long actualPurchaseId = purchaseDao.add(purchase).getId();

        int expectedPurchaseId = 8;
        assertEquals(expectedPurchaseId, actualPurchaseId);
    }
}