package com.manhcode.springrest.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhcode.springrest.demo.entity.Customer;
import com.manhcode.springrest.demo.error.CustomerNotFoundException;
import com.manhcode.springrest.demo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	//inject service
	@Autowired
	private CustomerService customerService;
	
	//getall customer
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}
	
	//get customer by id
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found- "+ customerId);
		}
		return theCustomer;
	}
	
	//add customer by POST method
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		theCustomer.setId(0); // saveorupdate find 0---> notfound --> addd
		
		//service add new customer
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	} 
	
	//Update customer use PUT method
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		//service update
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	//Delete customer by id
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found- "+ customerId);
		}
		
		customerService.deletecustomer(customerId);
		
		return "Deleted customer id- " + customerId;
	}
	
	@GetMapping("/hello")
	public String showHello() {
		return "Hello World";
	}
	
}
