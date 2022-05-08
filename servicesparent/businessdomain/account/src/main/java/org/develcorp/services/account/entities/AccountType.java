package org.develcorp.services.account.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountTypeId;

    @NotEmpty(message = "La descripcion no puede estar vacia.")
    @Column(length = 50, nullable = false)
    private String description;
}
