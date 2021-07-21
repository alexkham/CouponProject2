package spring_boot_coupon_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring_boot_coupon_system.repositories.CompanyRepository;
import spring_boot_coupon_system.repositories.CouponRepository;
import spring_boot_coupon_system.repositories.CustomerRepository;

@Service
public abstract class ClientService {
	
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	
	public abstract boolean login(String email,String password);
	


}
