package com.epam.esm.controller;

import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.HateoasLinkBuilder;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * The type Tag controller is used to receive REST API requests
 * with mapping "/tags".
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/tags", produces = APPLICATION_JSON_VALUE)
@Validated
public class TagController {
    private final TagService tagService;

    /**
     * Find all tags.
     *
     * @return the list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> findAll(@NotNull @Valid Pagination pagination) {
        List<TagDto> tags = tagService.findAll(pagination);
        HateoasLinkBuilder.buildTagsLink(tags);
        return tags;
    }

    /**
     * Find by id tag.
     *
     * @param id the id
     * @return the tag dto
     * @throws ResourceNotFoundException if tag isn't found
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findById(@PathVariable @Positive Long id) {
        TagDto tag = tagService.findById(id);
        HateoasLinkBuilder.buildTagLink(tag);
        return tag;
    }

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> findTopTag(@NotNull @Valid Pagination pagination) {
        List<TagDto> tags = tagService.findTopTag(pagination);
        HateoasLinkBuilder.buildTagsLink(tags);
        return tags;
    }

    /**
     * Add tag to Db.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     * @throws ResourceAlreadyExistsException if tag with such name already exists
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@Valid @RequestBody TagDto tagDto) {
        TagDto tag = tagService.add(tagDto);
        HateoasLinkBuilder.buildTagLink(tag);
        return tag;
    }

    /**
     * Delete tag from Db.
     *
     * @param id the id
     * @throws ResourceNotFoundException if tag with such id isn't found
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable @Positive Long id) {
        tagService.deleteById(id);
    }
}