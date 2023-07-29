package org.example.dao;

import org.example.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepoI extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmailAllIgnoreCase(String email);

}