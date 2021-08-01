package com.manhcode.springrest.demo.dao;

import java.util.List;

import com.manhcode.springrest.demo.entity.Customer;

public interface CustomerDAO {
	public List<Customer> getCustomers();
	
	public Customer getCustomer(int theId);
	
	public void saveCustomer(Customer theCustomer);
	
	public void deletecustomer(int theId);
}
