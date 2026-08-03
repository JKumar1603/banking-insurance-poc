package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
@Autowired private CustomerRepository repository;
public Customer addCustomer(Customer customer){return repository.save(customer);} 
public List<Customer> getAllCustomers(){return repository.findAll();}
public Customer getCustomerById(Long id){return repository.findById(id).orElseThrow();}
public Customer updateCustomer(Long id, Customer customer){customer.setId(id); return repository.save(customer);}
public void deleteCustomer(Long id){repository.deleteById(id);}
}