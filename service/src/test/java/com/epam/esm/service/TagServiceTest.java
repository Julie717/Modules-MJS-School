package com.epam.esm.service;

import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Spy
    private final TagConverterImpl tagConverter = new TagConverterImpl();

    @Test
    void findAllTestPositive() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(3L, "jumping"));
        Page<Tag> tagPage = new PageImpl<>(tags);
        Pageable pageable = PageRequest.of(3, 10);
        Mockito.when(tagRepository.findAll(pageable)).thenReturn(tagPage);
        Pagination pagination = new Pagination(3, 10);
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(1L, "gift"));
        tagsDto.add(new TagDto(2L, "sport"));
        tagsDto.add(new TagDto(3L, "jumping"));

        List<TagDto> actual = tagService.findAll(pagination);

        verify(tagConverter, times(1)).convertTo(tags);
        assertEquals(tagsDto, actual);
    }

    @Test
    void findAllTestNegative() {
        Page<Tag> tagPage = new PageImpl<>(new ArrayList<>());
        Mockito.when(tagRepository.findAll(any(Pageable.class))).thenReturn(tagPage);
        Pagination pagination = new Pagination(10, 2);

        assertThrows(ResourceNotFoundException.class, () -> tagService.findAll(pagination));
    }

    @Test
    void findByIdTestPositive() {
        Long id = 2L;
        Optional<Tag> tag = Optional.of(new Tag(2L, "sport"));
        Mockito.when(tagRepository.findById(id)).thenReturn(tag);
        TagDto expected = new TagDto(2L, "sport");

        TagDto actual = tagService.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(tagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(id));
    }

    @Test
    void findByRangeNamesTestPositive() {
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(null, "gift"));
        tagsDto.add(new TagDto(null, "sport"));
        tagsDto.add(new TagDto(null, "jumping"));
        List<String> tagNames = new ArrayList<>();
        tagNames.add("gift");
        tagNames.add("sport");
        tagNames.add("jumping");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(8L, "jumping"));
        Mockito.when(tagRepository.findByNameIn(tagNames)).thenReturn(tags);

        List<TagDto> actual = tagService.findByRangeNames(tagsDto);

        List<TagDto> expected = new ArrayList<>();
        expected.add(new TagDto(1L, "gift"));
        expected.add(new TagDto(8L, "jumping"));
        assertEquals(expected, actual);
    }

    @Test
    void findByRangeNamesTestEmpty() {
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(null, "gift"));
        tagsDto.add(new TagDto(null, "sport"));
        List<String> tagNames = new ArrayList<>();
        tagNames.add("gift");
        tagNames.add("sport");
        List<Tag> tags = new ArrayList<>();
        Mockito.when(tagRepository.findByNameIn(tagNames)).thenReturn(tags);

        List<TagDto> actual = tagService.findByRangeNames(tagsDto);

        List<TagDto> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void findTopTagTest() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(8L, "jumping"));
        int page = 3;
        int perPage = 2;
        Pagination pagination = new Pagination(page, perPage);
        Pageable pageable = PageRequest.of(page, perPage);
        Mockito.when(tagRepository.findTopTag(pageable)).thenReturn(tags);

        List<TagDto> actual = tagService.findTopTag(pagination);

        List<TagDto> expected = new ArrayList<>();
        expected.add(new TagDto(1L, "gift"));
        expected.add(new TagDto(8L, "jumping"));
        assertEquals(expected, actual);
    }

    @Test
    void addTestPositive() {
        Tag tag = new Tag(null, "jumping");
        Mockito.when(tagRepository.findByName(tag.getName())).thenReturn(Optional.empty());
        Tag tagWithId = new Tag(3L, "jumping");
        Mockito.when(tagRepository.save(tag)).thenReturn(tagWithId);
        TagDto tagDto = new TagDto(null, "jumping");

        TagDto actual = tagService.add(tagDto);

        TagDto expected = new TagDto(3L, "jumping");
        assertEquals(expected, actual);
    }

    @Test
    void addTestNegative() {
        Mockito.when(tagRepository.findByName(anyString())).thenReturn(Optional.of(new Tag()));
        TagDto tagDto = new TagDto();
        tagDto.setName("Skating");

        assertThrows(ResourceAlreadyExistsException.class, () -> tagService.add(tagDto));
    }
}