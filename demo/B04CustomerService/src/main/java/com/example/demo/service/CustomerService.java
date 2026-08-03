package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Customer;

public interface CustomerService {
Customer addCustomer(Customer customer);
List<Customer> getAllCustomers();
Customer getCustomerById(Long id);
Customer updateCustomer(Long id, Customer customer);
void deleteCustomer(Long id);
}