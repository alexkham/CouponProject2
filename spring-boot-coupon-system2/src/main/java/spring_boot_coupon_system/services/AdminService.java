package spring_boot_coupon_system.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import spring_boot_coupon_system.exceptions.ErrorMessages;

import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.exceptions.CouponSystemException;



@Service
public class AdminService extends ClientService {

	@Override
	public boolean login(String email, String password) {
		return false;
	}
	
	public int addCompany(Company company) {
		return 0;
		
	}
	
	public void updateCompany(Company company) { 
		
	}
	
	public void deleteCompany(int companyId) {
		
	}
	
	public ArrayList<Company> getAllCompanies(){
		return null;
		
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
	
	public void deleteCustomer(int customerId) {
		
	}
	
	public ArrayList<Customer> getAllCustomers(){
		return null;
		
	}
	
	public Customer getOneCustomer(Long customerId) throws CouponSystemException {
		
		//Both validating and delivering data
				return customerRepository
						.findById(customerId)
						.orElseThrow(()->new CouponSystemException
							(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST));
				
				
		
	}

}
