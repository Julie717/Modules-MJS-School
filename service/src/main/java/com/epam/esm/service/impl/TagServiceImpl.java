package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Tag;
import com.epam.esm.exception.ResourceIsAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag findById(int idTag) throws ResourceNotFoundException {
        return tagDao.findById(idTag)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id = " + idTag + " isn't found"));
    }

    @Override
    public boolean add(TagDto tagDto) throws ResourceIsAlreadyExistException {
        boolean isExisted = tagDao.findTagByName(tagDto.getName()).isPresent();
        if (isExisted) {
            throw new ResourceIsAlreadyExistException("Tag with name \"" + tagDto.getName() + "\" is already exist");
        } else {
            Tag tag = new Tag();
            tag.setName(tagDto.getName());
            return tagDao.add(tag);
        }
    }

    @Override
    public boolean deleteById(int idTag) throws ResourceNotFoundException {
        boolean isExisted = tagDao.findById(idTag).isPresent();
        if (isExisted) {
            throw new ResourceNotFoundException("Tag with id \"" + idTag + "\" isn't found");
        } else {
            return tagDao.deleteById(idTag);
        }
    }
}