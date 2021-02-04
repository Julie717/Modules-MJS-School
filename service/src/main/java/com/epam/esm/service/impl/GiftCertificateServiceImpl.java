package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.exception.IllegalParameterException;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.querybuilder.GiftCertificateSearchBuilder;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
import com.epam.esm.validator.GiftCertificateSearchParameterValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final PurchaseDao purchaseDao;
    private final TagService tagService;
    private final GiftCertificateConverterImpl giftCertificateConverter;
    private final TagConverterImpl tagConverter;

    @Override
    public List<GiftCertificateDto> findAll(Pagination pagination) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll(pagination.getLimit(), pagination.getOffset());
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        id, GiftCertificate.class.getSimpleName()));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findByParameters(Map<String, String> parameters, Pagination pagination) {
        if (!GiftCertificateSearchParameterValidator.isParametersValid(parameters)) {
            throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
        }
        String request = GiftCertificateSearchBuilder.buildQuery(parameters);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByParameters(request, pagination.getLimit(),
                pagination.getOffset());
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND_BY_PARAMS);
        }
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public List<GiftCertificateDto> findByTagId(Long idTag, Pagination pagination) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByTagId(idTag,
                pagination.getLimit(), pagination.getOffset());
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATES_WITH_TAG_NOT_FOUND, idTag);
        }
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public GiftCertificateDto findGiftCertificateByTagId(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = giftCertificateDao.findByTagIdInGiftCertificate(idGiftCertificate, idTag)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATES_WITH_TAG_NOT_FOUND, idTag));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificate> currentGiftCertificate = giftCertificateDao.findByName(giftCertificateDto.getName());
        if (currentGiftCertificate.isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                    giftCertificateDto.getName());
        }
        if (giftCertificateDto.getTags() != null && !giftCertificateDto.getTags().isEmpty()) {
            List<TagDto> tags = tagService.findByRangeNames(giftCertificateDto.getTags());
            List<TagDto> newTags = receiveNewTags(tagConverter.convertFrom(tags), giftCertificateDto.getTags());
            if (!newTags.isEmpty()) {
                throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
            }
            giftCertificateDto.setTags(tags);
        }
        GiftCertificate giftCertificate = giftCertificateConverter.convertFrom(giftCertificateDto);
        return giftCertificateConverter.convertTo(giftCertificateDao.add(giftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto addTagsToGiftCertificate(Long id, List<TagDto> tagsDto) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        id, GiftCertificate.class.getSimpleName()));
        List<TagDto> newTags = receiveNewTags(giftCertificate.getTags(), tagsDto);
        if (newTags.isEmpty()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.TAG_ALREADY_EXISTS_IN_GIFT_CERTIFICATE,
                    giftCertificate.getName());
        }
        List<Tag> tags = giftCertificate.getTags();
        addNewTagsToGiftCertificate(giftCertificate, tagsDto);
        tagsDto.stream().filter(t -> tags.stream()
                .anyMatch(tag -> !tag.getName().equals(t.getName()))).forEach(
                t -> tags.add(tagConverter.convertFrom(t))
        );
        return giftCertificateConverter.convertTo(giftCertificateDao.update(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        GiftCertificate.class.getSimpleName()));
        List<Purchase> purchases = purchaseDao.findByIdGiftCertificate(id);
        if (purchases != null && !purchases.isEmpty()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATES_USED, giftCertificate.getName());
        }
        giftCertificateDao.delete(giftCertificate);
    }

    @Override
    @Transactional
    public void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(idGiftCertificate).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, idGiftCertificate,
                        GiftCertificate.class.getSimpleName()));
        boolean isExist = false;
        if (giftCertificate.getTags() != null || !giftCertificate.getTags().isEmpty()) {
            isExist = giftCertificate.getTags().stream().anyMatch(t -> t.getId().equals(idTag));
        }
        if (!isExist) {
            throw new ResourceNotFoundException(ErrorMessageReader.TAG_IN_GIFT_CERTIFICATE_NOT_FOUND, idTag);
        }
        giftCertificateDao.deleteTagFromGiftCertificate(idGiftCertificate, idTag);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        giftCertificateDto.getId(), GiftCertificate.class.getSimpleName()));
        Optional<GiftCertificate> giftCertificateWithNewNameInDB = giftCertificateDao
                .findByName(giftCertificateDto.getName());
        if (giftCertificateWithNewNameInDB.isPresent()) {
            if (!giftCertificateWithNewNameInDB.get().getId().equals(giftCertificateDto.getId())) {
                throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                        giftCertificateDto.getName());
            }
        }
        boolean isCorrectTags = checkTags(giftCertificateDto.getTags());
        if (!isCorrectTags) {
            throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
        }
        GiftCertificate newGiftCertificate = giftCertificateConverter.convertFrom(giftCertificateDto);
        newGiftCertificate.setCreateDate(currentGiftCertificate.getCreateDate());
        newGiftCertificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        return giftCertificateConverter.convertTo(giftCertificateDao.update(newGiftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto patchGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        giftCertificateDto.getId(), GiftCertificate.class.getSimpleName()));
        if (giftCertificateDto.getName() != null && !giftCertificateDto.getName().isEmpty()) {
            Optional<GiftCertificate> giftCertificateWithNewNameInDB = giftCertificateDao
                    .findByName(giftCertificateDto.getName());
            if (giftCertificateWithNewNameInDB.isPresent()) {
                if (!giftCertificateWithNewNameInDB.get().getId().equals(giftCertificateDto.getId())) {
                    throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                            giftCertificateDto.getName());
                }
            }
        }
        GiftCertificate newGiftCertificate = mergeCurrentAndNewGiftCertificate(currentGiftCertificate, giftCertificateDto);
        return giftCertificateConverter.convertTo(giftCertificateDao.update(newGiftCertificate));
    }

    private GiftCertificate mergeCurrentAndNewGiftCertificate(GiftCertificate currentGiftCertificate,
                                                              GiftCertificateDto newGiftCertificate) {
        if (newGiftCertificate.getName() != null && !newGiftCertificate.getName().isEmpty()) {
            currentGiftCertificate.setName(newGiftCertificate.getName());
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
        if (newGiftCertificate.getTags() != null) {
            addNewTagsToGiftCertificate(currentGiftCertificate, newGiftCertificate.getTags());
        }
        return currentGiftCertificate;
    }

    private List<TagDto> receiveNewTags(List<Tag> currentTags, List<TagDto> tagsDto) {
        List<TagDto> newTags = tagsDto;
        if (!currentTags.isEmpty()) {
            newTags = tagsDto.stream()
                    .filter(t -> currentTags.stream()
                            .noneMatch(c -> c.getName().equals(t.getName())))
                    .collect(Collectors.toList());
        }
        return newTags;
    }

    private void addNewTagsToGiftCertificate(GiftCertificate currentGiftCertificate, List<TagDto> tagsDto) {
        if (tagsDto != null) {
            List<Tag> tags = new ArrayList<>();
            List<TagDto> existTags = tagService.findByRangeNames(tagsDto);
            for (TagDto tag : tagsDto) {
                existTags.stream().filter(t -> t.getName().equals(tag.getName()))
                        .forEach(t -> tags.add(tagConverter.convertFrom(t)));
            }
            tagsDto.stream().filter(t -> existTags.stream()
                    .noneMatch(tag -> tag.getName().equals(t.getName())))
                    .forEach(t -> tags.add(tagConverter.convertFrom(t)));
            currentGiftCertificate.setTags(tags);
        }
    }

    private boolean checkTags(List<TagDto> newTags) {
        List<TagDto> tags = tagService.findByRangeNames(newTags);
        boolean isCorrect = newTags.stream().allMatch(t ->
                tags.stream().anyMatch(tag -> tag.getId().equals(t.getId())));
        if (isCorrect) {
            isCorrect = newTags.stream().allMatch(t ->
                    tags.stream()
                            .noneMatch(tag -> tag.getName().equals(t.getName()) && !tag.getId().equals(t.getId())));
        }
        return isCorrect;
    }
}