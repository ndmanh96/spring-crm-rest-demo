package com.manhcode.springrest.demo.service;

import java.util.List;

import com.manhcode.springrest.demo.entity.Customer;

public interface CustomerService {
	public List<Customer> getCustomers();

	public Customer getCustomer(int theId);

	public void saveCustomer(Customer theCustomer);

	public void deletecustomer(int theId);
}
