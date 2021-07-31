package spring_boot_coupon_system.bootstrap;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.repositories.CouponRepository;
@Component
@Order(4)
public class InitCoupons implements CommandLineRunner {
	
	protected static int couponCapacity=10;
	
	public static  List<Coupon>coupons=new ArrayList<>();
	
	@Autowired
	protected CouponRepository couponRepository;
	

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(TestUtilsGraphics.COUPONS);
		
		for(int i=0;i<InitCompanies.companies.size();i++) {
			
			for(int j=0;j<couponCapacity;j++)
				
			coupons.add(TestUtils.createRandomCoupon(InitCompanies.companies.get(i), InitCategories.categories.get(new Random().nextInt(10))));
			
		
		}
		
		couponRepository.saveAll(coupons);
		
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Tried to add "+couponCapacity*InitCompanies.companiesCapacity+" coupons");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Actually added "+coupons.size()+" coupons");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println
		((couponCapacity*InitCompanies.companiesCapacity==coupons.size())?"Success":" Having a problem");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		
		
		couponRepository.findAll().forEach(System.out::println);
		
		
		
	}

}
