package org.develcorp.services.customer.services;

import org.develcorp.services.customer.entities.Customer;

import java.net.UnknownHostException;
import java.util.List;

public interface CustomerService {

    List<Customer> listAllCustomers();

    Customer getCustomer(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer deleteCustomer(Long id);

    List<Customer> findByName(String name);

    Customer findByIdentification(String identification);
}