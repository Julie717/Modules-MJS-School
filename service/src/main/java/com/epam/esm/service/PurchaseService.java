package com.epam.esm.service;

import com.epam.esm.model.PurchaseRequestDto;
import com.epam.esm.model.PurchaseResponseDto;
import com.epam.esm.util.Pagination;

import java.util.List;

/**
 * The interface Purchase service.
 */
public interface PurchaseService {
    /**
     * Find all purchases.
     *
     * @param pagination contains limit and offset for search
     * @param idUser     is the id of authorised user
     * @param role       is the role of authorised user
     * @return the list of purchases
     */
    List<PurchaseResponseDto> findAll(Pagination pagination, Long idUser, String role);

    /**
     * Find purchase by id.
     *
     * @param id     is the id of purchase
     * @param idUser is the id of authorised user
     * @param role   is the role of authorised user
     * @return the purchase response DTO
     */
    PurchaseResponseDto findById(Long id, Long idUser, String role);

    /**
     * Create purchase for user.
     *
     * @param purchase is the purchase that contains id user and list of gift certificate's ids
     * @param idUser   is the id of authorised user
     * @param role     is the role of authorised user
     * @return the purchase response DTO
     */
    PurchaseResponseDto makePurchase(PurchaseRequestDto purchase, Long idUser, String role);
}