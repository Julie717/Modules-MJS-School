package com.epam.esm.model;

import com.epam.esm.dao.ColumnName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.PURCHASE_ID)
    Long id;

    @Column(name = ColumnName.PURCHASE_COST)
    BigDecimal cost;

    @Column(name = ColumnName.PURCHASE_DATE)
    Timestamp purchaseDate;

    @ManyToOne
    @JoinColumn(name = ColumnName.USER_ID)
    User user;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "purchase_gift_certificate",
            joinColumns = {@JoinColumn(name = ColumnName.PURCHASE_ID)},
            inverseJoinColumns = {@JoinColumn(name = ColumnName.GIFT_CERTIFICATE_ID)})
    List<GiftCertificate> giftCertificates;
}
