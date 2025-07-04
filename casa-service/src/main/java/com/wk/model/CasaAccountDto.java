package com.wk.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CasaAccountDto {
    private String accountNo;
    private String accountType;
    private BigDecimal amount;
    private String status;
}

