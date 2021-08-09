package spring_boot_coupon_system.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.bootstrap.init.InitCoupons;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Purchase;
import spring_boot_coupon_system.repositories.PurchaseRepository;
@Component
@Order(5)
public class InitPurchases implements CommandLineRunner {
	
    public static int purchaseCapacity=10;
	
	protected static List<Purchase> purchases=new ArrayList<>();
	
	@Autowired
	protected PurchaseRepository purchaseRepository;
	

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(TestUtilsGraphics.PURCHASES);
		
		for(int i=0;i<InitCustomers.customers.size();i++)
			for(int j=0;j<purchaseCapacity;j++) {
				purchases.add(Purchase.builder()
					.customer(InitCustomers.customers.get(i))
					.coupon(InitCoupons.coupons.get(i*10+j))
					.purchaseDate(TestUtils.now)
					.isActive(true)
					.build());
				}
		
		purchaseRepository.saveAll(purchases);
		
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Tried to add "+purchaseCapacity*InitCustomers.customersCapacity+" purchases");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Actually added "+purchases.size()+" coupons");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println
		((purchaseCapacity*InitCustomers.customersCapacity==purchases.size())?"Success":" Having a problem");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		
		
		purchaseRepository.findAll().forEach(System.out::println);
		
	}

}
