package spring_boot_coupon_system.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.repositories.CompanyRepository;
@Component
@Order(2)
public class InitCompanies implements CommandLineRunner{
	
	public static  int companiesCapacity = 10;
	@Autowired
	protected CompanyRepository companyRepository;
	
	public static List<Company> companies=new ArrayList<>();
	

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(TestUtilsGraphics.COMPANIES);
		
		//List<Company> companies= new ArrayList<>();
		
		for(int i=0;i<companiesCapacity;i++) 
			
		     companies.add(TestUtils.createRandomCompanyNoCoupons());
		
		
		companyRepository.saveAll(companies);
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Tried to add "+companiesCapacity+" companies");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Actually added "+companies.size()+" companies");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println((companiesCapacity==companies.size())?"Success":" Having a problem");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		
		companyRepository.findAll().forEach(System.out::println);
		
		//companies.get(0).getCompanyCoupons().add(TestUtils.createRandomCoupon(companies.get(0)));
        		
	}

}
