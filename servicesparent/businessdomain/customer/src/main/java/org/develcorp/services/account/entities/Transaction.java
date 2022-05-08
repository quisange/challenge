package org.develcorp.services.account.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Transaction {

    private Long transactionId;

    private Long accountId;

    private Date date;

    public enum TransactionType {
        Retiro,
        Deposito,
        Apertura
    }

    private TransactionType transactionType;

    private BigDecimal value;

    private BigDecimal balance;

    private boolean status;

    private String message;
}
