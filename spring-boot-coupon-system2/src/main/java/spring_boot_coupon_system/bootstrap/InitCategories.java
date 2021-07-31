package spring_boot_coupon_system.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.repositories.CategoryRepository;
import spring_boot_coupon_system.repositories.CouponRepository;

@Component
@Order(3)
public class InitCategories implements CommandLineRunner {
    
	
	protected static int categoriesCapacity=10;
	
	protected static List<Category> categories=new ArrayList<>();
	
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