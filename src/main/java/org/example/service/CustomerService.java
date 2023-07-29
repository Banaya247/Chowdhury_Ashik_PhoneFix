package org.example.service;

import org.example.dao.CustomerRepoI;
import org.example.dto.CustomerDTO;
import org.example.models.Customer;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CustomerService {

    CustomerRepoI customerRepoI;

    @Autowired
    public CustomerService(CustomerRepoI customerRepoI) {
        this.customerRepoI = customerRepoI;
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customers = customerRepoI.findAll();

        if(customers.isEmpty()) {
            log.debug("Empty list of Customers!!");
            throw new Exception("Empty List!");
        }

        return customers;
    }

    public List<CustomerDTO> getCustomerEssentialInfo() {

        return customerRepoI.findAll().stream().map((oneCustomer) -> {
            return new CustomerDTO(oneCustomer.getId(), oneCustomer.getFullName(), oneCustomer.getDob());
        }).collect(Collectors.toList());

    }
}
