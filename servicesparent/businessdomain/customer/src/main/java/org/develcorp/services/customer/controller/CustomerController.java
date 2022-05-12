package org.develcorp.services.customer.controller;

import org.develcorp.services.customer.entities.Customer;
import org.develcorp.services.customer.exception.ApiExceptionHandler;
import org.develcorp.services.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    @GetMapping (value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        Customer customer = customerService.getCustomer(id);

        if(customer == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomer(@RequestParam(name = "name", required = false) String name){
        List<Customer> customers;

        if (null == name){
            customers = customerService.listAllCustomers();
        } else {
            customers = customerService.findByName(name);
        }

        if(customers == null || customers.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, apiExceptionHandler.formatMessage(result));
        }

        Customer customerDB = customerService.createCustomer (customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        customer.setPersonId(id);

        Customer customerDB = customerService.updateCustomer(customer);
        return ResponseEntity.ok(customerDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        Customer customerDelete = customerService.deleteCustomer(id);
        return ResponseEntity.ok(customerDelete);
    }
}