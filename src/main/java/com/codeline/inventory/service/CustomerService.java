package com.codeline.inventory.service;

import com.codeline.inventory.entity.Customer;
import com.codeline.inventory.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        return customerRepository.save(customer);
    }
}
