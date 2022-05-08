package org.develcorp.services.customer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor @NoArgsConstructor
public class Customer extends Person implements Serializable {
    @Size (min = 4, max = 12, message = "Su clave debe tener entre 8 y 12 caracteres.")
    @Column (length = 12, nullable = false)
    private String password;

    private boolean status;

    @Transient
    List<?> accounts;
}
