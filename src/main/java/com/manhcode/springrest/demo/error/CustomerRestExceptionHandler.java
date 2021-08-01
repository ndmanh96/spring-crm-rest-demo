package com.manhcode.springrest.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerRestExceptionHandler {
	// add an exception for Customernotdfound
	@ExceptionHandler
	public ResponseEntity<CustomerErrorMessage> handleException(CustomerNotFoundException e) {
		//create responsemessage
		CustomerErrorMessage message = new CustomerErrorMessage();
		
		message.setStatus(HttpStatus.NOT_FOUND.value());
		message.setMessage(e.getMessage());
		message.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		
	}
	
	
	//add an another exception to catch any exception
	@ExceptionHandler
	public ResponseEntity<CustomerErrorMessage> handleException(Exception e) {
		//create responsemessage
		CustomerErrorMessage message = new CustomerErrorMessage();
		
		message.setStatus(HttpStatus.BAD_REQUEST.value());
		message.setMessage(e.getMessage());
		message.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		
	}
	
}
