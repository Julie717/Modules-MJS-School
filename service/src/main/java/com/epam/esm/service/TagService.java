package com.epam.esm.service;

import com.epam.esm.model.Tag;
import com.epam.esm.exception.IncorrectValueException;
import com.epam.esm.exception.ResourceIsAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagDto;

import java.util.List;

public interface TagService {
    List<Tag> findAll();
    Tag findById(int idTag) throws ResourceNotFoundException;
    boolean add(TagDto tagDto) throws ResourceIsAlreadyExistException, IncorrectValueException;
    boolean deleteById(int idTag) throws ResourceNotFoundException;
}