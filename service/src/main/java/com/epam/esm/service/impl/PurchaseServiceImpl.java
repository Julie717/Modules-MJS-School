package com.epam.esm.service.impl;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.User;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.Role;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.converter.impl.GiftCertificateConverterImpl;
import com.epam.esm.model.converter.impl.PurchaseResponseConverterImpl;
import com.epam.esm.repository.PurchaseRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.util.ErrorMessageReader;
import com.epam.esm.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PurchaseResponseConverterImpl purchaseResponseConverter;
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateConverterImpl giftCertificateConverter;

    @Override
    public List<PurchaseResponseDto> findAll(Pagination pagination, Long idUser, String role) {
        List<PurchaseResponseDto> purchases;
        if (Role.valueOf(role) == Role.ROLE_ADMIN) {
            purchases = findAll(pagination);
        } else {
            purchases = findByIdUser(idUser, pagination);
        }
        if (purchases.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessageReader.RESOURCES_NOT_FOUND, Purchase.class.getSimpleName());
        }
        return purchases;
    }


    @Override
    public PurchaseResponseDto findById(Long id, Long idUser, String role) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Purchase.class.getSimpleName()));
        if (Role.valueOf(role) == Role.ROLE_USER && !idUser.equals(purchase.getUser().getId())) {
            throw new AccessDeniedException(ErrorMessageReader.ACCESS_DENIED);
        }
        return purchaseResponseConverter.convertTo(purchase);
    }

    @Override
    @Transactional
    public PurchaseResponseDto makePurchase(PurchaseRequestDto purchaseRequestDto, Long idUser, String role) {
        if (purchaseRequestDto.getIdUser() != null) {
            if (Role.valueOf(role) == Role.ROLE_ADMIN) {
                idUser = purchaseRequestDto.getIdUser();
            } else {
                if (!idUser.equals(purchaseRequestDto.getIdUser())) {
                    throw new AccessDeniedException(ErrorMessageReader.ACCESS_DENIED);
                }
            }
        }
        Long finalIdUser = idUser;
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, finalIdUser,
                        User.class.getSimpleName()));
        List<Long> idGiftCertificates = purchaseRequestDto.getIdGiftCertificates();
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        idGiftCertificates.stream().map(giftCertificateService::findById)
                .forEach(g -> giftCertificates.add(giftCertificateConverter.convertFrom(g)));
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setGiftCertificates(giftCertificates);
        purchase.setCost(countCost(giftCertificates));
        purchase = purchaseRepository.save(purchase);
        return purchaseResponseConverter.convertTo(purchase);
    }

    private List<PurchaseResponseDto> findAll(Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage());
        List<Purchase> purchases = purchaseRepository.findAll(pageable).getContent();
        return purchaseResponseConverter.convertTo(purchases);
    }

    private List<PurchaseResponseDto> findByIdUser(Long idUser, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPerPage());
        List<Purchase> purchases = purchaseRepository.findByIdUser(idUser, pageable);
        return purchaseResponseConverter.convertTo(purchases);
    }

    private BigDecimal countCost(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}