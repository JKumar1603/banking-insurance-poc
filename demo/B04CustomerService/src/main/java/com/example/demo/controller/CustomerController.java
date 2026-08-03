package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
@Autowired private CustomerService service;
@PostMapping("/add") public Customer addCustomer(@RequestBody Customer c){return service.addCustomer(c);}
@GetMapping("/all") public List<Customer> getAllCustomers(){return service.getAllCustomers();}
}