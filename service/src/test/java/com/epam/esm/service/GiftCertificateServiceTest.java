package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.IllegalParameterException;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
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
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyList;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagService tagService;

    @Spy
    private final TagConverterImpl tagConverter = new TagConverterImpl();

    @Spy
    private final GiftCertificateConverterImpl giftCertificateConverter = new GiftCertificateConverterImpl(tagConverter);

    @Test
    void findAllTestEmptyList() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        Mockito.when(giftCertificateDao.findAll(anyInt(), anyInt())).thenReturn(giftCertificates);
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        Pagination pagination = new Pagination(10, 2);

        List<GiftCertificateDto> actual = giftCertificateService.findAll(pagination);

        verify(giftCertificateConverter).convertTo(giftCertificates);
        assertEquals(giftCertificatesDto, actual);
    }

    @Test
    void findAllTestPositive() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null));
        Mockito.when(giftCertificateDao.findAll(anyInt(), anyInt())).thenReturn(giftCertificates);
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        giftCertificatesDto.add(new GiftCertificateDto(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null));
        Pagination pagination = new Pagination(1, 0);

        List<GiftCertificateDto> actual = giftCertificateService.findAll(pagination);

        verify(giftCertificateConverter, times(1)).convertTo(giftCertificates);
        assertEquals(giftCertificatesDto, actual);
    }

    @Test
    void findAllWithTagsTest() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(7L, "make you fun"));
        giftCertificates.add(new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags));
        tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(5L, "wonderful gift"));
        tags.add(new Tag(7L, "make you fun"));
        giftCertificates.add(new GiftCertificate(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags));
        Mockito.when(giftCertificateDao.findAll(anyInt(), anyInt())).thenReturn(giftCertificates);
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(1L, "gift"));
        tagsDto.add(new TagDto(2L, "sport"));
        tagsDto.add(new TagDto(7L, "make you fun"));
        giftCertificatesDto.add(new GiftCertificateDto(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tagsDto));
        tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(2L, "sport"));
        tagsDto.add(new TagDto(5L, "wonderful gift"));
        tagsDto.add(new TagDto(7L, "make you fun"));
        giftCertificatesDto.add(new GiftCertificateDto(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tagsDto));
        Pagination pagination = new Pagination(1, 0);

        List<GiftCertificateDto> actual = giftCertificateService.findAll(pagination);

        assertEquals(giftCertificatesDto, actual);
    }

    @Test
    void findByIdTestPositive() {
        Long id = 2L;
        Optional<GiftCertificate> giftCertificate = Optional.of(new GiftCertificate(2L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null));
        Mockito.when(giftCertificateDao.findById(id)).thenReturn(giftCertificate);
        GiftCertificateDto expected = new GiftCertificateDto(2L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);

        GiftCertificateDto actual = giftCertificateService.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(giftCertificateDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findById(id));
    }

    @Test
    void findByParametersTestPositive() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        parameters.put("description", "beautiful");
        parameters.put("sort", "nameGiftCertificate,-createDate");
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Skating",
                "Ice skating is a sport in which people slide " +
                        "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                        "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        giftCertificates.add(giftCertificate);
        Mockito.when(giftCertificateDao.findByParameters(anyString(), anyInt(), anyInt())).thenReturn(giftCertificates);
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(2L, "Skating",
                "Ice skating is a sport in which people slide " +
                        "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                        "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        giftCertificatesDto.add(giftCertificateDto);
        Pagination pagination = new Pagination(2, 0);

        List<GiftCertificateDto> actual = giftCertificateService.findByParameters(parameters, pagination);

        assertEquals(giftCertificatesDto, actual);
    }

    @Test
    void findByParametersTestIncorrectInputParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("color", "gift");
        parameters.put("description", "beautiful");
        Pagination pagination = new Pagination(2, 0);

        assertThrows(IllegalParameterException.class, () -> giftCertificateService.findByParameters(parameters, pagination));
    }

    @Test
    void findByParametersTestNotFound() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        Mockito.when(giftCertificateDao.findByParameters(anyString(), anyInt(), anyInt())).thenReturn(giftCertificates);
        Pagination pagination = new Pagination(2, 0);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findByParameters(parameters, pagination));
    }

    @Test
    void findByTagIdTestPositive() {
        Long idTag = 2L;
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Skating",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "Skating"));
        giftCertificate.setTags(tags);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        Mockito.when(giftCertificateDao.findByTagId(anyLong(), anyInt(), anyInt()))
                .thenReturn(giftCertificates);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(2L, "Skating",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(2L, "Skating"));
        giftCertificateDto.setTags(tagsDto);
        List<GiftCertificateDto> expected = new ArrayList<>();
        expected.add(giftCertificateDto);
        Pagination pagination = new Pagination(2, 3);

        List<GiftCertificateDto> actual = giftCertificateService.findByTagId(idTag, pagination);

        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdTestNegative() {
        Long idTag = 2L;
        Mockito.when(giftCertificateDao.findByTagId(anyLong(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        Pagination pagination = new Pagination(2, 3);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findByTagId(idTag, pagination));
    }

    @Test
    void findGiftCertificateByTagIdTestPositive() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "Skating"));
        Optional<GiftCertificate> giftCertificate = Optional.of(
                new GiftCertificate(2L, "Skating",
                        "It's wonderful", BigDecimal.valueOf(10),
                        30, Timestamp.valueOf("2021-01-10 12:15:37"),
                        Timestamp.valueOf("2021-01-10 12:15:37"), tags));
        Mockito.when(giftCertificateDao.findByTagIdInGiftCertificate(anyLong(), anyLong()))
                .thenReturn(giftCertificate);
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(2L, "Skating"));
        GiftCertificateDto expected = new GiftCertificateDto(2L, "Skating",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tagsDto);

        GiftCertificateDto actual = giftCertificateService.findGiftCertificateByTagId(2L, 2L);

        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByTagIdTestNegative() {
        Mockito.when(giftCertificateDao.findByTagIdInGiftCertificate(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateByTagId(21L, 5L));
    }


    @Test
    void addTestPositive() {
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Skating",
                "Ice skating is a sport in which people slide " +
                        "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                        "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        Mockito.when(giftCertificateDao.findByName(giftCertificate.getName())).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(giftCertificate);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(2L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);

        GiftCertificateDto actual = giftCertificateService.add(giftCertificateDto);

        actual.setCreateDate(giftCertificateDto.getCreateDate());
        actual.setLastUpdateDate(giftCertificateDto.getLastUpdateDate());
        assertEquals(giftCertificateDto, actual);
    }

    @Test
    void addTestNegative() {
        Mockito.when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(new GiftCertificate()));
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Skating");

        assertThrows(ResourceAlreadyExistsException.class, () -> giftCertificateService.add(giftCertificateDto));
    }

    @Test
    void addTagsToGiftCertificateTestPositive() {
        Long id = 2L;
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "wonderful gift"));
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Skating",
                "It's wonderful gift", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags);
        Mockito.when(giftCertificateDao.findById(id)).thenReturn(Optional.of(giftCertificate));
        tags = new ArrayList<>();
        tags.add(new Tag(2L, "wonderful gift"));
        tags.add(new Tag(5L, "skating"));
        giftCertificate = new GiftCertificate(2L, "Skating",
                "It's wonderful gift", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags);
        Mockito.when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate);
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(2L, "wonderful gift"));
        tagsDto.add(new TagDto(5L, "skating"));
        List<TagDto> tagsAlreadyExist = new ArrayList<>();
        tagsAlreadyExist.add(new TagDto(2L, "wonderful gift"));
        Mockito.when(tagService.findByRangeNames(tagsDto)).thenReturn(tagsAlreadyExist);
        GiftCertificateDto expected = new GiftCertificateDto(2L, "Skating",
                "It's wonderful gift", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tagsDto);

        GiftCertificateDto actual = giftCertificateService.addTagsToGiftCertificate(id, tagsDto);

        assertEquals(expected, actual);
    }

    @Test
    void addTagsToGiftCertificateTestNegative() {
        Long id = 2L;
        Mockito.when(giftCertificateDao.findById(id)).thenThrow(ResourceNotFoundException.class);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Skating");

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.addTagsToGiftCertificate(id, anyList()));
    }

    @Test
    void addTagsToGiftCertificateTestTagsAlreadyExist() {
        Long id = 2L;
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(2L, "wonderful gift"));
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Skating",
                "It's wonderful gift", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags);
        Mockito.when(giftCertificateDao.findById(id)).thenReturn(Optional.of(giftCertificate));
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(2L, "wonderful gift"));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.addTagsToGiftCertificate(id, tagsDto));
    }

    @Test
    void updateGiftCertificateTestPositive() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(2L, "Skating",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Fitness",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        Mockito.when(giftCertificateDao.findById(giftCertificateDto.getId()))
                .thenReturn(Optional.of(giftCertificate));
        Mockito.when(giftCertificateDao.findByName(giftCertificateDto.getName()))
                .thenReturn(Optional.empty());
        giftCertificate.setName(giftCertificateDto.getName());
        Mockito.when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate);

        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(giftCertificateDto);

        Timestamp lastUpdateDate = Timestamp.valueOf(LocalDateTime.now());
        actual.setLastUpdateDate(lastUpdateDate);
        giftCertificateDto.setLastUpdateDate(lastUpdateDate);
        assertEquals(giftCertificateDto, actual);
    }

    @Test
    void updateGiftCertificateTestNotFound() {
        Mockito.when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(15L);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    void updateGiftCertificateTestAlreadyExist() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(2L, "Skating",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Fitness",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        Mockito.when(giftCertificateDao.findById(giftCertificateDto.getId()))
                .thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificateFound = new GiftCertificate(14L, "Skating",
                "It's wonderful", BigDecimal.valueOf(9),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        Mockito.when(giftCertificateDao.findByName(giftCertificateDto.getName()))
                .thenReturn(Optional.of(giftCertificateFound));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    void patchGiftCertificateTestPositive() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(2L);
        giftCertificateDto.setName("Skating");
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Fitness",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        Mockito.when(giftCertificateDao.findById(giftCertificateDto.getId()))
                .thenReturn(Optional.of(giftCertificate));
        Mockito.when(giftCertificateDao.findByName(giftCertificateDto.getName()))
                .thenReturn(Optional.empty());
        giftCertificate.setName(giftCertificateDto.getName());
        Mockito.when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate);

        GiftCertificateDto actual = giftCertificateService.patchGiftCertificate(giftCertificateDto);

        Timestamp lastUpdateDate = Timestamp.valueOf(LocalDateTime.now());
        actual.setLastUpdateDate(lastUpdateDate);
        GiftCertificateDto expected = new GiftCertificateDto(2L, "Skating",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                lastUpdateDate, null);
        assertEquals(expected, actual);
    }

    @Test
    void patchGiftCertificateTestNotFound() {
        Mockito.when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(15L);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.patchGiftCertificate(giftCertificateDto));
    }

    @Test
    void patchGiftCertificateTestAlreadyExist() {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(2L);
        giftCertificateDto.setName("Skating");
        GiftCertificate giftCertificate = new GiftCertificate(2L, "Fitness",
                "It's wonderful", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        Mockito.when(giftCertificateDao.findById(giftCertificateDto.getId()))
                .thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificateFound = new GiftCertificate(14L, "Skating",
                "It's wonderful", BigDecimal.valueOf(9),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        Mockito.when(giftCertificateDao.findByName(giftCertificateDto.getName()))
                .thenReturn(Optional.of(giftCertificateFound));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.patchGiftCertificate(giftCertificateDto));
    }
}