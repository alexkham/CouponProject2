package spring_boot_coupon_system.bootstrap.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.bootstrap.init.InitCategories;
import spring_boot_coupon_system.bootstrap.init.InitCompanies;
import spring_boot_coupon_system.bootstrap.init.InitCoupons;
import spring_boot_coupon_system.bootstrap.TestUtils;
import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Customer;
import spring_boot_coupon_system.entities.Purchase;
import spring_boot_coupon_system.exceptions.CouponSystemException;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Component
@Order(7)
public class CompanyServiceTest extends ServiceTest implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("\t Running CompanyService Test");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		testCompanyLoginMethod();
		testGetCompanyDetailsMethod();
		testIsCompanyExist();
		testUpdateCouponMethod();
		testGetCompanyCoupons();
		testGetCompanyCouponsByCategory();
		testGetCompanyCouponsByMaxPrice();
		testAddCouponMethod();
		testValidateCompanyMethod();
		testDeleteCouponMethod();
		
		
		
	}
	
	@Test
	@Transactional
	private void testValidateCompanyMethod() throws CouponSystemException {

		 System.out.println("\t Testing validateCompany method");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		 

		 System.out.println("\t Testing validateCompany method with wrong (non-existing) Id");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		 //Define an id number which does not exist for sure in the database
		 Long nonExistingId= TestUtils.random.nextInt(InitCompanies.companiesCapacity)+10000l;;
		 
		 try {
				assertThrows(CouponSystemException.class,()->companyService.validateCompany(nonExistingId));
						
				System.out.println("\t Passed successfully: proper exception has been thrown ");
				
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		
		 

		 System.out.println("\t Testing validateCompany method for inactive company");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		 //Picking up random company from the existing
		 Company existingCompany=companyService
				 .getCompanyDetails(TestUtils.random.nextInt(InitCompanies.companiesCapacity)+1l);
		 //Turning the company to inactive
		 existingCompany.setIsActive(false);
		 //Getting its id
		 Long inactiveCompanyId= companyRepository.save(existingCompany).getCompanyId();
		 
		
		 try {
				assertThrows(CouponSystemException.class,()->companyService.validateCompany(inactiveCompanyId));
						
				System.out.println("\t Passed successfully: proper exception has been thrown ");
				
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		//Cleaning up the slate at the end of the test
		 existingCompany.setIsActive(true);
		 companyRepository.save(existingCompany);
		
	}
	
	@Test
	@Transactional
	private void testCompanyLoginMethod() {
		
		 System.out.println("\t Testing CompanyLogin method");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		
        System.out.println("\t Testing Login method with wrong credentials");
        System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		//Creating new company which is still not in the database
		Company company=TestUtils.createRandomCompanyNoCoupons();
		company.setCompanyId(TestUtils.random.nextLong()+10l);
		
		try {
			assertThrows(CouponSystemException.class, 
					()->companyService.login(company.getCompanyId(), company.getEmail(),company.getPassword()));
			System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
		System.out.println("\t Testing Login method with right credentials");
		//Saving new company to the database and getting generated id
		company.setCompanyId(null);
		Long companyId =companyRepository.save(company).getCompanyId();
		
		try {
			assertTrue(companyService.login(companyId, company.getEmail(), company.getPassword()));
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
	private void testGetCompanyDetailsMethod() throws CouponSystemException {
		
		System.out.println("\t Testing getCompanyDetails method");
		System.out.println("\t"+TestUtils.starSeparator);
		System.out.println();
		
		System.out.println("\t Trying to get company details with non existing company Id number");
		//Generating id number which does not exist in the database at the moment for sure
		Long nonExistId=10000l;
		
		  try {
		    	
			    assertThrows(CouponSystemException.class, ()->companyService.getCompanyDetails(nonExistId));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		  
		  System.out.println("\t Trying to get company details with existing Id number");
		  System.out.println("\t"+TestUtils.simpleSeparator);
		  
		  //Getting random company from the list of existing
		  Company existingCompany=companyRepository
				  .findAll()
				  .get(TestUtils.random.nextInt(InitCompanies.companiesCapacity));
		  //Extract the actual id number
		  Long existId=existingCompany.getCompanyId();
		  //Invoking tested method with the id of the erxisting company
		  Company actualCompany=companyService.getCompanyDetails(existId);
		
		   try {
			   assertNotNull(actualCompany);
			   assertEquals(existingCompany.getCompanyId(), actualCompany.getCompanyId());
			   assertEquals(existingCompany.getName(), actualCompany.getName());
			   assertEquals(existingCompany.getEmail(), actualCompany.getEmail());
			   assertEquals(existingCompany.getPassword(), actualCompany.getPassword());
			   System.out.println("\t Success");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		   
        System.out.println("\t Trying to get company details of an inactive company ");
		   //Turning existing company to an inactive and saving it back
		   actualCompany.setIsActive(false);
		   companyRepository.save(actualCompany);
		   
		   try {
		    	
			    assertThrows(CouponSystemException.class, ()->companyService.getCompanyDetails(actualCompany.getCompanyId()));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		   //Cleaning up after the test is done
		   actualCompany.setIsActive(true);
		   companyRepository.save(actualCompany);
		
	}
	@Test
	@Transactional
	private void testIsCompanyExist() throws CouponSystemException {
		
		 System.out.println("\t Testing isCompanyExist() method");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		//Creating random company 
		Company company=TestUtils.createRandomCompanyNoCoupons();
		//Setting company is to non-existing number(out of the range)
		company.setCompanyId(TestUtils.random.nextLong()+InitCompanies.companiesCapacity);
		
		 System.out.println("\t Running test with non-existing(unsaved) company");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		
		try {
			assertThrows(CouponSystemException.class,()->companyService.isCompanyExists
					(company.getCompanyId(), company.getEmail(), company.getPassword()));
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		//Saving the random company created
		company.setCompanyId(null);
		companyRepository.save(company);
		Long companyId=company.getCompanyId();
		String email=company.getEmail();
		String password=company.getPassword();
		
		 System.out.println("\t Running test again after company has been saved ");
		 System.out.println("\t"+TestUtils.simpleSeparator);
		 System.out.println();
		
		 try {
				assertTrue(companyService.isCompanyExists
						(companyId, email, password));
				System.out.println("\t Passed successfully: company was found ");
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
	private void testGetCompanyCoupons() throws CouponSystemException{
		
        System.out.println("\t Testing getCompanyCoupons method ");
        System.out.println("\t"+TestUtils.simpleSeparator);
        System.out.println();
		
        //Generating random id from the existing range
		Long clientId = TestUtils.random.nextInt(InitCompanies.companiesCapacity/2)+1l;
		//Getting coupons of an existing company
		List<Coupon> companyCoupons=companyService.getCompanyCoupons(clientId);
		int actualAmountOfCoupons=companyCoupons.size();
		
		//Filtering company coupons from overall and comparing the result
		int expectedAmountOfCoupons=couponRepository
				.findAll()
				.stream()
				.filter(c->c.getCompany().getCompanyId()==clientId)
				.filter(c->c.getIsActive())
				.collect(Collectors.toList())
				.size();
		
		 try {
			   
			   assertEquals( expectedAmountOfCoupons,actualAmountOfCoupons);
			   System.out.println("\t Coupons list of proper size  created : Success");
			   
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		//Checking that all coupons are active
		for(Coupon coupon:companyCoupons) {
			if(!coupon.getIsActive())
				System.out.println("\t One or more of company coupons are inactive.Test failed");
		
		}
		
		System.out.println("\t Test success: all company coupons are active");
		
		//Turning half of the coupons to an inactive
		
		for(int i=0;i<companyCoupons.size();i++)
		
		      if(i%2==0) {
		    	  companyCoupons.get(i).setIsActive(false);
		    	  
		      }
		
		couponRepository.saveAll(companyCoupons);
		
		List<Coupon> companyCoupons2=companyService.getCompanyCoupons(clientId);
	    int  amountOfCoupons2=companyCoupons2.size();
		
		 try {
			   
			   assertEquals( expectedAmountOfCoupons/2,amountOfCoupons2);
			   System.out.println("\t"+TestUtils.simpleSeparator);
			   System.out.println("\t Inactive coupons were filtered : Success");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		 
		 //Cleaning up after the test is done
		 for(int i=0;i<companyCoupons.size();i++)
				
		      if(i%2==0) {
		    	  companyCoupons.get(i).setIsActive(true);
		    	  
		      }
		
		couponRepository.saveAll(companyCoupons);
		
	}
	
	@Test
	private void testGetCompanyCouponsByCategory() throws CouponSystemException {
		
        System.out.println("\t Testing getCompanyCouponsByCategory test");
		
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		//Picking one random existing category
		Category category=categoryRepository.findById
				(TestUtils.random.nextInt(InitCategories.categoriesCapacity/2)+1l).get();
		
		//Invoking the tested method
		List<Coupon> companyCouponsByCategory=companyService
				.getCompanyCouponsByCategory
				(TestUtils.random.nextInt(InitCompanies.companiesCapacity/2)+1l, category);
		
		int couponsQuantity=companyCouponsByCategory.size();
		
		try {
			assertTrue(couponsQuantity<=InitCoupons.couponCapacity);
			System.out.println("\t Passed successfully:coupon list of proper size created ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		//Checking all coupons belong to the expected category
		for(Coupon coupon: companyCouponsByCategory) {
			
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
	private void testGetCompanyCouponsByMaxPrice() throws CouponSystemException {
		
		System.out.println("\t Testing getCompanyCouponsByMaxPrice method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		double maxUnitPrice= TestUtils.random.nextDouble()*10;
		
		Long clientId=TestUtils.random.nextInt(InitCompanies.companiesCapacity/2)+1l;
		
		List <Coupon> companyCouponsByMaxPrice=companyService
				.getCompanyCouponsByMaxPrice(clientId, maxUnitPrice);
		
		try {
			assertTrue(companyCouponsByMaxPrice.size()<=InitCoupons.couponCapacity);
			System.out.println("\t Passed successfully:coupon list of proper size created ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		if(companyCouponsByMaxPrice.size()==0)
			System.out.println("\t  No coupons with unit price lower than specified");
		
		for(Coupon coupon:companyCouponsByMaxPrice) {
			
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
	private void testAddCouponMethod() throws CouponSystemException {
		
		System.out.println("\t Testing addCoupon method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		System.out.println("\t Trying to add coupon belonging to another company");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		Long clientId=TestUtils.random.nextInt(InitCompanies.companiesCapacity/2)+2l;
		Long otherCompanyId=clientId-1l;
		Coupon couponToAdd=TestUtils
				.createRandomCoupon
				(companyService.getCompanyDetails(otherCompanyId), categoryRepository.findById(1l).get());
		
		try {
			assertThrows(CouponSystemException.class,()->companyService.addCoupon(clientId, couponToAdd));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println("\t Trying to  add already  existing coupon");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		Coupon couponToAdd2=companyService
				.getCompanyCoupons(clientId)
				.get(TestUtils.random.nextInt(InitCoupons.couponCapacity/2));
		
		try {
			assertThrows(CouponSystemException.class,()->companyService.addCoupon(clientId, couponToAdd2));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
		System.out.println("\t Trying to add coupon with the same title as existing");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		Coupon couponToAdd3=TestUtils
				.createRandomCoupon
				(companyService.getCompanyDetails(clientId), categoryRepository.findById(1l).get());
		
		couponToAdd3.setTitle(couponToAdd2.getTitle());
		couponToAdd3.setId(100l);
		
		try {
			assertThrows(CouponSystemException.class,()->companyService.addCoupon(clientId, couponToAdd3));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		

		System.out.println("\t Adding coupon successfully ");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		
		Company company=companyRepository
				.findAll()
				.get(TestUtils.random.nextInt(InitCompanies.companiesCapacity));
		Category category=categoryRepository
				.findAll()
				.get(TestUtils.random.nextInt(InitCategories.categoriesCapacity/2));
		Coupon couponToAdd4=TestUtils.createRandomCoupon(company, category);
		couponToAdd4.setId(10000l);
		Long actualId=companyService.addCoupon(company.getCompanyId(),couponToAdd4);
		
		
		
		couponToAdd4.setId(actualId);
	    Coupon actualAddedCoupon=couponRepository.findById(actualId).get();
		
		try {
			assertNotNull(actualAddedCoupon);
			assertEquals(couponToAdd4.getTitle(), actualAddedCoupon.getTitle());
			assertEquals(couponToAdd4.getDescription(), actualAddedCoupon.getDescription());
			assertEquals(couponToAdd4.getQuantity(), actualAddedCoupon.getQuantity());
			assertEquals(couponToAdd4.getIsActive(), actualAddedCoupon.getIsActive());
			System.out.println("\t Success : coupon"+couponToAdd4.toString()+" has been added ");
			System.out.println("\t"+TestUtils.simpleSeparator);
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\t"+TestUtils.warning);
		    System.out.println("\t DID NOT PASS !");
			System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
		
	}
	
	@Test
	private void testUpdateCouponMethod() throws CouponSystemException {
		
		System.out.println("\t Testing updateCoupon method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		//Unathorized access
		
		System.out.println("\t Trying to update coupon belonging to another company");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		Long clientId=TestUtils.random.nextInt(InitCompanies.companiesCapacity/2)+2l;
		Long otherCompanyId=clientId-1l;
		Coupon couponToUpdate=TestUtils
				.createRandomCoupon
				(companyService.getCompanyDetails(otherCompanyId), categoryRepository.findById(1l).get());
		
		try {
			assertThrows(CouponSystemException.class,()->companyService.updateCoupon(clientId, couponToUpdate));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println("\t Trying to update coupon with the same title as existing");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		Coupon couponToUpdate2=TestUtils
				.createRandomCoupon
				(companyService.getCompanyDetails(clientId), categoryRepository.findById(1l).get());
		Coupon sampleCompanyCoupon=companyService.getCompanyCoupons(clientId).get(0);
		
		couponToUpdate2.setTitle(sampleCompanyCoupon.getTitle());
		couponToUpdate2.setId(100l);
		
		try {
			assertThrows(CouponSystemException.class,()->companyService.updateCoupon(clientId, couponToUpdate2));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println("\t Trying to update coupon successfully");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		sampleCompanyCoupon.setTitle("New Title");
		Coupon updatedCoupon=companyService.updateCoupon(clientId, sampleCompanyCoupon);
		Coupon actualCoupon=couponRepository.findById(updatedCoupon.getId()).get();
		String updatedCouponTitle=updatedCoupon.getTitle();
		String actuaCouponTitle=actualCoupon.getTitle();
		
		try {
			   
			   assertEquals( updatedCouponTitle,actuaCouponTitle);
			   System.out.println("\t"+TestUtils.simpleSeparator);
			   System.out.println("\t  Success : Coupon updated OK");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		
		
	}
	
		
	
	@Test
	@Transactional
	private void testDeleteCouponMethod() throws CouponSystemException {
		
		System.out.println("\t Testing deleteCoupon method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		System.out.println("\t Trying to delete non-existing coupon");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		//Creating new coupon(still unsaved to the database)
		Company company=companyRepository
				.findAll()
				.get(TestUtils.random.nextInt(InitCompanies.companiesCapacity/2));
		Category category=categoryRepository
				.findAll()
				.get(TestUtils.random.nextInt(InitCategories.categoriesCapacity/2));
		Coupon unsavedCoupon=TestUtils.createRandomCoupon(company, category);
		unsavedCoupon.setId(12345678l);
		
		try {
			assertThrows(CouponSystemException.class,
					()->companyService.deleteCoupon(company.getCompanyId(), unsavedCoupon.getId()));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println("\t Trying to delete inactive coupon");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		//Picking up an existing coupon and turning it into inactive
		Coupon existingCoupon=couponRepository
				.findAll()
				.get(TestUtils.random.nextInt(InitCoupons.couponCapacity));
		
		Long companyId=existingCoupon.getCompany().getCompanyId();
		existingCoupon.setIsActive(false);
		couponRepository.save(existingCoupon);
		
		try {
			assertThrows(CouponSystemException.class,()->companyService.deleteCoupon(companyId, existingCoupon.getId()));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		//Cleaning up after the test is done
		existingCoupon.setIsActive(true);
		couponRepository.save(existingCoupon);
		
		System.out.println("\t Trying to delete coupon belonging to another company ");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		//Giving wrong credentials at the company id argument position
		
		try {
			assertThrows(CouponSystemException.class,
					()->companyService.deleteCoupon
					(((companyId<5)?(companyId+1l):(companyId-1l)), existingCoupon.getId()));
					
			System.out.println("\t Passed successfully: proper exception has been thrown ");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		System.out.println("\t Successfully deleting coupon ");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		companyService.deleteCoupon(companyId, existingCoupon.getId());
		
		//Extracting the results
		
		Coupon deletedCoupon=couponRepository.findById(existingCoupon.getId()).get();
		
		List<Purchase> purchaseHistory=purchaseRepository.findByCouponId(existingCoupon.getId());
		
		
		//Checking the results
		
		try {
			
			assertFalse(deletedCoupon.getIsActive());
			for(Purchase purchase:purchaseHistory)
				assertFalse(purchase.isActive());
			System.out.println("\t Passed successfully: coupon deleted(inactive) and no purchase history");
			
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
		//Cleaning up after the test is done
		existingCoupon.setIsActive(true);
		
		for(Purchase purchase:purchaseHistory)
			purchase.setActive(true);
		
		purchaseRepository.saveAll(purchaseHistory);
		
		
	}
	
	
	
}
