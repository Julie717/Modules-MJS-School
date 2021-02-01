package com.epam.esm.model;

import com.epam.esm.dao.ColumnName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.USER_ID)
    Long id;

    @Column(name = ColumnName.USER_NAME)
    String name;

    @Column(name = ColumnName.USER_SURNAME)
    String surname;

    @OneToMany(mappedBy = "user",orphanRemoval = true)
    List<Purchase> purchases;
}