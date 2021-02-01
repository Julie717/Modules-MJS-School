package com.epam.esm.dao;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TagDaoTest {
   /* private EmbeddedDatabase dataSource;
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
        tag.setName(name);

        Tag actual = tagDao.add(tag);

        Tag expected = new Tag();
        expected.setId(8);
        expected.setName(name);
        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdTestPositive() {
        Long id = 1;

        boolean actual = tagDao.deleteById(id);

        assertTrue(actual);
    }

    @Test
    void deleteByIdTestNegative() {
        Long id = 25;

        boolean actual = tagDao.deleteById(id);

        assertFalse(actual);
    }

    @Test
    void findByIdTestPositive() {
        Long id = 2;

        Optional<Tag> actual = tagDao.findById(id);

        Tag tag = new Tag(2, "sport");
        Optional<Tag> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNotFound() {
        Long id = 25;

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
        Long id = 4;

        boolean actual = tagDao.deleteFromGiftCertificateTag(id);

        assertTrue(actual);
    }

    @Test
    void deleteFromGiftCertificateTagNegative() {
        Long id = 44;

        boolean actual = tagDao.deleteFromGiftCertificateTag(id);

        assertFalse(actual);
    }*/
}