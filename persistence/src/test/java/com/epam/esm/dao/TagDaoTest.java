package com.epam.esm.dao;

import com.epam.esm.dao.extractor.ListTagResultSetExtractor;
import com.epam.esm.dao.extractor.TagResultSetExtractor;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagDaoTest {
    private EmbeddedDatabase dataSource;
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:script.sql").addScript("classpath:script-inserts.sql").build();
        tagDao = new TagDaoImpl(new JdbcTemplate(dataSource), new TagResultSetExtractor(),
                new ListTagResultSetExtractor());
    }

    @AfterEach
    void tearDown() {
        dataSource.shutdown();
        tagDao = null;
    }

    @Test
    void findTagByNameTestPositive() {
        String name = "gift";

        Optional<Tag> actual = tagDao.findTagByName(name);

        Tag tag = new Tag(1, "gift");
        Optional<Tag> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameTestNotFound() {
        String name = "skating";

        Optional<Tag> actual = tagDao.findTagByName(name);

        Optional<Tag> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void addTest() {
        String name = "funny";
        Tag tag = new Tag();
        tag.setNameTag(name);

        Tag actual = tagDao.add(tag);

        Tag expected = new Tag();
        expected.setIdTag(8);
        expected.setNameTag(name);
        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdTestPositive() {
        int id = 1;

        boolean actual = tagDao.deleteById(id);

        assertTrue(actual);
    }

    @Test
    void deleteByIdTestNegative() {
        int id = 25;

        boolean actual = tagDao.deleteById(id);

        assertFalse(actual);
    }

    @Test
    void findByIdTestPositive() {
        int id = 2;

        Optional<Tag> actual = tagDao.findById(id);

        Tag tag = new Tag(2, "sport");
        Optional<Tag> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNotFound() {
        int id = 25;

        Optional<Tag> actual = tagDao.findById(id);

        Optional<Tag> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findAllTest() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1, "gift"));
        expected.add(new Tag(2, "sport"));
        expected.add(new Tag(3, "jumping"));
        expected.add(new Tag(4, "riding"));
        expected.add(new Tag(5, "wonderful gift"));
        expected.add(new Tag(6, "relax"));
        expected.add(new Tag(7, "make you fun"));

        List<Tag> actual = tagDao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameInRangePositive() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1, "gift"));
        expected.add(new Tag(3, "jumping"));
        expected.add(new Tag(6, "relax"));
        String tagRangeNames = "('gift', 'skating', 'jumping', 'relax', 'fitness')";

        List<Tag> actual = tagDao.findTagByNameInRange(tagRangeNames);

        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameInRangeNotFound() {
        List<Tag> expected = new ArrayList<>();
        String tagRangeNames = "('skating', 'fitness')";

        List<Tag> actual = tagDao.findTagByNameInRange(tagRangeNames);

        assertEquals(expected, actual);
    }

    @Test
    void deleteFromGiftCertificateTagPositive() {
        int id = 4;

        boolean actual = tagDao.deleteFromGiftCertificateTag(id);

        assertTrue(actual);
    }

    @Test
    void deleteFromGiftCertificateTagNegative() {
        int id = 44;

        boolean actual = tagDao.deleteFromGiftCertificateTag(id);

        assertFalse(actual);
    }
}