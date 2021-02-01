package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorMessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagConverterImpl tagConverter;

    @Autowired

    public TagServiceImpl(TagDao tagDao, TagConverterImpl tagConverter) {
        this.tagDao = tagDao;
        this.tagConverter = tagConverter;
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = tagDao.findAll();
        return tagConverter.convertTo(tags);
    }

    @Override
    public TagDto findById(Long id) {
        Tag tag = tagDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Tag.class.getSimpleName()));
        return tagConverter.convertTo(tag);
    }

    @Override
    public List<TagDto> findByRangeNames(List<TagDto> tagsDto) {
        List<String> tagNames = tagsDto.stream().map(tag -> tag.getName()).collect(Collectors.toList());
        return tagConverter.convertTo(tagDao.findTagByNameInRange(tagNames));
    }

    @Override
    @Transactional
    public TagDto add(TagDto tagDto) {
        boolean isExist = tagDao.findTagByName(tagDto.getName()).isPresent();
        if (isExist) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.TAG_ALREADY_EXISTS,
                    tagDto.getName());
        }
        Tag tag = tagConverter.convertFrom(tagDto);
        return tagConverter.convertTo(tagDao.add(tag));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tag tag = tagDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Tag.class.getSimpleName()));
        tagDao.deleteTagFromGiftCertificates(id);
        tagDao.delete(tag);
    }
}