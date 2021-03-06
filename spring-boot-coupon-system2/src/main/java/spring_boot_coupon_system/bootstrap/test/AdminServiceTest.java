package spring_boot_coupon_system.bootstrap.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import spring_boot_coupon_system.bootstrap.TestUtils;
import spring_boot_coupon_system.bootstrap.init.InitCompanies;
import spring_boot_coupon_system.bootstrap.init.InitCustomers;
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
@Order(8)
public class AdminServiceTest extends ServiceTest implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("\t Running AdminService Test");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		testAdminLoginMethod();
		
		testGetAllCompaniesMethod();
		testGetAllCustomersMethod();
		
		testGetOneCompanyMethod();
		testGetOneCustomerMethod();
		testAddCompanyMethod();
		testAddCustomerMethod();
		testUpdateCustomerMethod();
		testUpdateCompanyMethod();
		testDeleteCustomerMethod();
		testDeleteCompanyMethod();
		
		
	}
	
	
	@Test
	private void testAdminLoginMethod() {
		
		System.out.println("\t Testing AdminLogin method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();	
		
		 
		
        System.out.println("\t Testing Login method with wrong credentials");
        System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		try {
			assertThrows(CouponSystemException.class, 
					()->adminService.login(TestUtils.createRandomEmail(),TestUtils.createRandomString(5)));
					
			System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		 System.out.println("\t Testing Login method with right credentials");
	        System.out.println("\t"+TestUtils.simpleSeparator);
			System.out.println();
			
		
		
		try {
			
			assertTrue(adminService.login("admin@admin.com", "admin"));
			
			System.out.println("\t Passed: login performed  successfully! ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
	}
	
	@Test
	private void testAddCompanyMethod() throws CouponSystemException {
		
		System.out.println("\t Testing AddCompany method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		System.out.println("\t Trying to add company with  existing name");
        System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		//Picking up 1 random from all existing and extracting it's name
		List<Company> allCompanies=companyRepository.findAll();
		String existingName=allCompanies.get(TestUtils.random.nextInt(allCompanies.size()-1)).getName();
		
		//Creating new company and setting it's name to existing
		Company companyToAdd=TestUtils.createRandomCompanyNoCoupons();
		String initialCompanyName=companyToAdd.getName();
		String initialCompanyEmail=companyToAdd.getEmail();
		companyToAdd.setName(existingName);
		companyToAdd.setCompanyId(1000l);
		
		try {
			assertThrows(CouponSystemException.class, 
					()->adminService.addCompany(companyToAdd));
					
			System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
		//Returning to new company it's initial name and setting it's email to an existing
		
		companyToAdd.setName(initialCompanyName);
		String existingEmail=allCompanies.get(allCompanies.size()-1).getEmail();
		companyToAdd.setEmail(existingEmail);
		
		System.out.println("\t Trying to add company with  existing email");
        System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		try {
			assertThrows(CouponSystemException.class, 
					()->adminService.addCompany(companyToAdd));
					
			System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
		System.out.println("\t Testing addCompany method with proper credentials");
        System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		//Returning to company it's initial email, adding company and checking the results
		companyToAdd.setEmail(initialCompanyEmail);
		
		Long addedCompanyId=adminService.addCompany(companyToAdd);
		
		Company addedCompany=companyRepository.findById(addedCompanyId).get();
		
		companyToAdd.setCompanyId(addedCompanyId);
		
        try {
			
			assertEquals(companyToAdd, addedCompany);
			
			System.out.println("\t Passed: company added successfully! ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
	}
	
	
	@Test
	private void testUpdateCompanyMethod() throws CouponSystemException {
		System.out.println("\t Testing UpdateCompany method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		System.out.println("\t Trying to update company name ");
        System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		
		//Picking one random company from the existing
		Company companyToUpdate=companyRepository
				.findAll()
				.get(TestUtils.random.nextInt(InitCompanies.companiesCapacity));
		
		//Trying to change company name and update it (forbidden action)
		
		Long companyToUpdateId=companyToUpdate.getCompanyId();
		
		String updatedCompanyName=TestUtils.createRandomString(8);
		
		String originalCompanyName=companyToUpdate.getName();
		
		companyToUpdate.setName(updatedCompanyName);
		
		 try {
		    	
			    assertThrows(CouponSystemException.class, ()->adminService.updateCompany(companyToUpdate));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		
		
		//Updating company credentials
		String updatedEmail=TestUtils.createRandomEmail();
		String updatedPassword=TestUtils.createRandomString(8);
		
		
		companyToUpdate.setName(originalCompanyName);
		companyToUpdate.setPassword(updatedPassword);
		companyToUpdate.setEmail(updatedEmail);
		
		adminService.updateCompany(companyToUpdate);
		
		Company actualCompany=companyRepository.findById(companyToUpdateId).get();
		
		  try {
				
				assertEquals(updatedEmail, actualCompany.getEmail());
				assertEquals(updatedPassword, actualCompany.getPassword());
				
				System.out.println("\t Passed: company updated successfully! ");
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
	private void testDeleteCompanyMethod() throws CouponSystemException {
		
		System.out.println("\t Testing DeleteCompany method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
        Long companyToDeleteId=TestUtils.random.nextInt(10)+1l;
		
		adminService.deleteCompany(companyToDeleteId);
		
		Company actualDeletedCompany=companyRepository.findById(companyToDeleteId).get();
		
		
		int couponsCount=actualDeletedCompany.getCompanyCoupons().size();
		
		
		 try {
				
			    assertFalse(actualDeletedCompany.getIsActive());
			    assertEquals(0, couponsCount);
				System.out.println("\t Passed: company deleted successfully! ");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		
		actualDeletedCompany.setIsActive(true);
		companyRepository.save(actualDeletedCompany);
		
		
		
		
		
		
	}
	@Test
	private void testGetAllCompaniesMethod() {
		
		System.out.println("\t Testing GetAllCompanies method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		List<Company> allCompaniesActual=adminService.getAllCompanies();
		List<Company> allCompaniesExpected=companyRepository
				.findAll()
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
         try {
			
			assertEquals(allCompaniesExpected.size(), allCompaniesActual.size());
			for(int i=0;i<allCompaniesActual.size();i++) {
				assertEquals(allCompaniesExpected.get(i).getEmail(), allCompaniesExpected.get(i).getEmail());
				assertEquals(allCompaniesExpected.get(i).getName(), allCompaniesExpected.get(i).getName());
				assertEquals(allCompaniesExpected.get(i).getPassword(), allCompaniesExpected.get(i).getPassword());
				if(!allCompaniesActual.get(i).getIsActive())
					
					System.out.println("Unexpected result: One or more companies in the list are inactive");
			}
			System.out.println("\t Passed: list of  all companies extracted successfully! ");
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
	private void testGetOneCompanyMethod() throws CouponSystemException {
		
		System.out.println("\t Testing GetOneCompany method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		System.out.println("\t Trying to get company  with non existing company Id number");
		
		//Generating id number which does not exist for sure in the database at the moment
		
		Long nonExistId=TestUtils.random.nextLong()+100l;
		
		  try {
		    	
			    assertThrows(CouponSystemException.class, ()->adminService.getOneCompany(nonExistId));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		  
		  System.out.println("\t Trying to get company  with existing Id number");
		  System.out.println("\t"+TestUtils.simpleSeparator);
		  
		  //Getting random from existing range of id's
		  Long existId=TestUtils.random.nextInt(InitCompanies.companiesCapacity/2)+2l;
		  
		   Company company=adminService.getOneCompany(existId);
		
		   try {
			   assertNotNull(company);
			   assertEquals(company.getCompanyId(), existId);
			   System.out.println("\t Success : company has been retrieved");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		   
        System.out.println("\t Trying to get  an inactive company ");
		   
		   company.setIsActive(false);
		   companyRepository.save(company);
		   
		   try {
		    	
			    assertThrows(CouponSystemException.class, ()->adminService.getOneCompany(company.getCompanyId()));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		   
		   //Cleaning up after the test is done
		   company.setIsActive(true);
		   companyRepository.save(company);
		
		
	}
	@Test
	private void testAddCustomerMethod() throws CouponSystemException {
		
		System.out.println("\t Testing AddCustomer method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		
				/*Creating new customer, setting his email to an existing one 
				 * randomly picked from the list of all customers
				 */
				List<Customer> allCustomers=customerRepository.findAll();
				
				Customer customerToAdd=TestUtils.createRandomCustomer();
				String initialCustomerEmail=customerToAdd.getEmail();
				String existingEmail=allCustomers.get(allCustomers.size()-1).getEmail();
				customerToAdd.setEmail(existingEmail);
				customerToAdd.setId(1000l);
				
				
				
				System.out.println("\t Trying to add customer with  existing email");
		        System.out.println("\t"+TestUtils.simpleSeparator);
				System.out.println();
				try {
					assertThrows(CouponSystemException.class, 
							()->adminService.addCustomer(customerToAdd));
							
					System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
					System.out.println("\t"+TestUtils.starSeparator);
					
				} catch (Throwable t) {
					 t.printStackTrace();
					 System.out.println("\t"+TestUtils.warning);
				     System.out.println("\t DID NOT PASS !");
					 System.out.println("\t"+TestUtils.starSeparator);
				}
				
				
				System.out.println("\t Testing addCustomer method with proper credentials");
		        System.out.println("\t"+TestUtils.simpleSeparator);
				System.out.println();
				
				//Setting new customer email to the original , adding and checking the result
				customerToAdd.setEmail(initialCustomerEmail);
				
				Long addedCustomerId=adminService.addCustomer(customerToAdd).getId();
				
				Customer addedCustomer=customerRepository.findById(addedCustomerId).get();
				
				customerToAdd.setId(addedCustomerId);
				
		        try {
					
					assertEquals(customerToAdd, addedCustomer);
					
					System.out.println("\t Passed: customer added successfully! ");
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
	private void testUpdateCustomerMethod() throws CouponSystemException {
		
		System.out.println("\t Testing UpdateCustomer method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		//Picking up one random customer from the list of existing
		
		Customer customerToUpdate=customerRepository
				.findAll()
				.get(TestUtils.random.nextInt(InitCustomers.customersCapacity));
		
		//Creating new data for the customer
		String updatedFirstName=TestUtils.createRandomString(8);
		String updatedLastName=TestUtils.createRandomString(7);
		String updatedEmail=TestUtils.createRandomEmail();
		String updatedPassword=TestUtils.createRandomString(6);
		
		Long customerToUpdateId=customerToUpdate.getId();
		//Setting  customer's data
		customerToUpdate.setFirstName(updatedFirstName);
		customerToUpdate.setLastName(updatedLastName);
		customerToUpdate.setPassword(updatedPassword);
		customerToUpdate.setEmail(updatedEmail);
		//Updating
		adminService.updateCustomer(customerToUpdate);
		//Getting actual and checking the results
		Customer actualCustomer=customerRepository.findById(customerToUpdateId).get();
		
		  try {
				
				assertEquals(updatedFirstName, actualCustomer.getFirstName());
				assertEquals(updatedLastName, actualCustomer.getLastName());
				assertEquals(updatedEmail, actualCustomer.getEmail());
				assertEquals(updatedPassword, actualCustomer.getPassword());
				
				System.out.println("\t Passed: customer updated successfully! ");
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
	private void testDeleteCustomerMethod() throws CouponSystemException {
		
		System.out.println("\t Testing DeleteCustomer method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
		
		Long customerToDeleteId=TestUtils.random.nextInt(10)+1l;
		
		adminService.deleteCustomer(customerToDeleteId);
		
		Customer actualDeletedCustomer=customerRepository.findById(customerToDeleteId).get();
		
		
		int couponsCount=actualDeletedCustomer.getCoupons().size();
		
		List<Purchase> customerPurchases=purchaseRepository.findByCustomerId(customerToDeleteId);
		
		
		 try {
				
			    assertFalse(actualDeletedCustomer.getIsActive());
			    assertEquals(0, couponsCount);
			    for(Purchase purchase:customerPurchases) {
			    	if(purchase.isActive())
			    		System.out.println("One or more customer's purchases are still active");
			    }
				System.out.println("\t Passed: customer deleted successfully! ");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
				 t.printStackTrace();
				 System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			}
		
		actualDeletedCustomer.setIsActive(true);
		customerRepository.save(actualDeletedCustomer);
		
	}
	@Test
	private void testGetAllCustomersMethod() {
		
		System.out.println("\t Testing GetAllCustomers method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
				
		List<Customer> allCustomersActual=adminService.getAllCustomers();
		List<Customer> allCustomersExpected=customerRepository
				.findAll()
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
         try {
			
			assertEquals(allCustomersExpected.size(), allCustomersActual.size());
			for(int i=0;i<allCustomersActual.size();i++) {
				assertEquals(allCustomersExpected.get(i).getEmail(), allCustomersExpected.get(i).getEmail());
				assertEquals(allCustomersExpected.get(i).getFirstName(), allCustomersExpected.get(i).getFirstName());
				assertEquals(allCustomersExpected.get(i).getLastName(), allCustomersExpected.get(i).getLastName());
				assertEquals(allCustomersExpected.get(i).getPassword(), allCustomersExpected.get(i).getPassword());
				
				//Checking if all the customers are active 
				if(!allCustomersActual.get(i).getIsActive())
					
					System.out.println("Unexpected result: One or more customers in the list are inactive");
			}
			System.out.println("\t Passed: list of  all customers extracted successfully! ");
			System.out.println("\t"+TestUtils.starSeparator);
			
		} catch (Throwable t) {
			 t.printStackTrace();
			 System.out.println("\t"+TestUtils.warning);
		     System.out.println("\t DID NOT PASS !");
			 System.out.println("\t"+TestUtils.starSeparator);
		}
		
		
	}
	@Test
	private void testGetOneCustomerMethod() throws CouponSystemException {
		
		System.out.println("\t Testing GetOneCustomer method");
		System.out.println("\t"+TestUtils.simpleSeparator);
		System.out.println();
		
				
		System.out.println("\t Trying to get customer  with non existing  Id number");
		
		//Generating non existing "fake" id which is way out of the  current range
		Long nonExistId=TestUtils.random.nextLong()+100l;
		
		  try {
		    	
			    assertThrows(CouponSystemException.class, ()->adminService.getOneCompany(nonExistId));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		  
		  System.out.println("\t Trying to get customer  with an existing Id number");
		  System.out.println("\t"+TestUtils.simpleSeparator);
		  
		  //Generating id number from the current range
		  Long existId=TestUtils.random.nextInt(InitCustomers.customersCapacity/2)+2l;
		  
		   Customer customer=adminService.getOneCustomer(existId);
		
		   try {
			   assertNotNull(customer);
			   assertEquals(customer.getId(), existId);
			   System.out.println("\t Success : customer has been retrieved");
			   System.out.println("\t"+TestUtils.simpleSeparator);
		} catch (Exception e) {
			 System.out.println("\t"+TestUtils.warning);
			 System.out.println("\t DID NOT PASS !");
			
			e.printStackTrace();
		}
		   
        System.out.println("\t Trying to get  an inactive customer ");
		   //Setting the customer to inactive and saving
		   customer.setIsActive(false);
		   customerRepository.save(customer);
		   
		   try {
		    	
			    assertThrows(CouponSystemException.class, ()->adminService.getOneCustomer(customer.getId()));
				System.out.println("\t Passed successfully! "+CouponSystemException.class.getName()+" has been thrown");
				System.out.println("\t"+TestUtils.starSeparator);
				
			} catch (Throwable t) {
						    
			     t.printStackTrace();
			     System.out.println("\t"+TestUtils.warning);
			     System.out.println("\t DID NOT PASS !");
				 System.out.println("\t"+TestUtils.starSeparator);
			     
			}
		   //Cleaning up after the test is done
		   customer.setIsActive(true);
		   customerRepository.save(customer);
		
		
		
	}
		
	
	

}
