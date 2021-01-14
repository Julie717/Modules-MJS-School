package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.IllegalParameterException;
import com.epam.esm.exception.ResourceIsAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.*;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.querybuilder.GiftCertificateSearchBuilder;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.validator.GiftCertificateSearchParameterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateConverterImpl giftCertificateConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagService tagService,
                                      GiftCertificateConverterImpl giftCertificateConverter) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.giftCertificateConverter = giftCertificateConverter;
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public List<GiftCertificateDto> findAllWithTags() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAllWithTags();
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public GiftCertificateDto findById(int idGiftCertificate) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(idGiftCertificate)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND, idGiftCertificate));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        boolean isExist = giftCertificateDao.findGiftCertificateByName(giftCertificateDto.getNameGiftCertificate()).isPresent();
        if (isExist) {
            throw new ResourceIsAlreadyExistException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                    giftCertificateDto.getNameGiftCertificate());
        }
        GiftCertificate giftCertificate = giftCertificateConverter.convertFrom(giftCertificateDto);
        addCreateAndUpdateDate(giftCertificate);
        return giftCertificateConverter.convertTo(giftCertificateDao.add(giftCertificate));
    }

    @Override
    public void deleteById(int idGiftCertificate) {
        giftCertificateDao.findById(idGiftCertificate).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND, idGiftCertificate));
        giftCertificateDao.deleteById(idGiftCertificate);
    }

    private void addCreateAndUpdateDate(GiftCertificate giftCertificate) {
        Timestamp dateTimeNow = Timestamp.valueOf(LocalDateTime.now());
        giftCertificate.setCreateDate(dateTimeNow);
        giftCertificate.setLastUpdateDate(dateTimeNow);
    }

    @Override
    public List<GiftCertificateDto> findByParameters(Map<String, String> parameters) {
        if (!GiftCertificateSearchParameterValidator.isParametersValid(parameters)) {
            throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
        }
        String request = GiftCertificateSearchBuilder.buildQuery(parameters);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByParameters(request);
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND_BY_PARAMS);
        }
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(giftCertificateDto.getIdGiftCertificate())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND,
                        giftCertificateDto.getIdGiftCertificate()));
        if (giftCertificateDto.getNameGiftCertificate() != null && !giftCertificateDto.getNameGiftCertificate().isEmpty()) {
            boolean isExist = giftCertificateDao.findGiftCertificateByName(giftCertificateDto.getNameGiftCertificate()).isPresent();
            if (isExist) {
                throw new ResourceIsAlreadyExistException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                        giftCertificateDto.getNameGiftCertificate());
            }
        }
        GiftCertificate newGiftCertificate = mergeCurrentAndNewGiftCertificate(currentGiftCertificate, giftCertificateDto);
        return giftCertificateConverter.convertTo(giftCertificateDao.update(newGiftCertificate));
    }

    @Override
    public GiftCertificateDto findGiftCertificateWithTags(int idGiftCertificate) {
        GiftCertificate giftCertificate = giftCertificateDao.findGiftCertificateWithTags(idGiftCertificate)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND,
                        idGiftCertificate));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    public GiftCertificateDto findGiftCertificateWithTagsByTagName(int idGiftCertificate, String nameTag) {
        GiftCertificate giftCertificate = giftCertificateDao.findGiftCertificateWithTagsByTagName(idGiftCertificate, nameTag)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_WITH_TAG_NOT_FOUND,
                        idGiftCertificate, nameTag));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    private GiftCertificate mergeCurrentAndNewGiftCertificate(GiftCertificate currentGiftCertificate,
                                                              GiftCertificateDto newGiftCertificate) {
        if (newGiftCertificate.getNameGiftCertificate() != null && !newGiftCertificate.getNameGiftCertificate().isEmpty()) {
            currentGiftCertificate.setNameGiftCertificate(newGiftCertificate.getNameGiftCertificate());
        }
        if (newGiftCertificate.getDescription() != null && !newGiftCertificate.getDescription().isEmpty()) {
            currentGiftCertificate.setDescription(newGiftCertificate.getDescription());
        }
        if (newGiftCertificate.getPrice() != null) {
            currentGiftCertificate.setPrice(newGiftCertificate.getPrice());
        }
        if (newGiftCertificate.getDuration() != null) {
            currentGiftCertificate.setDuration(newGiftCertificate.getDuration());
        }
        currentGiftCertificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        return currentGiftCertificate;
    }

    @Override
    @Transactional
    public GiftCertificateDto addTagsToGiftCertificate(int idGiftCertificate, List<TagDto> tagsDto) {
        giftCertificateDao.findGiftCertificateWithTags(idGiftCertificate)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND,
                        idGiftCertificate));
        List<TagDto> tagsAlreadyExist = tagService.findByRangeNames(tagsDto);
        List<TagDto> tagsNotExist = tagsDto;
        if (!tagsAlreadyExist.isEmpty()) {
            tagsNotExist = tagsDto.stream()
                    .filter(tag -> tagsAlreadyExist.stream()
                            .noneMatch(t -> t.getNameTag().equals(tag.getNameTag())))
                    .collect(Collectors.toList());
            for (TagDto tag : tagsAlreadyExist) {
                Boolean isExist = giftCertificateDao.isGiftCertificateWithTagExist(idGiftCertificate, tag.getIdTag());
                if (isExist == null || !isExist) {
                    giftCertificateDao.addTagToGiftCertificate(idGiftCertificate, tag.getIdTag());
                }
            }
        }
        if (!tagsNotExist.isEmpty()) {
            List<TagDto> newTags = new ArrayList<>();
            TagDto tagDto;
            for (TagDto tag : tagsNotExist) {
                tagDto = tagService.add(tag);
                newTags.add(tagDto);
            }
            newTags.forEach(tag -> giftCertificateDao.addTagToGiftCertificate(idGiftCertificate, tag.getIdTag()));
        }
        return findGiftCertificateWithTags(idGiftCertificate);
    }
}