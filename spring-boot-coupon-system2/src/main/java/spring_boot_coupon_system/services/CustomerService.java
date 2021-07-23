package spring_boot_coupon_system.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Coupon;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.exceptions.ErrorMessages;

@Service
public class CustomerService extends ClientService implements ClientLoginService{
	
	/*public void addNewCustomer(Customer customer) {
		customerRepository.save(customer);
	}*/
	@Override
	public boolean login(Long clientId, String email, String password) throws CouponSystemException {
		
		//Both client validation and check for entity
		Customer customer= customerRepository
				.findById(clientId)
				.orElseThrow(()->new CouponSystemException(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST));
		
		if(!customer.getIsActive())
			throw new CouponSystemException(ErrorMessages.CUSTOMER_IS_INACTIVE);
		
		String customerEmail=customer.getEmail().toLowerCase().trim();
		String customerPassword=customer.getPassword();
				
		
		return customerEmail.equalsIgnoreCase(email.toLowerCase().trim())
				&&customerPassword.equals(password);
	}
	
	

	
	public  int  purchaseCoupon(Coupon coupon) {
		return 0;
		
	}
	
	
	public List<Coupon> getCustomerCoupons(Long clientId) throws CouponSystemException{
		
		if(!customerRepository.existsById(clientId))
			throw new CouponSystemException(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST);
		
		List<Coupon> couponsByCustomerId = couponRepository.findByCustomerId(clientId);
		
		
		return couponsByCustomerId;
		
	}
	
	public List<Coupon> getCustomerCouponsByCategory(Long clientId ,Category category) throws CouponSystemException{
		
		if(!customerRepository.existsById(clientId))
			throw new CouponSystemException(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST);
		
		Long categoryId=category.getId();
		
		List<Coupon> couponsByCustomerIdAndCategory = couponRepository.findByCustomerIdAndCategory(clientId,categoryId);
		
		
		return couponsByCustomerIdAndCategory;
		
	}
	
	public List<Coupon> getCustomerCouponsByMaxPrice(Long clientId, double maxPrice) throws CouponSystemException{
		
		if(!customerRepository.existsById(clientId))
			throw new CouponSystemException(ErrorMessages.CLIENT_ID_DOES_NOT_EXIST);
		
		List<Coupon> couponsByMaxPrice = couponRepository.findByCustomerIdAndUnitPriceLessThan(clientId,maxPrice);
		return couponsByMaxPrice;
		
	}
	
	public Customer getCustomerDetails(Long clientId) throws CouponSystemException {
		
		
		//Both client validation and check for entity
		
		Customer customer= customerRepository
				.findById(clientId)
				.orElseThrow(()->new CouponSystemException(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST));
		
		if(!customer.getIsActive())
			throw new CouponSystemException(ErrorMessages.CUSTOMER_IS_INACTIVE);
		
		
		return customer;
		
		
		
	}

	
}
