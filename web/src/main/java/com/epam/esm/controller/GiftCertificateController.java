package com.epam.esm.controller;

import com.epam.esm.exception.IllegalParameterException;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
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

/**
 * The type Gift certificate controller is used to receive REST API requests
 * with mapping "/giftCertificates".
 */
@RestController
@RequestMapping(value = "/giftCertificates", produces = APPLICATION_JSON_VALUE)
@Validated
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Find by id gift certificate.
     *
     * @param id the id
     * @return the gift certificate dto
     * @throws ResourceNotFoundException if gift certificate isn't found
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findById(@PathVariable @Positive Integer id) {
        return giftCertificateService.findById(id);
    }

    /**
     * Add gift certificate to Db.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     * @throws ResourceAlreadyExistsException if gift certificate with such name already exists in DB
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@Validated(ValidationGroup.CreateValidation.class)
                                                 @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.add(giftCertificateDto);
    }

    /**
     * Delete gift certificate FROM Db.
     *
     * @param id the id
     * @throws ResourceNotFoundException if gift certificate with such id isn't found
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable @Positive Integer id) {
        giftCertificateService.deleteById(id);
    }

    /**
     * Find by parameters gift certificates.
     *
     * @param parameters the parameters
     * @return the list
     * @throws IllegalParameterException if search parameters aren't correct
     * @throws ResourceNotFoundException if gift certificates with such parameters aren't found
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findByParameters(@RequestParam Map<String, String> parameters) {
        List<GiftCertificateDto> giftCertificates;
        if (parameters.isEmpty()) {
            giftCertificates = giftCertificateService.findAll();
        } else {
            giftCertificates = giftCertificateService.findByParameters(parameters);
        }
        return giftCertificates;
    }

    /**
     * Update gift certificate parameters.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     * @throws ResourceNotFoundException      if gift certificate with such id isn't found
     * @throws ResourceAlreadyExistsException if gift certificate with updated name exists in DB
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable
                                                    @Positive Integer id,
                                                    @Validated(ValidationGroup.UpdateValidation.class)
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setIdGiftCertificate(id);
        return giftCertificateService.updateGiftCertificate(giftCertificateDto);
    }

    /**
     * Find gift certificate with tags by id.
     *
     * @param id      the id
     * @param nameTag the name tag
     * @return the gift certificate dto
     * @throws ResourceNotFoundException if gift certificate isn't found
     */
    @GetMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
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

    /**
     * Add tags to gift certificate gift certificate dto.
     *
     * @param id   the id
     * @param tags the tags
     * @return the gift certificate dto
     * @throws ResourceNotFoundException if gift certificate isn't found
     */
    @PostMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addTagsToGiftCertificate(@PathVariable @Positive Integer id,
                                                       @Valid @NotNull @RequestBody List<TagDto> tags) {
        return giftCertificateService.addTagsToGiftCertificate(id, tags);
    }

    /**
     * Find gift certificates with tags list.
     *
     * @return the list
     */
    @GetMapping(value = "/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findGiftCertificatesWithTags() {
        return giftCertificateService.findAllWithTags();
    }

    /**
     * Delete tag from gift certificate.
     *
     * @param idGiftCertificate the id gift certificate
     * @param idTag             the id tag
     * @throws ResourceNotFoundException if gift certificate with such tag isn't found
     */
    @DeleteMapping(value = "/{idGiftCertificate}/tags/{idTag}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTagFromGiftCertificate(@PathVariable @Positive Integer idGiftCertificate,
                                             @PathVariable @Positive Integer idTag) {
        giftCertificateService.deleteTagFromGiftCertificate(idGiftCertificate, idTag);
    }
}