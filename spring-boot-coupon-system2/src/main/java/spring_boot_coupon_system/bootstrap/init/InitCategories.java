package spring_boot_coupon_system.bootstrap.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.bootstrap.TestUtils;
import spring_boot_coupon_system.bootstrap.TestUtilsGraphics;
import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.repositories.CategoryRepository;
import spring_boot_coupon_system.repositories.CouponRepository;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Component
@Order(3)
public class InitCategories implements CommandLineRunner {
    
	
	public static int categoriesCapacity=10;
	
	public static List<Category> categories=new ArrayList<>();
	
	@Autowired
	protected CategoryRepository categoryRepository;
	
	

	@Override
	public void run(String... args) throws Exception {
		
		
		System.out.println(TestUtilsGraphics.CATEGORIES);
		
		
		
		
		for(int i=0;i<categoriesCapacity;i++) 
			
			
			categories.add(Category.builder().categoryName(TestUtils.createRandomString(7)).build());
		
		
		categoryRepository.saveAll(categories);
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Tried to add "+categoriesCapacity+" categories");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Actually added "+categories.size()+" categories");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println((categoriesCapacity==categories.size())?"Success":" Having a problem");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		categoryRepository.findAll().forEach(System.out::println);
		
	}

}
