package org.develcorp.services.customer.repository;

import org.assertj.core.api.Assertions;
import org.develcorp.services.customer.entities.Customer;
import org.develcorp.services.customer.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryMockTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindAll_thenReturnListCustomers(){
        Customer customer = Customer.builder()
                .age(23)
                .address("Amazonas")
                .gender(Person.Gender.Masculino)
                .identification("0103948374")
                .name("Jhon Lazo")
                .phone("0948734748")
                .password("39432328")
                .status(true)
                .build();

        customerRepository.save(customer);

        List<Customer> founds = customerRepository.findAll();

        Assertions.assertThat(founds.size()).isEqualTo(1);
    }
}
