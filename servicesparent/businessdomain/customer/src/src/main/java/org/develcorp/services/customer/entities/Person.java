package org.develcorp.services.customer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Data @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personId;

    @NotEmpty (message = "El nombre no puede estar vacio.")
    @Column (length = 100, nullable = false)
    private String name;

    public enum Gender {
        Masculino,
        Femenino
    }

    @Column (length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Positive (message = "La edad debe ser superior a 0.")
    private int age;

    @NotEmpty (message = "La identificacion no puede estar vacia.")
    @Size (min = 10, max = 10, message = "La identificacion debe tener 10 digitos")
    @Column (unique = true, length = 10, nullable = false)
    private String identification;

    @NotEmpty (message = "La identificacionno puede estar vacia.")
    @Column (length = 100, nullable = false)
    private String address;

    @NotEmpty (message = "El telefono no puede estar vacio.")
    @Size (min = 7, max = 10, message = "El telefono debe tener entre 7 y 10 digitos")
    @Column (length = 10, nullable = false)
    private String phone;
}
