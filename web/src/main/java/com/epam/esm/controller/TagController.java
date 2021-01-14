package com.epam.esm.controller;

import com.epam.esm.model.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/tags", produces = APPLICATION_JSON_VALUE)
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<TagDto> findAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public TagDto findById(@PathVariable @Positive Integer id) {
        return tagService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@Valid @RequestBody TagDto tagDto) {
        return tagService.add(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable @Positive Integer id) {
        tagService.deleteById(id);
    }
}