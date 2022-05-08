package org.develcorp.services.account.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Report {

    private Date date;
    private String customer;
    private Long accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean status;
    private BigDecimal transactionValue;
    private BigDecimal transactionBalance;
}