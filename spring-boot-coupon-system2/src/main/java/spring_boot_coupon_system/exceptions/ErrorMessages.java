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
	CUSTOMER_EMAIL_EXISTS("Customer with such an email exists already.Cannot add "),
	COUPON_IS_INACTIVE("Coupon specified is inactive already"),
	COUPON_DOES_NOT_EXIST("Coupon specified does not exist"), 
	COUPON_TITLE_EXISTS("Coupon with such a title exists already. Title has to be unique"),
	CAN_NOT_UPDATE_COMPANY_NAME("Company name isn't changable ,can not update"), 
	COUPON_PURCHASED_BY_CUSTOMER("This coupon has been already purchased by customer."+
	 "Can not purchase same coupon more than once"), 
	COUPON_IS_NOT_IN_STOCK("This coupon currently isn't in stock and can not be purchased "), 
	COUPON_EXPIRED("This coupon expired and can not be purchased"), 
	UNATHORIZED_ACCES_ATTEMPT("Unathorized access attempt:the credentials provided do not allow to perform that action"),
	COUPON_ALREADY_EXISTS("Such coupon exists already in the database,can not add duplicates");
	
	private String message;

	private ErrorMessages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
	

}
