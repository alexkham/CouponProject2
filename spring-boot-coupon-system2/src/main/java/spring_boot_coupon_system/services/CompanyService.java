package spring_boot_coupon_system.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import lombok.Data;
import lombok.Getter;
import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Purchase;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.exceptions.ErrorMessages;
import spring_boot_coupon_system.repositories.CompanyRepository;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Service
public class CompanyService extends ClientService implements ClientLoginService {
	
	
	/**
	 *Checks if company with specified email and password values exists in the database
	 * @param clientId Long Id number of company attempting to perform action
	 * @param email String value 
	 * @param password String value 
	 * @return boolean value If company with such credentials exists-true,otherwise-false
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(Long clientId,String email, String password) throws CouponSystemException {
		
		//Case there is wrong client id provided
		
		Company company = getCompanyDetails(clientId);
		
		String companyEmail=company.getEmail().toLowerCase().trim();
		String companyPassword=company.getPassword();
				
		
		return companyEmail.equalsIgnoreCase(email.toLowerCase().trim())
				&&companyPassword.equals(password);
	}


	

    /**
     * 
     * @param clientId  Long Id number of company attempting to perform the action
     * @param email String value 
     * @param password  String value
     * @return boolean value :if company does exist-true,otherwise-false
     * @throws CouponSystemException
     */
	public boolean isCompanyExists(Long clientId, String email,String password) throws CouponSystemException {
		
		validateCompany(clientId);

		return (companyRepository.findByEmailAndPassword(email, password)!=null);


	}
    
	/**
	 * Adds a new coupon to the database after validating its data
	 * @param clientId Long Id number of company attempting to perform the action
	 * @param coupon Object of type Coupon to be added
	 * @throws CouponSystemException
	 */
	@Transactional
	public Long addCoupon(Long clientId ,Coupon coupon) throws CouponSystemException {
		
		
		//Checking if company exists and active
		validateCompany(clientId);
		

		if(coupon.getCompany()!=null&& coupon.getCompany().getCompanyId()!=clientId) 

			throw new CouponSystemException
			(ErrorMessages.UNATHORIZED_ACCES_ATTEMPT);
		
		 if(couponRepository.existsById(coupon.getId()))
			 throw new CouponSystemException(ErrorMessages.COUPON_ALREADY_EXISTS);
		
		 String couponTitle=coupon.getTitle();
		 if(couponTitle!=null&&couponRepository.findByCompanyCompanyIdAndTitle(clientId, couponTitle) != null) 
			
			throw new CouponSystemException
			(ErrorMessages.COUPON_TITLE_EXISTS);
		
		
		 Coupon couponToAdd=couponRepository.save(coupon);
		 
		 
		return couponToAdd.getId();
		
	
	}
	
	
	/**
	 * Updates record  corresponding to a coupon received as a parameter
	 * @param coupon Object of type Coupon 
	 * @param clientId Long Id number of company attempting to perform the action
	 * @throws CouponSystemException
	*/ 
	public Coupon updateCoupon(Long clientId,Coupon coupon) throws CouponSystemException {
		
		
		//Checking if company exists
		validateCompany(clientId);
		
		Long couponId=coupon.getId();
		Long companyId=coupon.getCompany().getCompanyId();
		String title=coupon.getTitle();
		Coupon otherCoupon=null;
		
		
		//Checking if the coupon belongs to the company 
		if(companyId!=null&&companyId!=clientId) 
			
			 throw new CouponSystemException
			 (ErrorMessages.UNATHORIZED_ACCES_ATTEMPT);
			
		else {
			//Can use clientId either ,but since it already has passed the validation they should be equal 
			otherCoupon=couponRepository.findByCompanyCompanyIdAndTitle(companyId, title);
			
			if(otherCoupon!=null&&otherCoupon.getId()!=couponId) 
				
				throw new CouponSystemException(ErrorMessages.COUPON_TITLE_EXISTS);
			
		}
		
		return couponRepository.save(coupon);

		
	}
	
