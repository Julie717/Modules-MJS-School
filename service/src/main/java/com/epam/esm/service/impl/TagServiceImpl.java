package com.epam.esm.service.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagConverterImpl tagConverter;

    @Override
    public List<TagDto> findAll(Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage());
        List<Tag> tags = tagRepository.findAll(pageable).getContent();
        if (tags.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.RESOURCES_NOT_FOUND, Tag.class.getSimpleName());
        }
        return tagConverter.convertTo(tags);
    }

    @Override
    public TagDto findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Tag.class.getSimpleName()));
        return tagConverter.convertTo(tag);
    }

    @Override
    public List<TagDto> findByRangeNames(List<TagDto> tagsDto) {
        List<String> tagNames = tagsDto.stream().map(TagDto::getName).collect(Collectors.toList());
        return tagConverter.convertTo(tagRepository.findByNameIn(tagNames));
    }

    public List<TagDto> findTopTag(Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage());
        List<Tag> tags = tagRepository.findTopTag(pageable);
        if (tags.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.RESOURCES_NOT_FOUND, Tag.class.getSimpleName());
        }
        return tagConverter.convertTo(tags);
    }

    @Override
    @Transactional
    public TagDto add(TagDto tagDto) {
        boolean isExist = tagRepository.findByName(tagDto.getName()).isPresent();
        if (isExist) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.TAG_ALREADY_EXISTS,
                    tagDto.getName());
        }
        Tag tag = tagConverter.convertFrom(tagDto);
        return tagConverter.convertTo(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Tag.class.getSimpleName()));
        tagRepository.deleteTagFromGiftCertificates(id);
        tagRepository.delete(tag);
    }
}