package com.manhcode.springrest.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import com.manhcode.springrest.demo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// inject session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create a query ... sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;
	}

	@Override
	public Customer getCustomer(int theId) {

		// get currnent session
		Session currentSession = sessionFactory.getCurrentSession();

		// get customer
		Customer theCustomer = currentSession.get(Customer.class, theId);

		return theCustomer;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get currnent session
		Session currentSession = sessionFactory.getCurrentSession();

		// save/upate the customer ... finally LOL
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public void deletecustomer(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete object with primary key
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);

		theQuery.executeUpdate();

	}

}
