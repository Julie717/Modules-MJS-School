package com.epam.esm.controller;

import com.epam.esm.model.GiftCertificateDto;
import com.epam.esm.model.TagDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/giftCertificates", produces = APPLICATION_JSON_VALUE)
@Validated
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public GiftCertificateDto findById(@PathVariable @Positive Integer id) {
        return giftCertificateService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@Validated(ValidationGroup.CreateValidation.class)
                                                 @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.add(giftCertificateDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable @Positive Integer id) {
        giftCertificateService.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<GiftCertificateDto> findByParameters(@RequestParam Map<String, String> parameters) {
        List<GiftCertificateDto> giftCertificates;
        if (parameters.isEmpty()) {
            giftCertificates = giftCertificateService.findAll();
        } else {
            giftCertificates = giftCertificateService.findByParameters(parameters);
        }
        return giftCertificates;
    }

    @PutMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable
                                                    @Positive Integer id,
                                                    @Validated(ValidationGroup.UpdateValidation.class)
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setIdGiftCertificate(id);
        return giftCertificateService.updateGiftCertificate(giftCertificateDto);
    }

    @GetMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.FOUND)
    public GiftCertificateDto findGiftCertificateWithTags(@PathVariable @Positive Integer id,
                                                          @RequestParam(required = false) String nameTag) {
        GiftCertificateDto giftCertificateDto;
        if (nameTag == null) {
            giftCertificateDto = giftCertificateService.findGiftCertificateWithTags(id);
        } else {
            giftCertificateDto = giftCertificateService.findGiftCertificateWithTagsByTagName(id, nameTag);
        }
        return giftCertificateDto;
    }

    @PostMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.FOUND)
    public GiftCertificateDto addTagsToGiftCertificate(@PathVariable @Positive Integer id,
                                                       @Valid @NotNull @RequestBody List<TagDto> tags) {
        return giftCertificateService.addTagsToGiftCertificate(id, tags);
    }

    @GetMapping(value = "/tags")
    @ResponseStatus(HttpStatus.FOUND)
    public List<GiftCertificateDto> findGiftCertificatesWithTags() {
        return giftCertificateService.findAllWithTags();
    }
}