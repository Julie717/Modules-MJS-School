package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfigTest;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = PersistenceConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class GiftCertificateRepositoryTest {
    private static final GiftCertificate GIFT_CERTIFICATE_TRAMPOLINE_JUMPING;
    private static final GiftCertificate GIFT_CERTIFICATE_SKATING;
    private static final GiftCertificate GIFT_CERTIFICATE_SKATING_WITH_ONE_TAG;
    private static final GiftCertificate GIFT_CERTIFICATE_FITNESS;
    private static final GiftCertificate GIFT_CERTIFICATE_HORSEBACK_RIDING;
    private static final GiftCertificate GIFT_CERTIFICATE_SPA;

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    static {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(3L, "jumping"));
        tags.add(new Tag(6L, "relax"));
        tags.add(new Tag(7L, "make you fun"));
        GIFT_CERTIFICATE_TRAMPOLINE_JUMPING = new GiftCertificate(4L, "Trampoline jumping",
                "Trampoline jumping can be fun.", BigDecimal.valueOf(20), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags);
        tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(7L, "make you fun"));
        GIFT_CERTIFICATE_SKATING = new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags);
        tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        GIFT_CERTIFICATE_SKATING_WITH_ONE_TAG = new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags);
        tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(7L, "make you fun"));
        GIFT_CERTIFICATE_FITNESS = new GiftCertificate(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags);
        tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(4L, "riding"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(6L, "relax"));
        GIFT_CERTIFICATE_HORSEBACK_RIDING = new GiftCertificate(3L, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags);
        GIFT_CERTIFICATE_SPA = new GiftCertificate();
        GIFT_CERTIFICATE_SPA.setName("Spa");
        GIFT_CERTIFICATE_SPA.setDuration(30);
        GIFT_CERTIFICATE_SPA.setPrice(BigDecimal.valueOf(70));
        GIFT_CERTIFICATE_SPA.setDescription("Good relax");
        GIFT_CERTIFICATE_SPA.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        GIFT_CERTIFICATE_SPA.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    @Transactional
    void findByIdTestPositive() {
        Optional<GiftCertificate> expected = Optional.of(GIFT_CERTIFICATE_TRAMPOLINE_JUMPING);
        Long id = 4L;

        Optional<GiftCertificate> actual = giftCertificateRepository.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNotFound() {
        Optional<GiftCertificate> expected = Optional.empty();
        Long id = 25L;

        Optional<GiftCertificate> actual = giftCertificateRepository.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void findAllTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(GIFT_CERTIFICATE_SKATING);
        expected.add(GIFT_CERTIFICATE_FITNESS);
        expected.add(GIFT_CERTIFICATE_HORSEBACK_RIDING);
        expected.add(GIFT_CERTIFICATE_TRAMPOLINE_JUMPING);
        Pageable pageable = PageRequest.of(0, 4);

        List<GiftCertificate> actual = giftCertificateRepository.findAll(pageable).getContent();

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void findAllFromOffsetPositionTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(GIFT_CERTIFICATE_HORSEBACK_RIDING);
        expected.add(GIFT_CERTIFICATE_TRAMPOLINE_JUMPING);
        Pageable pageable = PageRequest.of(1, 2);

        List<GiftCertificate> actual = giftCertificateRepository.findAll(pageable).getContent();

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void findByNameTestPositive() {
        Optional<GiftCertificate> expected = Optional.of(GIFT_CERTIFICATE_HORSEBACK_RIDING);
        String name = "Horseback riding";

        Optional<GiftCertificate> actual = giftCertificateRepository.findByName(name);

        assertEquals(expected, actual);
    }

    @Test
    void findByNameTestNotFound() {
        Optional<GiftCertificate> expected = Optional.empty();
        String name = "gift";

        Optional<GiftCertificate> actual = giftCertificateRepository.findByName(name);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTagTestPositive() {
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(GIFT_CERTIFICATE_SKATING_WITH_ONE_TAG);
        Long id = 2L;
        Pageable pageable = PageRequest.of(0, 1);

        List<GiftCertificate> actual = giftCertificateRepository.findByIdTag(id, pageable);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTagTestNotFound() {
        List<GiftCertificate> expected = new ArrayList<>();
        Long id = 25L;
        Pageable pageable = PageRequest.of(5, 40);

        List<GiftCertificate> actual = giftCertificateRepository.findByIdTag(id, pageable);

        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdInGiftCertificateTestPositive() {
        Optional<GiftCertificate> expected = Optional.of(GIFT_CERTIFICATE_SKATING_WITH_ONE_TAG);
        Long idGiftCertificate = 1L;
        Long idTag = 2L;

        Optional<GiftCertificate> actual = giftCertificateRepository.findByIdTagInGiftCertificate(idGiftCertificate, idTag);

        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdInGiftCertificateTestNotFound() {
        Optional<GiftCertificate> expected = Optional.empty();
        Long idGiftCertificate = 1L;
        Long idTag = 27L;

        Optional<GiftCertificate> actual = giftCertificateRepository.findByIdTagInGiftCertificate(idGiftCertificate, idTag);

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void addTest() {
        GiftCertificate actual = giftCertificateRepository.save(GIFT_CERTIFICATE_SPA);

        GiftCertificate expected = GIFT_CERTIFICATE_SPA;
        expected.setId(5L);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void updateTest() {
        GiftCertificate giftCertificate = new GiftCertificate(1L, "Skating",
                "Sales", BigDecimal.valueOf(50), 10, Timestamp.valueOf(LocalDateTime.now()),
                null, null);

        GiftCertificate actual = giftCertificateRepository.save(giftCertificate);

        GiftCertificate expected = giftCertificate;
        expected.setLastUpdateDate(actual.getLastUpdateDate());
        assertEquals(expected, actual);
    }
}