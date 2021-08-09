package spring_boot_coupon_system.bootstrap.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.util.Duration;

import java.lang.reflect.Method;
import java.sql.Date;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import spring_boot_coupon_system.bootstrap.init.InitCategories;
import spring_boot_coupon_system.bootstrap.InitCompanies;
import spring_boot_coupon_system.bootstrap.InitCustomers;
import spring_boot_coupon_system.bootstrap.InitPurchases;

import spring_boot_coupon_system.bootstrap.TestUtils;
import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.repositories.CustomerRepository;
import spring_boot_coupon_system.services.CustomerService;

@Component
@Order(6)
public class CustomersServiceTest extends ServiceTest implements CommandLineRunner {
	
	

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Running CustomerService Test");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		testValidateCustomerMethod();
		
		testCustomerLoginMethod();
		
        testGetCustomersDetails();
       
        testGetCustomersCoupons();
        
        testGetCustomerCouponsByCategory();
        
        testGetCustomerCouponsByMaxPrice();
        
        testPurchaseCouponMethod();
       
        
        
        
      
		
	}
	
	@Test
	@Transactional
	private void testValidateCustomerMethod() throws CouponSystemException {

		 System.out.println("\t Testing validateCustomer method");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		 

		 System.out.println("\t Testing validateCustomer method with wrong (non-existing) Id");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		 Long nonExistingId= TestUtils.random.nextInt(InitCustomers.customersCapacity)+10000l;
		 
		 try {
				assertThrows(CouponSystemException.class,()->customerService.validateCustomer(nonExistingId));
						
				System.out.println("\t Passed successfully: proper exception has been thrown ");
				
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		
		 

		 System.out.println("\t Testing validateCustomer method for inactive customer");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		 
		 Customer existingCustomer=customerService
				 .getCustomerDetails(TestUtils.random.nextInt(InitCustomers.customersCapacity)+1l);
		 existingCustomer.setIsActive(false);
		 Long inactiveCustomerId= customerRepository.save(existingCustomer).getId();
		 
		
		 try {
				assertThrows(CouponSystemException.class,()->customerService.validateCustomer(inactiveCustomerId));
						
				System.out.println("\t Passed successfully: proper exception has been thrown ");
				
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		
		 existingCustomer.setIsActive(true);
		 customerRepository.save(existingCustomer);
		 
	}
	
	@Test
	@Transactional
	private void testGetCustomersDetails() throws CouponSystemException{
		
		System.out.println("\t Testing getCustomerDetails method");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		
		System.out.println("\t Trying to get customer details with non existing Id number");
		
		Long nonExistId=10000l;
		
		  try {
		    	
			    assertThrows(CouponSystemException.class, ()->customerService.getCustomerDetails(nonExistId));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		  
		  System.out.println("\t Trying to get customer details with existing Id number");
		  System.out.println("\t"+TestUtils.simpleSeparator);
		  
		  Customer existingCustomer=customerRepository
				  .findAll()
				  .get(TestUtils.random.nextInt(InitCustomers.customersCapacity));
		  
		  Long existId=existingCustomer.getId();
		  
		  Customer actualCustomer=customerService.getCustomerDetails(existId);
		
		   try {
			   assertNotNull(actualCustomer);
			   assertEquals(existingCustomer.getId(), actualCustomer.getId());
			   assertEquals(existingCustomer.getFirstName(), actualCustomer.getFirstName());
			   assertEquals(existingCustomer.getLastName(), actualCustomer.getLastName());
			   assertEquals(existingCustomer.getEmail(), actualCustomer.getEmail());
			   assertEquals(existingCustomer.getPassword(), actualCustomer.getPassword());
			   System.out.println("\t Success");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		   System.out.println("\t Trying to get customer details of an inactive customer ");
		   
		   existingCustomer.setIsActive(false);
		   customerRepository.save(existingCustomer);
		   
		   try {
		    	
			    assertThrows(CouponSystemException.class, ()->customerService.getCustomerDetails(existingCustomer.getId()));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		   
		   existingCustomer.setIsActive(true);
		   customerRepository.save(existingCustomer);
				
	}
	
	
	@Test
	@Transactional
	private void testCustomerLoginMethod() throws CouponSystemException{
		
		System.out.println("\t Testing Login method with wrong credentials");
		
		Customer customer=TestUtils.createRandomCustomer();
		customer.setId(TestUtils.random.nextLong()+10l);
		
		try {
			assertThrows(CouponSystemException.class, 
					()->customerService.login(customer.getId(), customer.getEmail(),customer.getPassword()));
			System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
		System.out.println("\t Testing Login method with right credentials");
		
		customer.setId(null);
		Long customerId =customerRepository.save(customer).getId();
		
		try {
			assertTrue(customerService.login(customerId, customer.getEmail(), customer.getPassword()));
			System.out.println("\t Passed successfully! ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
	}
	
	
	@Test
	@Transactional
	private void testGetCustomersCoupons() throws CouponSystemException{
		
		System.out.println("\t Testing getCustomerCoupons method ");
		
		long clientId = TestUtils.random.nextInt(InitCustomers.customersCapacity/3)+1l;
		List<Coupon> customerCoupons=customerService.getCustomerCoupons(clientId);
		int amountOfCoupons=customerCoupons.size();
		
		 try {
			   
			   assertEquals(amountOfCoupons, InitPurchases.purchaseCapacity);
			   System.out.println("\t Coupons list of proper size  created : Success");
			   
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		
		for(Coupon coupon:customerCoupons) {
			if(!coupon.getIsActive())
				System.out.println("\t One or more of customer coupons are inactive.Test failed");
		
		}
		
		System.out.println("\t Test success: all customer coupons are active");
		
		for(int i=0;i<customerCoupons.size();i++)
		
		      if(i%2==0) {
		    	  customerCoupons.get(i).setIsActive(false);
		    	  
		      }
		
		couponRepository.saveAll(customerCoupons);
		customerCoupons=customerService.getCustomerCoupons(clientId);
	    amountOfCoupons=customerCoupons.size();
		
		 try {
			   
			   assertEquals( InitPurchases.purchaseCapacity/2,amountOfCoupons);
			   System.out.println("\t"+TestUtils.simpleSeparator);
			   System.out.println("\t Inactive coupons were filtered : Success");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		 
		 for(int i=0;i<customerCoupons.size();i++)
				
		      if(i%2==0) {
		    	  customerCoupons.get(i).setIsActive(true);
		    	  
		      }
		
		couponRepository.saveAll(customerCoupons);
		
	}
	
	@Test
	private void testGetCustomerCouponsByCategory() throws CouponSystemException {
		
		System.out.println("\t Testing getCustomerCouponsByCategory test");
		
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		Category category=categoryRepository.findById
				(TestUtils.random.nextInt(3)+1l).get();
		
		List<Coupon> customerCouponsByCategory=customerService
				.getCustomerCouponsByCategory
				(TestUtils.random.nextInt(InitCustomers.customersCapacity/2)+1l, category);
		
		int couponsQuantity=customerCouponsByCategory.size();
		
		try {
			assertTrue(couponsQuantity<=InitPurchases.purchaseCapacity);
			System.out.println("\t Passed successfully:coupon list of proper size created ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		for(Coupon coupon: customerCouponsByCategory) {
			
			if(coupon.getCategory().getId()!=category.getId()) {
				System.out.println("\t Test failed: not all coupons of the same category!");
			    System.out.println("\t"+TestUtils.warning);
		   }
			
		}
		
			System.out.println( " \t Test passed successfully : all coupons belong to the same category");
			System.out.println("\t"+TestUtils.starSeparator);
			System.out.println();
		
	}
	
	
	@Test
	private void testGetCustomerCouponsByMaxPrice() throws CouponSystemException {
		
		System.out.println("\t Testing getCustomerCouponsByMaxPrice method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		double maxUnitPrice= TestUtils.random.nextDouble()*100;
		
		Long clientId=7l;
		
		List <Coupon> customerCouponsByMaxPrice=customerService
				.getCustomerCouponsByMaxPrice(clientId, maxUnitPrice);
		
		try {
			assertTrue(customerCouponsByMaxPrice.size()<=InitPurchases.purchaseCapacity);
			System.out.println("\t Passed successfully:coupon list of proper size created ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		if(customerCouponsByMaxPrice.size()==0)
			System.out.println("\t  No coupons with unit price lower than specified");
		
		for(Coupon coupon:customerCouponsByMaxPrice) {
			
			if(coupon.getUnitPrice()>maxUnitPrice) {
				System.out.println("\t Test failed: not all coupons under specified prise!");
			    System.out.println("\t"+TestUtils.warning);
		      }
		
		
		}
		

			
			System.out.println( " \t Test passed successfully : all coupons unit prices are  below specified");
			System.out.println("\t"+TestUtils.starSeparator);
			System.out.println();
		
		
	}
	
	
	@Test
	@Transactional
	private void testPurchaseCouponMethod() throws CouponSystemException {
		
		System.out.println( " \t Testing purchaseCoupon method");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		
		System.out.println( " \t Testing purchaseCoupon method with non-existing coupon");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		Coupon nonExistingCoupon=TestUtils
				.createRandomCoupon
				(companyService
				.getCompanyDetails
				(TestUtils.random.nextInt
				(InitCompanies.companiesCapacity/2)+1l), 
				categoryRepository
				.findById(TestUtils.random.nextInt(InitCategories.categoriesCapacity-2)+1l).get());
		
		nonExistingCoupon.setId(1000l);
		
		Long clientId= TestUtils.random.nextInt(InitCustomers.customersCapacity/2)+1l;
		
		 try {
				assertThrows
				(CouponSystemException.class,()->customerService.purchaseCoupon(clientId,nonExistingCoupon));
						
				System.out.println("\t Passed successfully: proper exception has been thrown ");
				
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		
		
		System.out.println( " \t Testing purchaseCoupon method with inactive coupon");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		nonExistingCoupon.setIsActive(false);
		Coupon inactiveCoupon=couponRepository.save(nonExistingCoupon);
		
		try {
			assertThrows
			(CouponSystemException.class,()->customerService.purchaseCoupon(clientId,inactiveCoupon));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println( " \t Testing purchaseCoupon method with coupon purchased already");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		
		Coupon purchasedCoupon=customerService
				.getCustomerCoupons(clientId)
				.get(7);
		 try {
				assertThrows
				(CouponSystemException.class,()->customerService.purchaseCoupon(clientId,purchasedCoupon));
						
				System.out.println("\t Passed successfully: proper exception has been thrown ");
				
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		
		
		System.out.println( " \t Testing purchaseCoupon method with coupon out of stock");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		
		inactiveCoupon.setIsActive(true);
		inactiveCoupon.setQuantity(0);
		Coupon couponOutOfStock=couponRepository.save(inactiveCoupon);
		
		try {
			assertThrows
			(CouponSystemException.class,()->customerService.purchaseCoupon(clientId,couponOutOfStock));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println( " \t Testing purchaseCoupon method with expired coupon");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		Date now=new Date(System.currentTimeMillis());
		
		couponOutOfStock.setQuantity(10);
		couponOutOfStock.setEndDate(now);
		Coupon expiredCoupon=couponRepository.save(couponOutOfStock);
		
		try {
			assertThrows
			(CouponSystemException.class,()->customerService.purchaseCoupon(clientId,expiredCoupon));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println( " \t Testing purchaseCoupon method with good coupon");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		
	/*	expiredCoupon.setEndDate(new Date(System.currentTimeMillis()+31536000));
		Coupon couponToPurchase=couponRepository.save(expiredCoupon);
		customerService.purchaseCoupon(clientId, couponToPurchase);
		Long couponToPurchaseId=couponToPurchase.getId();
		Coupon actualCouponPurchased=customerService
				.getCustomerCoupons(clientId)
				.stream()
				.filter(c->c.getId()==couponToPurchaseId)
				.collect(Collectors.toList())
				.get(0);
				
		try {
			
			assertNotNull(actualCouponPurchased);
					
			System.out.println("\t Passed successfully ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}*/
		
		
	}

}
