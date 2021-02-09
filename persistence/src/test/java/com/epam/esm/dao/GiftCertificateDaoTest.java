package com.epam.esm.dao;

import com.epam.esm.config.DaoConfigTest;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest(classes = GiftCertificateDaoImpl.class)
@ContextConfiguration(classes = DaoConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class GiftCertificateDaoTest {
    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void findByIdTestPositive() {
        Long id = 4L;
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(3L, "jumping"));
        tags.add(new Tag(6L, "relax"));
        tags.add(new Tag(7L, "make you fun"));
        GiftCertificate giftCertificate = new GiftCertificate(4L, "Trampoline jumping",
                "Trampoline jumping can be fun.", BigDecimal.valueOf(20), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags);
        Optional<GiftCertificate> expected = Optional.of(giftCertificate);

        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNotFound() {
        Long id = 25L;

        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);

        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(7L, "make you fun"));
        expected.add(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(7L, "make you fun"));
        expected.add(new GiftCertificate(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(4L, "riding"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(6L, "relax"));
        expected.add(new GiftCertificate(3L, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(3L, "jumping"));
        tags.add(new Tag(6L, "relax"));
        tags.add(new Tag(7L, "make you fun"));
        expected.add(new GiftCertificate(4L, "Trampoline jumping",
                "Trampoline jumping can be fun.", BigDecimal.valueOf(20), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags));
        Integer limit = 10;
        Integer offset = 0;

        List<GiftCertificate> actual = giftCertificateDao.findAll(limit, offset);

        assertEquals(expected, actual);
    }

    @Test
    void findAllFromOffsetPositionTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(7L, "make you fun"));
        expected.add(new GiftCertificate(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(4L, "riding"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(6L, "relax"));
        expected.add(new GiftCertificate(3L, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags));
        Integer limit = 2;
        Integer offset = 1;

        List<GiftCertificate> actual = giftCertificateDao.findAll(limit, offset);

        assertEquals(expected, actual);
    }

    @Test
    void findByNameTestPositive() {
        String name = "Horseback riding";
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(4L, "riding"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(6L, "relax"));
        GiftCertificate giftCertificate = new GiftCertificate(3L, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags);
        Optional<GiftCertificate> expected = Optional.of(giftCertificate);

        Optional<GiftCertificate> actual = giftCertificateDao.findByName(name);

        assertEquals(expected, actual);
    }

    @Test
    void findByNameTestNotFound() {
        String name = "gift";

        Optional<GiftCertificate> actual = giftCertificateDao.findByName(name);

        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findByParametersTest() {
        String query = "FROM GiftCertificate gift JOIN FETCH gift.tags tag " +
                "WHERE gift.name LIKE CONCAT('%', 'a', '%') AND gift.description LIKE CONCAT('%', 'is', '%') " +
                "ORDER BY gift.name ASC";
        Integer limit = 20;
        Integer offset = 0;

        List<GiftCertificate> actual = giftCertificateDao.findByParameters(query, limit, offset);

        List<GiftCertificate> expected = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(4L, "riding"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(6L, "relax"));
        expected.add(new GiftCertificate(3L, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(7L, "make you fun"));
        expected.add(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags));
        assertEquals(expected, actual);
    }

    @Test
    void findByParametersNameTagTest() {
        String query = "FROM GiftCertificate gift JOIN FETCH gift.tags tag " +
                "WHERE tag.name LIKE CONCAT('%', 'a', '%') OR tag.name LIKE CONCAT('%', 'if', '%') " +
                "ORDER BY gift.name DESC";
        Integer limit = 2;
        Integer offset = 2;

        List<GiftCertificate> actual = giftCertificateDao.findByParameters(query, limit, offset);

        List<GiftCertificate> expected = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(6L, "relax"));
        expected.add(new GiftCertificate(3L, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(7L, "make you fun"));
        expected.add(new GiftCertificate(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags));

        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdTestPositive() {
        Long id = 2L;
        Integer limit = 1;
        Integer offset = 0;

        List<GiftCertificate> actual = giftCertificateDao.findByTagId(id, limit, offset);

        List<GiftCertificate> expected = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        expected.add(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags));
        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdTestNotFound() {
        Long id = 25L;
        Integer limit = 10;
        Integer offset = 0;

        List<GiftCertificate> actual = giftCertificateDao.findByTagId(id, limit, offset);

        List<GiftCertificate> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdInGiftCertificateTestPositive() {
        Long idGiftCertificate = 1L;
        Long idTag = 2L;

        Optional<GiftCertificate> actual = giftCertificateDao.findByTagIdInGiftCertificate(idGiftCertificate, idTag);

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        Optional<GiftCertificate> expected = Optional.of(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags));
        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdInGiftCertificateTestNotFound() {
        Long idGiftCertificate = 1L;
        Long idTag = 27L;

        Optional<GiftCertificate> actual = giftCertificateDao.findByTagIdInGiftCertificate(idGiftCertificate, idTag);

        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }


    @Test
    @Transactional
    void addTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Spa");
        giftCertificate.setDuration(30);
        giftCertificate.setPrice(BigDecimal.valueOf(70));
        giftCertificate.setDescription("Good relax");
        giftCertificate.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        giftCertificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));

        GiftCertificate actual = giftCertificateDao.add(giftCertificate);

        GiftCertificate expected = giftCertificate;
        giftCertificate.setId(5L);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void updateTest() {
        GiftCertificate giftCertificate = new GiftCertificate(1L, "Skating",
                "Sales", BigDecimal.valueOf(50), 10, Timestamp.valueOf(LocalDateTime.now()),
                null, null);

        GiftCertificate actual = giftCertificateDao.update(giftCertificate);

        GiftCertificate expected = giftCertificate;
        expected.setLastUpdateDate(actual.getLastUpdateDate());
        assertEquals(expected, actual);
    }
}