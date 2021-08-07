package spring_boot_coupon_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import spring_boot_coupon_system.bootstrap.InitCustomers;
import spring_boot_coupon_system.services.CompanyService;

@SpringBootApplication
@EnableScheduling
public class SpringBootCouponSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(SpringBootCouponSystemApplication.class, args);
		
		CompanyService cs=(CompanyService)context.getBean(CompanyService.class);
		
		System.out.println("\t Application started OK!");
		
		
		
	}

}