	/**
	 * Deletes (turns inactive) a coupon specified by id received as a parameter 
	 * @param clientId Long Id number of company attempting to perform the action
	 * @param couponId Id number of a coupon to be deleted
	 * @throws CouponSystemException
	 */
    @Transactional
	public void deleteCoupon(Long clientId, long couponId) throws CouponSystemException {
		
    	//Checking if company exists
    	validateCompany(clientId);
		
    	
    	
		Coupon coupon=couponRepository.findById( couponId).
				orElseThrow(()->new CouponSystemException(ErrorMessages.COUPON_DOES_NOT_EXIST));
		
		if(!coupon.getIsActive())
			throw new CouponSystemException(ErrorMessages.COUPON_IS_INACTIVE);
		
		Long companyId=coupon.getCompany().getCompanyId();
				
	    if(companyId!=null&&companyId!=clientId) 
			
			 throw new CouponSystemException
			 (ErrorMessages.UNATHORIZED_ACCES_ATTEMPT);
	    
				
	    coupon.setIsActive(false);
		couponRepository.save(coupon);
		
		List<Purchase> purchaseHistory=purchaseRepository.findByCouponId(couponId);
		
		for(Purchase purchase:purchaseHistory)
			purchase.setActive(false);
		
		purchaseRepository.saveAll(purchaseHistory);
					
			
	}
    
    
    /**
	 * Creates and returns a list of all coupons belonging to the current company
	 * @param clientId Long Id number of company attempting to perform the action
	 * @return List of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons(Long clientId) throws CouponSystemException{
		
		validateCompany(clientId);
		Company company=getCompanyDetails(clientId);
	 
		List<Coupon> companyCoupons=couponRepository.findByCompanyCompanyId(clientId)
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
		
		return companyCoupons;

	}
	   
	/**
	 * Creates and returns a list of coupons  belonging to the current company and with category received  as a parameter
	 * @param clientId Long Id number of company attempting to perform the action
	 * @param maxPrice Double value coupon unit price
	 * @return List of coupons 
	 * @throws CouponSystemException
	 */
       public List<Coupon> getCompanyCouponsByCategory(Long clientId,Category category) throws CouponSystemException{
		
		validateCompany(clientId);
		
		Long categoryId=category.getId();
		
		List<Coupon> companyCouponsByCategory=couponRepository.findByCompanyCompanyIdAndCategoryId(clientId,categoryId)
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
		
		return companyCouponsByCategory;

	}
       
       
       /**
   	 * Creates and returns a list of coupons belonging to the current company with unit price 
   	 * not exceeding a value received as a parameter
   	 * @param clientId Long Id number of company attempting to perform the action
   	 * @param maxPrice Double value coupon unit price
   	 * @return List of coupons 
   	 * @throws CouponSystemException
   	 */
       public List<Coupon> getCompanyCouponsByMaxPrice(Long clientId,double maxPrice) throws CouponSystemException{
    	 
    	validateCompany(clientId);
   		
   		    	   
		return couponRepository.findByCompanyCompanyIdAndUnitPriceLessThan(clientId, maxPrice)
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
    	   
    	   
       }

	

   	/**
   	 * Returns an object with all details identical to the current logged in company
   	 * @param clientId Long Id number of company attempting to perform the action
   	 * @return object of type Company
   	 * @throws CouponSystemException 
   	*/
       public Company getCompanyDetails(Long clientId) throws CouponSystemException {
    	
    	   Company company=companyRepository.findById(clientId)
  					.orElseThrow(()->new CouponSystemException(ErrorMessages.CLIENT_ID_DOES_NOT_EXIST));
  		
  		if(!company.getIsActive())
     			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
  		
  		
  		return company;
   		
	}
       /**
        * Performs a check if company specified by argument exists in the database and is active
        * @param clientId Long Id number of company attempting to perform the action  
        * @throws CouponSystemException
        */
       public void validateCompany(Long clientId) throws CouponSystemException {
    	   
    	   
   		if(!companyRepository.existsById(clientId))
   					throw new CouponSystemException(ErrorMessages.CLIENT_ID_DOES_NOT_EXIST);
   		
   		if(!companyRepository.findById(clientId).get().getIsActive())
      			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
   		
   		
   		
   	}  

	

}
