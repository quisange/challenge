package org.develcorp.services.account.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
// @SequenceGenerator(name="seq", initialValue=100000, allocationSize=100)
public class Account {

    @Id
    private Long accountId;

    private Long customerId;

    @ManyToOne
    private AccountType accountType;

    @Positive (message = "El valor inicial debe ser mayor a 0.")
    private BigDecimal initialBalance;

    private boolean status;

    @Transient
    private List<?> transactions;
}
