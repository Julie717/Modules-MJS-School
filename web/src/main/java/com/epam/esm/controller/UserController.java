package com.epam.esm.controller;

import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.model.UserDto;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserService userService;
    private final PurchaseService purchaseService;

    public UserController(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAllTags() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable @Positive Long id) {
        return userService.findById(id);
    }

    @PostMapping("/{idUser}/purchases")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseRequestDto makePurchase(@PathVariable @Positive Long idUser,
                                            @RequestBody PurchaseRequestDto purchase) {
        purchase.setIdUser(idUser);
        return purchaseService.makePurchase(purchase);
    }
}