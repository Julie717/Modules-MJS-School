package com.epam.esm.controller;

import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.util.HateoasLinkBuilder;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/purchases", produces = APPLICATION_JSON_VALUE)
@Validated
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseResponseDto> findAll(@Valid @NotNull Pagination pagination) {
        List<PurchaseResponseDto> purchases = purchaseService.findAll(pagination);
        HateoasLinkBuilder.buildPurchasesLink(purchases);
        return purchases;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseResponseDto findById(@PathVariable @Positive Long id) {
        PurchaseResponseDto purchase = purchaseService.findById(id);
        HateoasLinkBuilder.buildPurchaseLink(purchase);
        return purchase;
    }
}