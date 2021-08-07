package spring_boot_coupon_system.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;

import spring_boot_coupon_system.repositories.CategoryRepository;
import spring_boot_coupon_system.repositories.CompanyRepository;
import spring_boot_coupon_system.repositories.CouponRepository;
import spring_boot_coupon_system.repositories.CustomerRepository;
import spring_boot_coupon_system.repositories.PurchaseRepository;
import spring_boot_coupon_system.services.AdminService;
import spring_boot_coupon_system.services.CompanyService;
import spring_boot_coupon_system.services.CustomerService;

public abstract class ServiceTest {
	
	@Autowired
	protected AdminService adminService;
	@Autowired
	protected CompanyService companyService;
	@Autowired
    protected CustomerService customerService;
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
