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
import spring_boot_coupon_system.bootstrap.init.InitCompanies;
import spring_boot_coupon_system.bootstrap.init.InitCoupons;
import spring_boot_coupon_system.bootstrap.init.InitCustomers;
import spring_boot_coupon_system.bootstrap.init.InitPurchases;

import spring_boot_coupon_system.bootstrap.TestUtils;
import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.entities.Purchase;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.repositories.CustomerRepository;
import spring_boot_coupon_system.services.CustomerService;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Component
@Order(6)
public class CustomersServiceTest extends ServiceTest implements CommandLineRunner {
	
	

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("\t Running CustomerService Test");
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
		 
		 //Generating id number from within existing range
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
		 
		 //Picking one of existing customers and turning him into inactive
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
		
		 
		 //Cleaning up after the test is done
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
		
		//Generating id number way out of the current existing range
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
		  
		  //Picking up one random customer out of the existing range 
		  Customer existingCustomer=customerRepository
				  .findAll()
				  .get(TestUtils.random.nextInt(InitCustomers.customersCapacity));
		  //Extracting existing id
		  Long existId=existingCustomer.getId();
		  //Invoking the method
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
		   //Turning the customer into inactive and saving
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
		   //Cleaning up after the test is done
		   existingCustomer.setIsActive(true);
		   customerRepository.save(existingCustomer);
				
	}
	
	
	@Test
	@Transactional
	private void testCustomerLoginMethod() throws CouponSystemException{
		
		System.out.println("\t Testing Login method with wrong credentials");
		//Creating new  (unsaved,non-existing) customer
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
		//Saving new customer and getting the generated id
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
		
		//Generating random id from the existing range and invoking the method
		Long clientId = TestUtils.random.nextInt(InitCustomers.customersCapacity/3)+1l;
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
		//Checking all coupons are active
		for(Coupon coupon:customerCoupons) {
			if(!coupon.getIsActive())
				System.out.println("\t One or more of customer coupons are inactive.Test failed");
		
		}
		
		System.out.println("\t Test success: all customer coupons are active");
		
		//Turning half of the customer  coupons into inactive
		for(int i=0;i<customerCoupons.size();i++)
		
		      if(i%2==0) {
		    	  customerCoupons.get(i).setIsActive(false);
		    	  
		      }
		//Saving and invoking the method again
		couponRepository.saveAll(customerCoupons);
		List<Coupon>customerCoupons2=customerService.getCustomerCoupons(clientId);
	    int amountOfCoupons2=customerCoupons2.size();
	    //Checking again:supposed to be half of the previous test result
		
		 try {
			   
			   assertEquals( InitPurchases.purchaseCapacity/2,amountOfCoupons2);
			   System.out.println("\t"+TestUtils.simpleSeparator);
			   System.out.println("\t Inactive coupons were filtered : Success");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		 
		 
		 //Cleaning up after the test is done
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
		
		//Picking up random category out of the existing
		Category category=categoryRepository.findById
				(TestUtils.random.nextInt(3)+1l).get();
		//Invoking the method for random customer out of the existing
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
		
		//Checking all coupons belong to the same specified category
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
		//Generating random double value
		double maxUnitPrice= TestUtils.random.nextDouble()*100;
		//Picking random customer id from the range of existing and invoking the method 
		Long clientId=TestUtils.random.nextInt(InitCustomers.customersCapacity/2)+1l;;
		
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
		
		//Checking that no coupons with unit price exceeding the max price specified
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
		
		//Creating new still unsaved coupon
		Coupon nonExistingCoupon=TestUtils
				.createRandomCoupon
				(companyService
				.getCompanyDetails
				(TestUtils.random.nextInt
				(InitCompanies.companiesCapacity/2)+1l), 
				categoryRepository
				.findById(TestUtils.random.nextInt(InitCategories.categoriesCapacity-2)+1l).get());
		
		nonExistingCoupon.setId(1000l);
		//Getting random client id from within existing range
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
		//Turning new coupon to an inactive and saving it
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
		
		//Picking one random coupon from the existing
		Coupon purchasedCoupon=customerService
				.getCustomerCoupons(clientId)
				.get(TestUtils.random.nextInt(InitCoupons.couponCapacity/2));
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
		//Turning the coupon from previous check back into active and setting its quantity in stock to zero 
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
		//The coupon from previous check back in stock but expiration date is today(means its expired)
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
		//Setting back expiration date to a  date in the future
		expiredCoupon.setEndDate(TestUtils.createFuturePastDate(1, 1, 1));
		Coupon couponToPurchase=couponRepository.save(expiredCoupon);
		Long purchaseId=customerService.purchaseCoupon(clientId, couponToPurchase);
		Long couponToPurchaseId=couponToPurchase.getId();
		Coupon actualCouponPurchased=customerService
				.getCustomerCoupons(clientId)
				.stream()
				.filter(c->c.getId()==couponToPurchaseId)
				.collect(Collectors.toList())
				.get(0);
		Purchase actualPurchase=purchaseRepository.findByCouponId(couponToPurchaseId).get(0);
				
		try {
			
			assertNotNull(actualCouponPurchased);
			assertNotNull(actualPurchase);
			
			assertEquals(actualCouponPurchased.getTitle(),actualPurchase.getCoupon().getTitle() );
			assertEquals(actualCouponPurchased.getDescription(),actualPurchase.getCoupon().getDescription() );
			assertEquals(actualCouponPurchased.getQuantity(),actualPurchase.getCoupon().getQuantity() );
					
			System.out.println("\t Passed successfully:new purchase have been created ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
	}

}
