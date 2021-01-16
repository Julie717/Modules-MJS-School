package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import com.epam.esm.exception.ResourceIsAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagDto;
import com.epam.esm.model.converter.impl.TagConverterImpl;
import com.epam.esm.querybuilder.TagSearchBuilder;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorMessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public TagDto findById(int idTag) {
        Tag tag = tagDao.findById(idTag)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.TAG_NOT_FOUND, idTag));
        return tagConverter.convertTo(tag);
    }

    @Override
    public TagDto add(TagDto tagDto) {
        boolean isExist = tagDao.findTagByName(tagDto.getNameTag()).isPresent();
        if (isExist) {
            throw new ResourceIsAlreadyExistException(ErrorMessageReader.TAG_ALREADY_EXISTS,
                    tagDto.getNameTag());
        }
        Tag tag = tagConverter.convertFrom(tagDto);
        return tagConverter.convertTo(tagDao.add(tag));
    }

    @Override
    @Transactional
    public void deleteById(int idTag) {
        tagDao.findById(idTag)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.TAG_NOT_FOUND, idTag));
        tagDao.deleteFromGiftCertificateTag(idTag);
        tagDao.deleteById(idTag);
    }

    @Override
    public List<TagDto> findByRangeNames(List<TagDto> tagsDto) {
        String tagsNameForQuery = TagSearchBuilder.buildQueryToSearchTags(tagsDto);
        return tagConverter.convertTo(tagDao.findTagByNameInRange(tagsNameForQuery));
    }
}