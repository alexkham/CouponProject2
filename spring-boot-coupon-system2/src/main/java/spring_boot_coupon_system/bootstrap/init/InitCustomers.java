package spring_boot_coupon_system.bootstrap.init;

import java.util.ArrayList;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.bootstrap.TestUtils;
import spring_boot_coupon_system.bootstrap.TestUtilsGraphics;
import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.repositories.CustomerRepository;

/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Component
@Order(1)
public class InitCustomers implements CommandLineRunner{
	
	public static int  customersCapacity=10;
	public static List<Customer> customers=new ArrayList();
	
	@Autowired
	protected   CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(TestUtilsGraphics.CUSTOMERS);
		
		
		for(int i=0;i<customersCapacity;i++) 
			
			customers.add(TestUtils.createRandomCustomer());
			
			
		
		
		customerRepository.saveAll(customers);
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Tried to add "+customersCapacity+" customers");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println("Actually added "+customers.size()+" customers");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		System.out.println((customersCapacity==customers.size())?"Success":" Having a problem");
		System.out.println(TestUtils.simpleSeparator);
		System.out.println();
		
		customerRepository.findAll().forEach(System.out::println);
	}

}
