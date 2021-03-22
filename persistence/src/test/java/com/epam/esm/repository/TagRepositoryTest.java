package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfigTest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = PersistenceConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void findAllTest() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1L, "gift"));
        expected.add(new Tag(2L, "sport"));
        expected.add(new Tag(3L, "jumping"));
        expected.add(new Tag(4L, "riding"));
        expected.add(new Tag(5L, "wonderful gift"));
        expected.add(new Tag(6L, "relax"));
        expected.add(new Tag(7L, "make you fun"));
        Pageable pageable = PageRequest.of(0, 10);

        List<Tag> actual = tagRepository.findAll(pageable).getContent();

        assertEquals(expected, actual);
    }

    @Test
    void findAllSecondPageTest() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(4L, "riding"));
        expected.add(new Tag(5L, "wonderful gift"));
        expected.add(new Tag(6L, "relax"));
        Pageable pageable = PageRequest.of(1, 3);

        List<Tag> actual = tagRepository.findAll(pageable).getContent();

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestPositive() {
        Long id = 2L;

        Optional<Tag> actual = tagRepository.findById(id);

        Tag tag = new Tag(2L, "sport");
        Optional<Tag> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNotFound() {
        Long id = 25L;

        Optional<Tag> actual = tagRepository.findById(id);

        Optional<Tag> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameTestPositive() {
        String name = "gift";

        Optional<Tag> actual = tagRepository.findByName(name);

        Tag tag = new Tag(1L, "gift");
        Optional<Tag> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameTestNotFound() {
        String name = "skating";

        Optional<Tag> actual = tagRepository.findByName(name);

        Optional<Tag> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameInRangePositive() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1L, "gift"));
        expected.add(new Tag(3L, "jumping"));
        expected.add(new Tag(6L, "relax"));
        List<String> tagNames = new ArrayList<>();
        tagNames.add("gift");
        tagNames.add("skating");
        tagNames.add("jumping");
        tagNames.add("relax");
        tagNames.add("fitness");

        List<Tag> actual = tagRepository.findByNameIn(tagNames);

        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameInRangeNotFound() {
        List<Tag> expected = new ArrayList<>();
        List<String> tagNames = new ArrayList<>();
        tagNames.add("skating");
        tagNames.add("fitness");

        List<Tag> actual = tagRepository.findByNameIn(tagNames);

        assertEquals(expected, actual);
    }

    @Test
    void findTopTagTest() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1L, "gift"));
        Pageable pageable = PageRequest.of(0, 10);

        List<Tag> actual = tagRepository.findTopTag(pageable);

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void addTest() {
        String name = "funny";
        Tag tag = new Tag();
        tag.setName(name);

        Tag actual = tagRepository.save(tag);

        Tag expected = new Tag();
        expected.setId(8L);
        expected.setName(name);
        assertEquals(expected, actual);
    }
}