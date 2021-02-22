package com.epam.esm.controller;

import com.epam.esm.model.CustomUserDetails;
import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.HateoasLinkBuilder;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * The type Purchase controller is used to receive REST API requests
 * with mapping "/purchases".
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/purchases", produces = APPLICATION_JSON_VALUE)
@Validated
public class PurchaseController {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final PurchaseService purchaseService;

    /**
     * Find all purchases.
     *
     * @param pagination contains limit and offset for search
     * @return the list of purchases response DTO
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<PurchaseResponseDto> findAll(@Valid @NotNull Pagination pagination, SecurityContextHolderAwareRequestWrapper requestWrapper) {
        List<PurchaseResponseDto> purchases;
        if (requestWrapper.isUserInRole(ROLE_ADMIN)) {
            purchases = purchaseService.findAll(pagination);
        } else {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=(UsernamePasswordAuthenticationToken)requestWrapper.getUserPrincipal();
            CustomUserDetails userDetails=(CustomUserDetails)usernamePasswordAuthenticationToken.getPrincipal();
            purchases = purchaseService.findByIdUser(userDetails.getId(),pagination);
        }
        HateoasLinkBuilder.buildPurchasesLink(purchases);
        return purchases;
    }

    /**
     * Find purchase by id.
     *
     * @param id is the id of purchase
     * @return the purchase response DTO
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public PurchaseResponseDto findById(@PathVariable @Positive Long id, SecurityContextHolderAwareRequestWrapper requestWrapper) {
        PurchaseResponseDto purchase = purchaseService.findById(id);
        if (!requestWrapper.isUserInRole(ROLE_ADMIN)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=(UsernamePasswordAuthenticationToken)requestWrapper.getUserPrincipal();
            CustomUserDetails userDetails=(CustomUserDetails)usernamePasswordAuthenticationToken.getPrincipal();
            if (!userDetails.getId().equals(purchase.getIdUser())){
                throw new AccessDeniedException(ErrorMessageReader.ACCESS_DENIED);
            }
        }
        HateoasLinkBuilder.buildPurchaseLink(purchase);
        return purchase;
    }

    /**
     * Provides the ability to make purchase for user.
     *
     * @param purchaseDto is the purchase DTO that contains id user and list of gift certificate's ids
     * @return the purchase response DTO - information about new created purchase
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public PurchaseResponseDto makePurchase(@RequestBody @Valid PurchaseRequestDto purchaseDto) {
        PurchaseResponseDto purchase = purchaseService.makePurchase(purchaseDto);
        HateoasLinkBuilder.buildPurchaseLink(purchase);
        return purchase;
    }
}