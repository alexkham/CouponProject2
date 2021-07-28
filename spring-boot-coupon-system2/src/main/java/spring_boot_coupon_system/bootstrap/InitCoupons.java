package spring_boot_coupon_system.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.repositories.CouponRepository;
//@Component
//@Order(4)
public class InitCoupons implements CommandLineRunner {
	
	protected static int couponCapacity=10;
	
	protected List<Coupon> coupons=new ArrayList<>();
	
	@Autowired
	protected CouponRepository couponRepository;
	

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(TestUtilsGraphics.COUPONS);
		
	/*	for(int i=0;i<InitCompanies.companies.size();i++) {
			
			coupons.addAll(TestUtils.createCompanyCoupons(couponCapacity, InitCompanies.companies.get(i)));
			
		
		}
		
		couponRepository.saveAll(coupons);
		
		
		System.out.println("Tried to add "+couponCapacity*InitCompanies.companiesCapacity+" coupons");
		System.out.println("Actually added "+coupons.size()+" coupons");
		System.out.println((couponCapacity==coupons.size())?"Success":" Having a problem");
		
		
		couponRepository.findAll().forEach(System.out::println);
		
	//	TestUtils.createRandomCoupon(InitCompanies.companies.get(0));
		
		/*Coupon coupon=Coupon.builder()
				.category(TestUtils.createRandomCategory())
				.company(InitCompanies.companies.get(1))
				.description(TestUtils.createRandomString(8))
				.build();*/
		
	/*	Coupon coupon=new Coupon();
		coupon.setCompany(InitCompanies.companies.get(1));
		coupon.setTitle("First coupon");
		coupon.setQuantity(7);
		
		couponRepository.save(coupon);*/
		
		couponRepository.save(TestUtils.createRandomCoupon(InitCompanies.companies.get(1)));
		
		
		
	}

}
