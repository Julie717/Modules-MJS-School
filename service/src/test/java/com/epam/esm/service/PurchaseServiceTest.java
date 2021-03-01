package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.User;
import com.epam.esm.model.Role;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.converter.impl.PurchaseResponseConverterImpl;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.repository.PurchaseRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.PurchaseServiceImpl;
import com.epam.esm.util.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
    private static final Purchase PURCHASE;
    private static final PurchaseRequestDto PURCHASE_REQUEST_DTO;
    private static final PurchaseResponseDto PURCHASE_RESPONSE_DTO;
    private static final GiftCertificateDto GIFT_CERTIFICATE_SKATING;
    private static final GiftCertificateDto GIFT_CERTIFICATE_FITNESS;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateService giftCertificateService;

    @Spy
    private final PurchaseResponseConverterImpl purchaseResponseConverter = new PurchaseResponseConverterImpl();

    @Spy
    private final TagConverterImpl tagConverter = new TagConverterImpl();

    @Spy
    private final GiftCertificateConverterImpl giftCertificateConverter = new GiftCertificateConverterImpl(tagConverter);

    static {
        User user = new User();
        user.setId(2L);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null));
        giftCertificates.add(new GiftCertificate(7L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), null));
        PURCHASE = new Purchase(10L, BigDecimal.valueOf(90),
                Timestamp.valueOf("2021-01-12 11:34:18"), user, giftCertificates);
        List<Long> idGiftCertificates = new ArrayList<>();
        idGiftCertificates.add(1L);
        idGiftCertificates.add(7L);
        PURCHASE_REQUEST_DTO = new PurchaseRequestDto(2L, idGiftCertificates);
        GIFT_CERTIFICATE_SKATING = new GiftCertificateDto(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        GIFT_CERTIFICATE_FITNESS = new GiftCertificateDto(7L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), null);
        PURCHASE_RESPONSE_DTO = new PurchaseResponseDto(10L, BigDecimal.valueOf(90),
                Timestamp.valueOf("2021-01-12 11:34:18"), user.getId(), idGiftCertificates);
    }

    @Test
    void findAllTestPositive() {
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(PURCHASE);
        Mockito.when(purchaseRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(purchases));
        Pagination pagination = new Pagination(10, 2);
        List<PurchaseResponseDto> expected = new ArrayList<>();
        expected.add(PURCHASE_RESPONSE_DTO);

        List<PurchaseResponseDto> actual = purchaseService.findAll(pagination, 4L, Role.ROLE_ADMIN.name());

        assertEquals(expected, actual);
    }

    @Test
    void findAllTestNegative() {
        Mockito.when(purchaseRepository.findByIdUser(anyLong(), any(Pageable.class))).thenReturn(new ArrayList<>());
        Pagination pagination = new Pagination(10, 2);

        assertThrows(ResourceNotFoundException.class, () -> purchaseService.findAll(pagination, 1L, Role.ROLE_USER.name()));
    }

    @Test
    void findAllTestUser() {
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(PURCHASE);
        Mockito.when(purchaseRepository.findByIdUser(anyLong(), any(Pageable.class)))
                .thenReturn(purchases);
        Pagination pagination = new Pagination(10, 2);
        List<PurchaseResponseDto> expected = new ArrayList<>();
        expected.add(PURCHASE_RESPONSE_DTO);

        List<PurchaseResponseDto> actual = purchaseService.findAll(pagination, 4L, Role.ROLE_USER.name());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestPositive() {
        PurchaseResponseDto expected = PURCHASE_RESPONSE_DTO;
        Long id = 10L;
        Mockito.when(purchaseRepository.findById(id)).thenReturn(Optional.of(PURCHASE));

        PurchaseResponseDto actual = purchaseService.findById(id, 4L, Role.ROLE_ADMIN.name());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(purchaseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchaseService.findById(id, 4L, Role.ROLE_ADMIN.name()));
    }

    @Test
    void makePurchaseTestPositive() {
        BigDecimal expectedCost = BigDecimal.valueOf(90);
        Mockito.when(giftCertificateService.findById(GIFT_CERTIFICATE_SKATING.getId())).thenReturn(GIFT_CERTIFICATE_SKATING);
        Mockito.when(giftCertificateService.findById(GIFT_CERTIFICATE_FITNESS.getId())).thenReturn(GIFT_CERTIFICATE_FITNESS);
        Mockito.when(userRepository.findById(PURCHASE_REQUEST_DTO.getIdUser())).thenReturn(Optional.of(PURCHASE.getUser()));
        Mockito.when(purchaseRepository.save(any(Purchase.class))).thenReturn(PURCHASE);

        PurchaseResponseDto actual = purchaseService.makePurchase(PURCHASE_REQUEST_DTO);

        assertEquals(expectedCost, actual.getCost());
    }

    @Test
    void makePurchaseTestUserNotFound() {
        Mockito.when(giftCertificateService.findById(anyLong())).thenReturn(new GiftCertificateDto());
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        List<Long> idGiftCertificates = new ArrayList<>();
        idGiftCertificates.add(1L);
        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(2L, idGiftCertificates);

        assertThrows(ResourceNotFoundException.class, () -> purchaseService.makePurchase(purchaseRequestDto));
    }

    @Test
    void makePurchaseTestGiftCertificateNotFound() {
        Mockito.when(giftCertificateService.findById(anyLong())).thenThrow(new ResourceNotFoundException());
        List<Long> idGiftCertificates = new ArrayList<>();
        idGiftCertificates.add(1L);
        idGiftCertificates.add(7L);
        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(2L, idGiftCertificates);

        assertThrows(ResourceNotFoundException.class, () -> purchaseService.makePurchase(purchaseRequestDto));
    }
}