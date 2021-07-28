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
	
	protected List<Category> categories=new ArrayList<>();
	
	@Autowired
	protected CategoryRepository categoryRepository;
	
	

	@Override
	public void run(String... args) throws Exception {
		
		
		System.out.println(TestUtilsGraphics.CATEGORIES);
		
		
		/*for(int i=0;i<categoriesCapacity;i++)
			
			categories.add(TestUtils.createRandomCategory());
		
		
		
		categoryRepository.saveAll(categories);*/
		
		/*Category category=Category.builder()
				.categoryName("First category")
				.build();
		
		categoryRepository.save(category);*/
		
		for(int i=0;i<categoriesCapacity;i++) {
			
			
			categories.add(Category.builder().categoryName(TestUtils.createRandomString(7)).build());
		}
		
		categoryRepository.saveAll(categories);
	}

}
