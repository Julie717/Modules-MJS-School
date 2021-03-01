package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfigTest;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = PersistenceConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PurchaseRepositoryTest {
    private static final Purchase PURCHASE;

    @Autowired
    private PurchaseRepository purchaseRepository;

    static {
        PURCHASE = new Purchase();
        User user = new User();
        user.setId(2L);
        PURCHASE.setUser(user);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(2L);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        PURCHASE.setGiftCertificates(giftCertificates);
        PURCHASE.setCost(BigDecimal.valueOf(80));
        PURCHASE.setPurchaseDate(Timestamp.valueOf("2021-01-12 11:34:18"));
    }

    @Test
    void findAllTest() {
        int expectedAmountOfPurchases = 7;
        Pageable pageable = PageRequest.of(0, 10);

        int actualAmountOfPurchases = purchaseRepository.findAll(pageable).getContent().size();

        assertEquals(expectedAmountOfPurchases, actualAmountOfPurchases);
    }

    @Test
    void findByIdTest() {
        BigDecimal expectedCost = BigDecimal.valueOf(20);
        Long id = 3L;

        BigDecimal actualCost = purchaseRepository.findById(id).get().getCost();

        assertEquals(expectedCost, actualCost);
    }

    @Test
    void findByIdUserTest() {
        int expectedAmountOfPurchases = 2;
        Pageable pageable = PageRequest.of(0, 10);
        Long id = 3L;

        int amountOfPurchases= purchaseRepository.findByIdUser(id,pageable).size();

        assertEquals(expectedAmountOfPurchases, amountOfPurchases);
    }

    @Test
    void findByIdGiftCertificateTest() {
        int expectedAmountOfPurchases = 3;
        Long id = 4L;

        int actualAmountOfPurchases = purchaseRepository.findByIdGiftCertificate(id).size();

        assertEquals(expectedAmountOfPurchases, actualAmountOfPurchases);
    }

    @Test
    @Transactional
    void addTest() {
        int expectedPurchaseId = 8;

        Long actualPurchaseId = purchaseRepository.save(PURCHASE).getId();

        assertEquals(expectedPurchaseId, actualPurchaseId);
    }
}