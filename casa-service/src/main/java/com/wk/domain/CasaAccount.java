package com.wk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "casa_account")
@Data
public class CasaAccount {
    @Id
    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private String status;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}

