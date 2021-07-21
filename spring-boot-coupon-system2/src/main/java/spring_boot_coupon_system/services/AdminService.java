package spring_boot_coupon_system.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import spring_boot_coupon_system.exceptions.ErrorMessages;

import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.exceptions.CouponSystemException;



@Service
public class AdminService extends ClientService {

	private static final String ADMIN_PASSWORD = "admin";
	private static final String ADMIN_EMAIL = "admin@admin.com";
	

	@Override
	public boolean login(String email, String password) {
		return email.equalsIgnoreCase(ADMIN_EMAIL)&&password.equals(ADMIN_PASSWORD);
	}
	
	
	
	public void addCompany(Company company) throws CouponSystemException {
		
		if(companyRepository.existsByName(company.getName()))
			throw new CouponSystemException(ErrorMessages.COMPANY_NAME_EXISTS);
		if (companyRepository.existsByEmail(company.getEmail()))
			throw new CouponSystemException(ErrorMessages.COMPANY_EMAIL_EXISTS);
		
		
		companyRepository.save(company);
		
	}
	
	public void updateCompany(Company company) { 
		
	}
	
	public void deleteCompany(Long companyId) {
		
	}
	
	public List<Company> getAllCompanies(){
		
		return companyRepository.findAll();
		
	}
	
	public Company getOneCompany(Long companyId) throws CouponSystemException {
		//Checks the argument validity and creates new object in one piece of action
   		return companyRepository
   				.findById(companyId)
   				.orElseThrow(()->new CouponSystemException
   						(ErrorMessages.COMPANY_ID_DOES_NOT_EXIST));
   		
		
				
	}
	
	public int addCustomer(Customer customer) {
		return 0; 
		
	}
	
	public void updateCustomer(Customer customer) {
		
	}
	
	public void deleteCustomer(Long customerId) {
		
	}
	
	public List<Customer> getAllCustomers(){
		
		return customerRepository.findAll();
		
	}
	
	public Customer getOneCustomer(Long customerId) throws CouponSystemException {
		
		//Both validating and delivering data
				return customerRepository
						.findById(customerId)
						.orElseThrow(()->new CouponSystemException
							(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST));
				
				
		
	}

}
