package com.epam.esm.controller;

import com.epam.esm.model.Tag;
import com.epam.esm.exception.IncorrectValueException;
import com.epam.esm.exception.ResourceIsAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/tags")
@Validated
public class TagController {
    private TagService tagService;
    private TagValidator tagValidator;

    @Autowired
    public TagController(TagService tagService, TagValidator tagValidator) {
        this.tagService = tagService;
        this.tagValidator = tagValidator;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Tag> tags() {
        return tagService.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.FOUND)
    public Tag findById(@PathVariable int id) throws ResourceNotFoundException {
        return tagService.findById(id);
    }

    @PostMapping(value = "/addTag", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean add(@Valid @RequestBody TagDto tagDto) throws ResourceIsAlreadyExistException, IncorrectValueException {
     //   tagValidator.validate(tag, result);
       // if (result.hasErrors()) {
       //     StringBuffer fieldsName = new StringBuffer();
       /*     result.getFieldErrors().forEach(s -> fieldsName.append(s.getField() + ", "));
            fieldsName.delete(fieldsName.length() - 2, fieldsName.length() );
            throw new IncorrectValueException("Incorrect value in fields: " + fieldsName);
        }*/
        return tagService.add(tagDto);
    }

    @DeleteMapping(value = "/deleteTag", produces = APPLICATION_JSON_VALUE)
    public boolean deleteGiftCertificate(@Valid @PathVariable Integer id)throws ResourceNotFoundException {
        return tagService.deleteById(id);
    }
}