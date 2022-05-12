package org.develcorp.services.transaction.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    private Long accountId;

    @Column(length = 100, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    public enum TransactionType {
        Retiro,
        Deposito,
        Apertura
    }

    @Column (length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Positive(message = "El valor debe ser superior a 0")
    private BigDecimal value;

    private BigDecimal balance;

    private boolean status;

    private String message;
}
