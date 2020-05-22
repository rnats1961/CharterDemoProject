package com.charter.example.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charter.example.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
