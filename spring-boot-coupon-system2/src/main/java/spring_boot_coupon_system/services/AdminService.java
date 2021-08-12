package spring_boot_coupon_system.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


import spring_boot_coupon_system.exceptions.ErrorMessages;

import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.entities.Purchase;
import spring_boot_coupon_system.exceptions.CouponSystemException;

/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */

@Service
public class AdminService extends ClientService {

	private static final String ADMIN_PASSWORD = "admin";
	private static final String ADMIN_EMAIL = "admin@admin.com";
	

	
	/**
	 * 
	 * Verifies users credentials (admin email and password)
	 * @param email String 
	 * @param password String
	 * @return true-if exists row containing specified email and password,otherwise-false
	 * @throws CouponSystemException
	 */
	public boolean login(String email, String password) throws CouponSystemException {
		
		if(!email.equalsIgnoreCase(ADMIN_EMAIL)&&!password.equals(ADMIN_PASSWORD))
				throw new CouponSystemException(ErrorMessages.UNATHORIZED_ACCES_ATTEMPT);
		
		return true;
	}
	
	
	
	/**
	 * Adds a company received as a parameter to respective table in the database after verifying  its properties
	 * @param company Object of type Company
	 * @return Long -the Id Number of newly  added company as it shows in the database
	 * @throws CouponSystemException
	 */
	public Long addCompany(Company company) throws CouponSystemException {
		
		Company companyByName=companyRepository.findByName(company.getName());
		Company companyByEmail=companyRepository.findByEmail(company.getEmail());
		
		if(companyByName!=null&&companyByName.getIsActive())
			throw new CouponSystemException(ErrorMessages.COMPANY_NAME_EXISTS);
		if (companyByEmail!=null&&companyByEmail.getIsActive())
			throw new CouponSystemException(ErrorMessages.COMPANY_EMAIL_EXISTS);
		
		
		return companyRepository.save(company).getCompanyId();
		
	}
	
	
	/**
	 * Performs update of an existing record in the database by overwriting  respective values of it with 
	 * the data received as a parameter
	 * @param company Object of type Company
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		
		Long companyId=company.getCompanyId();
		
		Company companyToUpdate= getOneCompany(companyId);
   		
   		if(!company.getName().equals(companyToUpdate.getName()))
   			throw new CouponSystemException(ErrorMessages.CAN_NOT_UPDATE_COMPANY_NAME);
   		
   		companyRepository.save(company);
		
		
	}
	
	/**
	 * Turns one company record with specified id to "inactive "
	 * @param companyId Id number(int) of company to be deleted(deactivated)
	 * @throws CouponSystemException
	 */
	@Transactional
	public void deleteCompany(Long companyId) throws CouponSystemException {
		
		//Checks the argument validity and creates new object in one piece of action
		Company company= companyRepository
   				.findById(companyId)
   				.orElseThrow(()->new CouponSystemException
   						(ErrorMessages.COMPANY_ID_DOES_NOT_EXIST));
   		if(!company.getIsActive())
   			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
   		 
   		company.setIsActive(false);
   		
   		   		 
		companyRepository.save(company);
		
		List<Coupon> companyCoupons=couponRepository.findByCompanyCompanyId(companyId);
		
		for(Coupon coupon:companyCoupons)
			coupon.setIsActive(false);
		
		couponRepository.saveAll(companyCoupons);
		
	
	}
	
	
	/**
	 * Returns the list of all companies in the database
	 * @return List of objects of type Company
	 */
	public List<Company> getAllCompanies(){
		
		
		return companyRepository.findByIsActiveTrue();
		
		
	}
	
	
	/**
	 * Returns an object of type Company with an id value equal to the received as an argument
	 * @param companyId Long value of id number 
	 * @return An object of type Company
	 * @throws CouponSystemException
	 */
	public Company getOneCompany(Long companyId) throws CouponSystemException {
		//Checks the argument validity and creates new object in one piece of action
   		Company company= companyRepository
   				.findById(companyId)
   				.orElseThrow(()->new CouponSystemException
   						(ErrorMessages.COMPANY_ID_DOES_NOT_EXIST));
   		if(!company.getIsActive())
   			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
   		
		return company;
   		
		
				
	}
	
	
	/**
	 * Adds a new customer to the database
	 * @param customer Object of type Customer
	 * @return Newly created object of type Customer
	 * @throws CouponSystemException
	 */
	public Customer addCustomer(Customer customer) throws CouponSystemException {
		
		Customer customerByEmail=customerRepository.findByEmail(customer.getEmail());
		
		if(customerByEmail!=null&&customerByEmail.getIsActive())
			throw new CouponSystemException(ErrorMessages.CUSTOMER_EMAIL_EXISTS);
		
		return customerRepository.save(customer);
		
		
		
	}
	
	/**
	 * Updates values of the specified object  after verifying that such customer exists in the database
	 * @param customer Object of type Customer
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		
		
		Long customerId=customer.getId();
		
		//Will validate customer by Id(if both existing and active) and if not -proper exceptions will be thrown
		Customer customerToUpdate= getOneCustomer(customerId);
		
		//Once passes validation- no reason not to update
		customerRepository.save(customer);
		
	}
	
	/**
	 * Deletes (turns into inactive state) a record of customer with respective id number in the database
	 * @param customerId Integer value representing id of the customer
	 * @throws CouponSystemException
	 */
	@Transactional
	public void deleteCustomer(Long customerId) throws CouponSystemException {
		//Both validating and delivering data
		Customer customer= customerRepository
				.findById(customerId)
				.orElseThrow(()->new CouponSystemException(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST));

         if(!customer.getIsActive())
	        throw new CouponSystemException(ErrorMessages.CUSTOMER_IS_INACTIVE);

		 
		 customer.setIsActive(false);
		 
		 customerRepository.save(customer);
		 
		 List<Purchase> customerPurchases=purchaseRepository.findByCustomerId(customerId);
		 for(Purchase purchase:customerPurchases)
			 purchase.setActive(false);
		 
		 purchaseRepository.saveAll(customerPurchases);
		
	}
	
	 /**
     * Extracts and returns a list of all customers in the database
     * @return List of customers
     */
	public List<Customer> getAllCustomers(){
		
		return customerRepository.findByIsActiveTrue();
		
	}
	
	/**
	 * Retrieves from the database and returns as  an object a record with id number specified
	 * 
	 * @param Long type customerId
	 * @return Object of type Customer specified by Id
	 * @throws CouponSystemException
	 */
	public Customer getOneCustomer(Long customerId) throws CouponSystemException {
		
		//Both validating and delivering data
		Customer customer= customerRepository
						.findById(customerId)
						.orElseThrow(()->new CouponSystemException(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST));
		
		if(!customer.getIsActive())
			throw new CouponSystemException(ErrorMessages.CUSTOMER_IS_INACTIVE);
		
		return customer;
				
				
		
	}

}
