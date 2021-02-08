package com.epam.esm.service;

import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.User;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.converter.impl.PurchaseResponseConverterImpl;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.service.impl.PurchaseServiceImpl;
import com.epam.esm.util.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Mock
    private PurchaseDao purchaseDao;

    @Mock
    private UserDao userDao;

    @Mock
    private GiftCertificateService giftCertificateService;

    @Spy
    private final PurchaseResponseConverterImpl purchaseResponseConverter = new PurchaseResponseConverterImpl();

    @Spy
    private final TagConverterImpl tagConverter = new TagConverterImpl();

    @Spy
    private final GiftCertificateConverterImpl giftCertificateConverter = new GiftCertificateConverterImpl(tagConverter);

    @Test
    void findAllTest() {
        List<Purchase> purchases = new ArrayList<>();
        Mockito.when(purchaseDao.findAll(anyInt(), anyInt())).thenReturn(purchases);
        Pagination pagination = new Pagination(10, 2);
        List<PurchaseResponseDto> expected = new ArrayList<>();

        List<PurchaseResponseDto> actual = purchaseService.findAll(pagination);

        verify(purchaseResponseConverter).convertTo(purchases);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestPositive() {
        User user = new User();
        user.setId(2L);
        Long id = 5L;
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null));
        Optional<Purchase> purchase = Optional.of(new Purchase(id, BigDecimal.valueOf(50),
                Timestamp.valueOf("2021-01-12 11:34:18"), user, giftCertificates));
        Mockito.when(purchaseDao.findById(id)).thenReturn(purchase);
        List<Long> idGiftCertificates = new ArrayList<>();
        idGiftCertificates.add(1L);
        PurchaseResponseDto expected = new PurchaseResponseDto(id, BigDecimal.valueOf(50),
                Timestamp.valueOf("2021-01-12 11:34:18"), 2L, idGiftCertificates);

        PurchaseResponseDto actual = purchaseService.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(purchaseDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchaseService.findById(id));
    }

    @Test
    void makePurchaseTestPositive() {
        Long idUser = 2L;
        List<Long> idGiftCertificates = new ArrayList<>();
        idGiftCertificates.add(1L);
        idGiftCertificates.add(7L);
        PurchaseRequestDto purchaseRequestDto = new PurchaseRequestDto(2L, idGiftCertificates);
        GiftCertificateDto giftCertificateDto1 = new GiftCertificateDto(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        GiftCertificateDto giftCertificateDto2 = new GiftCertificateDto(7L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), null);
        Mockito.when(giftCertificateService.findById(1L)).thenReturn(giftCertificateDto1);
        Mockito.when(giftCertificateService.findById(7L)).thenReturn(giftCertificateDto2);
        User user = new User();
        user.setId(idUser);
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
        Purchase purchase = new Purchase(10L, BigDecimal.valueOf(90),
                Timestamp.valueOf("2021-01-12 11:34:18"), user, giftCertificates);
        Mockito.when(userDao.findById(idUser)).thenReturn(Optional.of(user));
        Mockito.when(purchaseDao.add(any(Purchase.class))).thenReturn(purchase);
        PurchaseResponseDto expected = new PurchaseResponseDto(10L, BigDecimal.valueOf(90),
                Timestamp.valueOf("2021-01-12 11:34:18"), idUser, idGiftCertificates);

        PurchaseResponseDto actual = purchaseService.makePurchase(purchaseRequestDto);

        Timestamp purchaseDate = Timestamp.valueOf(LocalDateTime.now());
        actual.setPurchaseDate(purchaseDate);
        expected.setPurchaseDate(purchaseDate);
        assertEquals(expected, actual);
    }

    @Test
    void makePurchaseTestUserNotFound() {
        Mockito.when(giftCertificateService.findById(anyLong())).thenReturn(new GiftCertificateDto());
        Mockito.when(userDao.findById(anyLong())).thenReturn(Optional.empty());
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