package com.manhcode.springrest.demo.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

@RestController
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.manhcode.springrest.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoConfig {
	@Autowired
	private Environment env;

	private Logger logger = Logger.getLogger(getClass().getName());

	@Bean
	public DataSource myDataSource() {
		// create connection pool
		ComboPooledDataSource myDataSource = new ComboPooledDataSource();

		// set jdbc driver
		try {
			myDataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for sanity's sake, let's log url and user ... just to make sure we are
		// reading the data
		logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info("jdbc.user=" + env.getProperty("jdbc.user"));

		// set database connection props
		myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		myDataSource.setUser(env.getProperty("jdbc.user"));
		myDataSource.setPassword(env.getProperty("jdbc.password"));

		// set connection pool props
		myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		return myDataSource;
	}

	// need a helper method
	// read environment property and convert to int

	private int getIntProperty(String propName) {

		String propVal = env.getProperty(propName);

		// now convert to int
		int intPropVal = Integer.parseInt(propVal);

		return intPropVal;
	}

//	Hibernate
	@Bean
	private Properties getHibernateProperties() {
		Properties props = new Properties();

		// get hibernate properties
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

		return props;
	}

	// sessionfactory
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// create session factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		// set properties
		sessionFactory.setDataSource(myDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());

		return sessionFactory;

	}

	// Hibernate Transaction
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}
}
