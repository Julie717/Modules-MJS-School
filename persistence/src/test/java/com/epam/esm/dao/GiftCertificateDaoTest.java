package com.epam.esm.dao;

import com.epam.esm.dao.extractor.*;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateDaoTest {
    private EmbeddedDatabase dataSource;
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:script.sql").addScript("classpath:script-inserts.sql").build();
        giftCertificateDao = new GiftCertificateDaoImpl(new JdbcTemplate(dataSource), new GiftCertificateResultSetExtractor(),
                new GiftCertificateWithTagResultSetExtractor(), new ListGiftCertificateResultSetExtractor(),
                new ListGiftCertificateWithTagResultSetExtractor());
    }

    @AfterEach
    void tearDown() {
        dataSource.shutdown();
        giftCertificateDao = null;
    }

    @Test
    void addTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setNameGiftCertificate("Spa");
        giftCertificate.setDuration(30);
        giftCertificate.setPrice(BigDecimal.valueOf(70));
        giftCertificate.setDescription("Good relax");
        giftCertificate.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        giftCertificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        GiftCertificate actual = giftCertificateDao.add(giftCertificate);
        GiftCertificate expected = giftCertificate;
        giftCertificate.setIdGiftCertificate(5);
        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdTestPositive() {
        int id = 1;
        boolean actual = giftCertificateDao.deleteById(id);
        assertTrue(actual);
    }

    @Test
    void deleteByIdTestNegative() {
        int id = 25;
        boolean actual = giftCertificateDao.deleteById(id);
        assertFalse(actual);
    }

    @Test
    void findByIdTestPositive() {
        int id = 4;
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        GiftCertificate giftCertificate = new GiftCertificate(4, "Trampoline jumping",
                "Trampoline jumping can be fun.", BigDecimal.valueOf(20), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), null);
        Optional<GiftCertificate> expected = Optional.of(giftCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdTestNotFound() {
        int id = 25;
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(new GiftCertificate(1, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null));
        expected.add(new GiftCertificate(2, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), null));
        expected.add(new GiftCertificate(3, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), null));
        expected.add(new GiftCertificate(4, "Trampoline jumping",
                "Trampoline jumping can be fun.", BigDecimal.valueOf(20), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), null));
        List<GiftCertificate> actual = giftCertificateDao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByNameTestPositive() {
        String name = "Horseback riding";
        Optional<GiftCertificate> actual = giftCertificateDao.findGiftCertificateByName(name);
        GiftCertificate giftCertificate = new GiftCertificate(3, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), null);
        Optional<GiftCertificate> expected = Optional.of(giftCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByNameTestNotFound() {
        String name = "gift";
        Optional<GiftCertificate> actual = giftCertificateDao.findGiftCertificateByName(name);
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findByParametersTest() {
        String queryLastPart = " WHERE name_gift_certificate LIKE '%a%' AND description LIKE '%is%' ORDER BY " +
                "name_gift_certificate ASC";
        List<GiftCertificate> actual = giftCertificateDao.findByParameters(queryLastPart);
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(new GiftCertificate(3, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), null));
        expected.add(new GiftCertificate(1, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null));
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        GiftCertificate giftCertificate = new GiftCertificate(1, "Skating",
                "Sales", BigDecimal.valueOf(50), 10, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), null);
        GiftCertificate actual = giftCertificateDao.update(giftCertificate);
        GiftCertificate expected = giftCertificate;
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateWithTagsTestPositive() {
        int id = 2;
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2, "sport"));
        tags.add(new Tag(5, "wonderful gift"));
        tags.add(new Tag(7, "make you fun"));
        GiftCertificate giftCertificate = new GiftCertificate(2, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags);
        Optional<GiftCertificate> actual = giftCertificateDao.findGiftCertificateWithTags(id);
        Optional<GiftCertificate> expected = Optional.of(giftCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateWithTagsTestNotFound() {
        int id = 25;
        Optional<GiftCertificate> actual = giftCertificateDao.findGiftCertificateWithTags(id);
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateWithTagsByTagNameTestPositive() {
        int id = 2;
        String nameTag = "gift";
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(5, "wonderful gift"));
        GiftCertificate giftCertificate = new GiftCertificate(2, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags);
        Optional<GiftCertificate> actual = giftCertificateDao.findGiftCertificateWithTagsByTagName(id, nameTag);
        Optional<GiftCertificate> expected = Optional.of(giftCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateWithTagsByTagNameTestNotFound() {
        int id = 45;
        Optional<GiftCertificate> actual = giftCertificateDao.findGiftCertificateWithTagsByTagName(id, "gift");
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void addTagToGiftCertificateTestPositive() {
        assertDoesNotThrow(() -> giftCertificateDao.addTagToGiftCertificate(1, 4));
    }

    @Test
    void addTagToGiftCertificateTestNegative() {
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.addTagToGiftCertificate(77, 24));
    }

    @Test
    void isGiftCertificateWithTagExistTestPositive() {
        boolean actual = giftCertificateDao.isGiftCertificateWithTagExist(2, 7);
        assertTrue(actual);
    }


    @Test
    void isGiftCertificateWithTagExistTestNegative() {
        boolean actual = giftCertificateDao.isGiftCertificateWithTagExist(1, 4);
        assertFalse(actual);
    }

    @Test
    void findAllWithTagsTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "gift"));
        tags.add(new Tag(2, "sport"));
        tags.add(new Tag(7, "make you fun"));
        expected.add(new GiftCertificate(1, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(2, "sport"));
        tags.add(new Tag(5, "wonderful gift"));
        tags.add(new Tag(7, "make you fun"));
        expected.add(new GiftCertificate(2, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(1, "gift"));
        tags.add(new Tag(4, "riding"));
        tags.add(new Tag(5, "wonderful gift"));
        tags.add(new Tag(6, "relax"));
        expected.add(new GiftCertificate(3, "Horseback riding", "Horseback riding is the activity of " +
                "riding a horse, especially for enjoyment or as a form of exercise.",
                BigDecimal.valueOf(100), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(2, "sport"));
        tags.add(new Tag(3, "jumping"));
        tags.add(new Tag(6, "relax"));
        tags.add(new Tag(7, "make you fun"));
        expected.add(new GiftCertificate(4, "Trampoline jumping",
                "Trampoline jumping can be fun.", BigDecimal.valueOf(20), 30, Timestamp.valueOf("2021-01-12 11:34:18"),
                Timestamp.valueOf("2021-01-12 11:34:18"), tags));
        List<GiftCertificate> actual = giftCertificateDao.findAllWithTags();
        assertEquals(expected, actual);
    }

    @Test
    void deleteFromGiftCertificateTagTestPositive() {
        boolean actual = giftCertificateDao.deleteFromGiftCertificateTag(3);
        assertTrue(actual);
    }

    @Test
    void deleteFromGiftCertificateTagTestNegative() {
        boolean actual = giftCertificateDao.deleteFromGiftCertificateTag(14);
        assertFalse(actual);
    }
}