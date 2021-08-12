package spring_boot_coupon_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.repositories.CategoryRepository;
import spring_boot_coupon_system.repositories.CompanyRepository;
import spring_boot_coupon_system.repositories.CouponRepository;
import spring_boot_coupon_system.repositories.CustomerRepository;
import spring_boot_coupon_system.repositories.PurchaseRepository;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Service
public abstract class ClientService {
	
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired
	protected CategoryRepository categoryRepository;
	
	@Autowired
	protected PurchaseRepository purchaseRepository;
	
	
	


}
