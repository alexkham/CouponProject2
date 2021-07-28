package spring_boot_coupon_system.services;

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



@Service
public class AdminService extends ClientService {

	private static final String ADMIN_PASSWORD = "admin";
	private static final String ADMIN_EMAIL = "admin@admin.com";
	

	public boolean login(String email, String password) {
		return email.equalsIgnoreCase(ADMIN_EMAIL)
				&&password.equals(ADMIN_PASSWORD);
	}
	
	
	
	public void addCompany(Company company) throws CouponSystemException {
		
		Company companyByName=companyRepository.findByName(company.getName());
		Company companyByEmail=companyRepository.findByEmail(company.getEmail());
		
		if(companyByName!=null&&companyByName.getIsActive())
			throw new CouponSystemException(ErrorMessages.COMPANY_NAME_EXISTS);
		if (companyByEmail!=null&&companyByEmail.getIsActive())
			throw new CouponSystemException(ErrorMessages.COMPANY_EMAIL_EXISTS);
		
		
		companyRepository.save(company);
		
	}
	
	public void updateCompany(Company company) throws CouponSystemException {
		
		Long companyId=company.getCompanyId();
		
		Company companyToUpdate= getOneCompany(companyId);
   		
   		if(!company.getName().equals(companyToUpdate.getName()))
   			throw new CouponSystemException(ErrorMessages.CAN_NOT_UPDATE_COMPANY_NAME);
   		
   		companyRepository.save(company);
		
		
	}
	
	
	@Transactional
	public void deleteCompany(Long companyId) throws CouponSystemException {
		//Checks the argument validity and creates new object in one piece of action
   		 Company company=getOneCompany(companyId);
   		 
   		company.setIsActive(false);
   		
   		   		 
		companyRepository.save(company);
		
		List<Coupon> companyCoupons=company.getCompanyCoupons();
		
		for(Coupon coupon:companyCoupons)
			coupon.setIsActive(false);
		
		couponRepository.saveAll(companyCoupons);
		
		//List<Purchase> companyPurchases=purchaseRepository.findByCompany(company);
		
		//for(Purchase purchase:companyPurchases)
		//	purchase.setActive(false);
		
		//purchaseRepository.saveAll(companyPurchases);
		
	}
	
	
	//TODO
	public List<Company> getAllCompanies(){
		
		
		//return companyRepository.findByActiveTrue();
		
		return companyRepository.findAll().stream().filter(c->c.getIsActive()).collect(Collectors.toList());
		
	}
	
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
	
	public void addCustomer(Customer customer) throws CouponSystemException {
		
		Customer customerByEmail=customerRepository.findByEmail(customer.getEmail());
		
		if(customerByEmail!=null&&customerByEmail.getIsActive())
			throw new CouponSystemException(ErrorMessages.CUSTOMER_EMAIL_EXISTS);
		
		customerRepository.save(customer);
		
		
		
	}
	
	public void updateCustomer(Customer customer) throws CouponSystemException {
		
		Long customerId=customer.getId();
		
		
		 Customer customerToUpdate=getOneCustomer(customerId);
		 
		 customer.setIsActive(true);
		
		customerRepository.save(customer);
		
	}
	
	@Transactional
	public void deleteCustomer(Long customerId) throws CouponSystemException {
		//Both validating and delivering data
		 Customer customer=getOneCustomer(customerId);
		 
		 customer.setIsActive(false);
		 
		 customerRepository.save(customer);
		
	}
	
	
	public List<Customer> getAllCustomers(){
		
		return customerRepository.findByIsActiveTrue();
		
	}
	
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
