package spring_boot_coupon_system.exceptions;

import lombok.Data;


public enum ErrorMessages {
	
	
	WRONG_DATA_PROVIDED("Wrong data provided:unexpected value"),
	COMPANY_NAME_EXISTS("Company with such a name exists already.Company name has to be unique"),
	COMPANY_EMAIL_EXISTS("Company with such an email exists already.Company email has to be unique"),
	CLIENT_ID_DOES_NOT_EXIST("Unauthorized access attempt :There is no client with such an Id! "),
	CUSTOMER_ID_DOES_NOT_EXIST("Customer with such an id does not exist"),
	COMPANY_ID_DOES_NOT_EXIST("Company with such an id does not exist"),
	COMPANY_IS_INACTIVE("This company is inactive already"),
	CUSTOMER_IS_INACTIVE("This customer is inactive already"),
	CUSTOMER_EMAIL_EXISTS("Customer with such an email exists already.Cannot add ");
	
	private String message;

	private ErrorMessages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
	

}
