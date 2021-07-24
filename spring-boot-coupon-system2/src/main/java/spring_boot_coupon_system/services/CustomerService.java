package spring_boot_coupon_system.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Coupon;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.entities.Purchase;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.exceptions.ErrorMessages;
import spring_boot_coupon_system.repositories.PurchaseRepository;

@Service
public class CustomerService extends ClientService implements ClientLoginService{

	/*public void addNewCustomer(Customer customer) {
		customerRepository.save(customer);
	}*/
	@Override
	public boolean login(Long clientId, String email, String password) throws CouponSystemException {

		//Both client validation and check for entity
		Customer customer=validateCustomer(clientId);

		String customerEmail=customer.getEmail().toLowerCase().trim();
		String customerPassword=customer.getPassword();


		return customerEmail.equalsIgnoreCase(email.toLowerCase().trim())
				&&customerPassword.equals(password);
	}




	public  Long  purchaseCoupon(Long clientId,Coupon coupon) throws CouponSystemException {

		Customer customer= validateCustomer(clientId);
		
		Coupon actualCoupon=couponRepository.findById(coupon.getId())
				.orElseThrow(()->new CouponSystemException(ErrorMessages.COUPON_DOES_NOT_EXIST));
		if(!actualCoupon.getIsActive())
			throw new CouponSystemException(ErrorMessages.COUPON_IS_INACTIVE);

		List<Coupon> clientCoupons=getCustomerCoupons(clientId)
				.stream()
				.filter((c)->c.getId()==coupon.getId())
				.collect(Collectors.toList());
		if(!clientCoupons.isEmpty())
			throw new CouponSystemException(ErrorMessages.COUPON_PURCHASED_BY_CUSTOMER);
		
		if(actualCoupon.getQuantity()==0)
			throw new CouponSystemException(ErrorMessages.COUPON_IS_NOT_IN_STOCK);
		
		 Date now=new Date(System.currentTimeMillis());
		 
		if(actualCoupon.getEndDate().before(now)) 
			throw new CouponSystemException(ErrorMessages.COUPON_EXPIRED);
		
		Purchase purchase= Purchase.builder()
				.customer(customer)
				.coupon(actualCoupon)
				.purchaseDate(now)
				.build();
		
		
		return purchaseRepository.save(purchase).getId();

	}


	public List<Coupon> getCustomerCoupons(Long clientId) throws CouponSystemException{

		Customer customer=validateCustomer(clientId);

		List<Coupon> couponsByCustomerId = couponRepository.findByCustomerId(clientId);


		return couponsByCustomerId;

	}

	public List<Coupon> getCustomerCouponsByCategory(Long clientId ,Category category) throws CouponSystemException{

		Customer customer=validateCustomer(clientId);

		Long categoryId=category.getId();

		List<Coupon> couponsByCustomerIdAndCategory = couponRepository.findByCustomerIdAndCategory(clientId,categoryId);


		return couponsByCustomerIdAndCategory;

	}




	

	public List<Coupon> getCustomerCouponsByMaxPrice(Long clientId, double maxPrice) throws CouponSystemException{

		Customer customer=validateCustomer(clientId);

		List<Coupon> couponsByMaxPrice = couponRepository.findByCustomerIdAndUnitPriceLessThan(clientId,maxPrice);
		return couponsByMaxPrice;

	}

	public Customer getCustomerDetails(Long clientId) throws CouponSystemException {


		return validateCustomer(clientId);
		
		
	}
	
	
	private Customer validateCustomer(Long clientId) throws CouponSystemException {
		Customer customer= customerRepository
				.findById(clientId)
				.orElseThrow(()->new CouponSystemException(ErrorMessages.CUSTOMER_ID_DOES_NOT_EXIST));

		if(!customer.getIsActive())
			throw new CouponSystemException(ErrorMessages.CUSTOMER_IS_INACTIVE);
		
		return customer;
	}


}
