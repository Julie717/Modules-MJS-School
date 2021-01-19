package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TagServiceTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDao tagDao;
    @Spy
    private final TagConverterImpl tagConverter = new TagConverterImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllTestEmptyList() {
        List<Tag> tags = new ArrayList<>();
        Mockito.when(tagDao.findAll()).thenReturn(tags);
        List<TagDto> tagsDto = new ArrayList<>();
        List<TagDto> actual = tagService.findAll();
        verify(tagConverter).convertTo(tags);
        assertEquals(tagsDto, actual);
    }

    @Test
    void findAllTestPositive() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "gift"));
        tags.add(new Tag(2, "sport"));
        tags.add(new Tag(3, "jumping"));
        Mockito.when(tagDao.findAll()).thenReturn(tags);
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(1, "gift"));
        tagsDto.add(new TagDto(2, "sport"));
        tagsDto.add(new TagDto(3, "jumping"));
        List<TagDto> actual = tagService.findAll();
        verify(tagConverter, times(1)).convertTo(tags);
        assertEquals(tagsDto, actual);
    }

    @Test
    void findByIdTestPositive() {
        int id = 2;
        Optional<Tag> tag = Optional.of(new Tag(2, "sport"));
        Mockito.when(tagDao.findById(id)).thenReturn(tag);
        TagDto expected = new TagDto(2, "sport");
        TagDto actual = tagService.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        int id = 25;
        Mockito.when(tagDao.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(id));
    }

    @Test
    void addTestPositive() {
        Tag tag = new Tag(null, "jumping");
        Mockito.when(tagDao.findTagByName(tag.getNameTag())).thenReturn(Optional.empty());
        Tag tagWithId = new Tag(3, "jumping");
        Mockito.when(tagDao.add(tag)).thenReturn(tagWithId);
        TagDto tagDto = new TagDto(null, "jumping");
        TagDto actual = tagService.add(tagDto);
        TagDto expected = new TagDto(3, "jumping");
        assertEquals(expected, actual);
    }

    @Test
    void addTestNegative() {
        Mockito.when(tagDao.findTagByName(anyString())).thenReturn(Optional.of(new Tag()));
        TagDto tagDto = new TagDto();
        tagDto.setNameTag("Skating");
        assertThrows(ResourceAlreadyExistsException.class, () -> tagService.add(tagDto));
    }

    @Test
    void deleteByIdTestPositive() {
        int id = 8;
        Mockito.when(tagDao.findById(id)).thenReturn(Optional.of(new Tag()));
        Mockito.when(tagDao.deleteFromGiftCertificateTag(id)).thenReturn(true);
        Mockito.when(tagDao.deleteById(id)).thenReturn(true);
        assertDoesNotThrow(() -> tagService.deleteById(id));
    }

    @Test
    void deleteByIdTestNegative() {
        int id = 8;
        Mockito.when(tagDao.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tagService.deleteById(id));
    }

    @Test
    void findByRangeNamesTestPositive() {
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(null, "gift"));
        tagsDto.add(new TagDto(null, "sport"));
        tagsDto.add(new TagDto(null, "jumping"));
        String tagsNameForQuery = "('gift', 'sport', 'jumping')";
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "gift"));
        tags.add(new Tag(8, "jumping"));
        Mockito.when(tagDao.findTagByNameInRange(tagsNameForQuery)).thenReturn(tags);
        List<TagDto> actual = tagService.findByRangeNames(tagsDto);
        List<TagDto> expected = new ArrayList<>();
        expected.add(new TagDto(1, "gift"));
        expected.add(new TagDto(8, "jumping"));
        assertEquals(expected, actual);
    }

    @Test
    void findByRangeNamesTestEmpty() {
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(null, "gift"));
        tagsDto.add(new TagDto(null, "sport"));
        String tagsNameForQuery = "('gift', 'sport')";
        List<Tag> tags = new ArrayList<>();
        Mockito.when(tagDao.findTagByNameInRange(tagsNameForQuery)).thenReturn(tags);
        List<TagDto> actual = tagService.findByRangeNames(tagsDto);
        List<TagDto> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }
}