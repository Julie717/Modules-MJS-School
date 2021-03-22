package com.epam.esm.service.impl;

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
import com.epam.esm.querybuilder.QueryBuilder;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.PurchaseRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.PaginationParser;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final PurchaseRepository purchaseRepository;
    private final TagService tagService;
    private final GiftCertificateConverterImpl giftCertificateConverter;
    private final TagConverterImpl tagConverter;

    @Override
    public GiftCertificateDto findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        id, GiftCertificate.class.getSimpleName()));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findByParameters(Map<String, String> parameters) {
        Pagination pagination = PaginationParser.parsePagination(parameters);
        Sort sort = QueryBuilder.buildSort(parameters);
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage(), sort);
        Predicate predicate = QueryBuilder.buildSearch(parameters);
        List<GiftCertificate> giftCertificates;
        if (!ObjectUtils.isEmpty(predicate)) {
            giftCertificates = giftCertificateRepository.findAll(predicate, pageable).getContent();
        } else {
            giftCertificates =giftCertificateRepository.findAll(pageable).getContent();
        }
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATE_NOT_FOUND_BY_PARAMS);
        }
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public List<GiftCertificateDto> findByTagId(Long idTag, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage());
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findByIdTag(idTag, pageable);
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATES_WITH_TAG_NOT_FOUND, idTag);
        }
        return giftCertificateConverter.convertTo(giftCertificates);
    }

    @Override
    public GiftCertificateDto findGiftCertificateByTagId(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = giftCertificateRepository.findByIdTagInGiftCertificate(idGiftCertificate, idTag)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.GIFT_CERTIFICATES_WITH_TAG_NOT_FOUND, idTag));
        return giftCertificateConverter.convertTo(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificate> currentGiftCertificate = giftCertificateRepository.findByName(giftCertificateDto.getName());
        if (currentGiftCertificate.isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                    giftCertificateDto.getName());
        }
        if (!ObjectUtils.isEmpty(giftCertificateDto.getTags())) {
            List<TagDto> tags = tagService.findByRangeNames(giftCertificateDto.getTags());
            List<TagDto> newTags = receiveNewTags(tagConverter.convertFrom(tags), giftCertificateDto.getTags());
            if (!newTags.isEmpty()) {
                throw new IllegalParameterException(ErrorMessageReader.GIFT_CERTIFICATE_INCORRECT_PARAMS);
            }
            giftCertificateDto.setTags(tags);
        }
        GiftCertificate giftCertificate = giftCertificateConverter.convertFrom(giftCertificateDto);
        return giftCertificateConverter.convertTo(giftCertificateRepository.save(giftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto addTagsToGiftCertificate(Long id, List<TagDto> tagsDto) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        id, GiftCertificate.class.getSimpleName()));
        List<TagDto> newTags = receiveNewTags(giftCertificate.getTags(), tagsDto);
        if (newTags.isEmpty()) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.TAG_ALREADY_EXISTS_IN_GIFT_CERTIFICATE,
                    giftCertificate.getName());
        }
        List<Tag> tags = giftCertificate.getTags();
        addNewTagsToGiftCertificate(giftCertificate, tagsDto);
        if (tags != null) {
            tagsDto.stream().filter(t -> tags.stream()
                    .anyMatch(tag -> !tag.getName().equals(t.getName()))).forEach(
                    t -> tags.add(tagConverter.convertFrom(t))
            );
        }
        return giftCertificateConverter.convertTo(giftCertificateRepository.save(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        GiftCertificate.class.getSimpleName()));
        List<Purchase> purchases = purchaseRepository.findByIdGiftCertificate(id);
        if (!ObjectUtils.isEmpty(purchases)) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATES_USED, giftCertificate.getName());
        }
        giftCertificateRepository.delete(giftCertificate);
    }

    @Override
    @Transactional
    public void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(idGiftCertificate).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, idGiftCertificate,
                        GiftCertificate.class.getSimpleName()));
        Optional<Tag> tagOptional = Optional.empty();
        if (!ObjectUtils.isEmpty(giftCertificate.getTags())) {
            tagOptional = giftCertificate.getTags().stream().filter(t -> t.getId().equals(idTag)).findFirst();
        }
        Tag tag = tagOptional.orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageReader.TAG_IN_GIFT_CERTIFICATE_NOT_FOUND, idTag));
        giftCertificate.getTags().remove(tag);
        giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate currentGiftCertificate = giftCertificateRepository.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        giftCertificateDto.getId(), GiftCertificate.class.getSimpleName()));
        Optional<GiftCertificate> giftCertificateWithNewNameInDB = giftCertificateRepository
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
        return giftCertificateConverter.convertTo(giftCertificateRepository.save(newGiftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto patchGiftCertificate(GiftCertificateDto giftCertificateDto) {
        //Check existence of certificate with this id
        GiftCertificate currentGiftCertificate = giftCertificateRepository.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND,
                        giftCertificateDto.getId(), GiftCertificate.class.getSimpleName()));
        //Check existence of certificate with new name. If there are found any certificate with the same name,
        //certificate won't be added
        if (!ObjectUtils.isEmpty(giftCertificateDto.getName())) {
            Optional<GiftCertificate> giftCertificateWithNewNameInDB = giftCertificateRepository
                    .findByName(giftCertificateDto.getName());
            if (giftCertificateWithNewNameInDB.isPresent()) {
                if (!giftCertificateWithNewNameInDB.get().getId().equals(giftCertificateDto.getId())) {
                    throw new ResourceAlreadyExistsException(ErrorMessageReader.GIFT_CERTIFICATE_ALREADY_EXISTS,
                            giftCertificateDto.getName());
                }
            }
        }
        GiftCertificate newGiftCertificate = mergeCurrentAndNewGiftCertificate(currentGiftCertificate, giftCertificateDto);
        return giftCertificateConverter.convertTo(giftCertificateRepository.save(newGiftCertificate));
    }

    private GiftCertificate mergeCurrentAndNewGiftCertificate(GiftCertificate currentGiftCertificate,
                                                              GiftCertificateDto newGiftCertificate) {
        if (!ObjectUtils.isEmpty(newGiftCertificate.getName())) {
            currentGiftCertificate.setName(newGiftCertificate.getName());
        }
        if (!ObjectUtils.isEmpty(newGiftCertificate.getDescription())) {
            currentGiftCertificate.setDescription(newGiftCertificate.getDescription());
        }
        if (!ObjectUtils.isEmpty(newGiftCertificate.getPrice())) {
            currentGiftCertificate.setPrice(newGiftCertificate.getPrice());
        }
        if (!ObjectUtils.isEmpty(newGiftCertificate.getDuration())) {
            currentGiftCertificate.setDuration(newGiftCertificate.getDuration());
        }
        if (!ObjectUtils.isEmpty(newGiftCertificate.getTags())) {
            addNewTagsToGiftCertificate(currentGiftCertificate, newGiftCertificate.getTags());
        }
        return currentGiftCertificate;
    }

    private List<TagDto> receiveNewTags(List<Tag> currentTags, List<TagDto> tagsDto) {
        List<TagDto> newTags = tagsDto;
        if (!ObjectUtils.isEmpty(currentTags)) {
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
        boolean isCorrect = true;
        if (newTags != null) {
            List<TagDto> tags = tagService.findByRangeNames(newTags);
            isCorrect = newTags.stream().allMatch(t ->
                    tags.stream().anyMatch(tag -> tag.getId().equals(t.getId())));
            if (isCorrect) {
                isCorrect = newTags.stream().allMatch(t ->
                        tags.stream()
                                .noneMatch(tag -> tag.getName().equals(t.getName()) && !tag.getId().equals(t.getId())));
            }
        }
        return isCorrect;
    }
}