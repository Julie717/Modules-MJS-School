package com.epam.esm.criteriabuilder;

public class GiftCertificateQueryBuilder {

    /*public static  CriteriaQuery build(GiftCertificateSearchParameter searchParameter, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        SetJoin<GiftCertificate, Tag> giftCertificateTagSetJoin = giftCertificateRoot.join(GiftCertificate_.tags);
        List<Predicate> predicates = new ArrayList<>();
        if (searchParameter.getNameGiftCertificate() != null) {
            predicates.add(criteriaBuilder.equal(giftCertificateRoot.get("name"), searchParameter.getNameGiftCertificate()));
        }
        if (searchParameter.getDescription() != null) {
            predicates.add(criteriaBuilder.equal(giftCertificateRoot.get("description"),
                    searchParameter.getDescription()));
        }
        if (searchParameter.getNameTag() != null) {
            predicates.add(criteriaBuilder.equal(giftCertificateRoot.get("tags"),
                    searchParameter.getNameTag()));
        }
        return criteriaQuery;
    }*/
}
