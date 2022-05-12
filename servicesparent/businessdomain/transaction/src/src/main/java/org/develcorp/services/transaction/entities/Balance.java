package org.develcorp.services.transaction.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountId;

    private BigDecimal actualBalance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
}
