package com.spring.login.repo.model;

import com.spring.login.util.MerchantStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Merchant", schema = "testdb")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;

    @Column(nullable = false)
    private Double totalTransactionSum;

}
