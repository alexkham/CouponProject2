package spring_boot_coupon_system.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.Data;
import lombok.Getter;
import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.exceptions.ErrorMessages;
import spring_boot_coupon_system.repositories.CompanyRepository;

@Service
public class CompanyService extends ClientService implements ClientLoginService {
	
	@Override
	public boolean login(Long clientId,String email, String password) throws CouponSystemException {
		
		//Case there is wrong client id provided
		
		Company company = getCompanyDetails(clientId);
		
		String companyEmail=company.getEmail().toLowerCase().trim();
		String companyPassword=company.getPassword();
				
		
		return companyEmail.equalsIgnoreCase(email.toLowerCase().trim())
				&&companyPassword.equals(password);
	}


	


	public boolean isCompanyExists(Long clientId, String email,String password) throws CouponSystemException {
		//Case there is wrong client id provided
		Company company=getCompanyDetails(clientId);
		//Comparing actual data with arguments
		String clientEmail=company.getEmail().trim();
		String clientPassword=company.getPassword().trim();
		if(!(clientEmail.equalsIgnoreCase(email.trim())&&clientPassword.equalsIgnoreCase(password))) {

			throw new CouponSystemException(ErrorMessages.WRONG_DATA_PROVIDED);
		}

		//Might be redundant :may just return true
		return company.equals(companyRepository.findByEmailAndPassword(email, password));


	}

	@Transactional
	public long addCoupon(Long clientId ,Coupon coupon) throws CouponSystemException {
		
		
		//Checking if company exists and active
		validateCompany(clientId);
		

		if(coupon.getCompany()!=null&& coupon.getCompany().getCompanyId()!=clientId) 

			throw new CouponSystemException
			(ErrorMessages.UNATHORIZED_ACCES_ATTEMPT);
		
		Optional<Coupon> optionalCoupon=couponRepository.findById(coupon.getId());
		String couponTitle=coupon.getTitle();
		

		
		//Checking if such coupon already exists
		//Cover the case argument coupon's id is null


		 if(optionalCoupon.get()!=null) 
			throw new CouponSystemException(ErrorMessages.COUPON_TITLE_EXISTS);
		
		
	//	else if(couponTitle!=null&&couponRepository.findByCompanyIdAndTitle(clientId, couponTitle) != null) 
			
			throw new CouponSystemException
			(ErrorMessages.COUPON_TITLE_EXISTS);

		
	//	Coupon addedCoupon=couponRepository.save(coupon);


	}

	public void updateCoupon(Long clientId,Coupon coupon) throws CouponSystemException {
		
		
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
		//	otherCoupon=couponRepository.findByCompanyIdAndTitle(companyId, title);
			
			if(otherCoupon!=null&&otherCoupon.getId()!=couponId) 
				
				throw new CouponSystemException(ErrorMessages.COUPON_TITLE_EXISTS);
			
		}
		
		couponRepository.save(coupon);

		
	}
	
    @Transactional
	public void deleteCoupon(Long clientId, long couponId) throws CouponSystemException {
		
		
		Coupon coupon=couponRepository.findById( couponId).
				orElseThrow(()->new CouponSystemException(ErrorMessages.COUPON_DOES_NOT_EXIST));
		
		if(!coupon.getIsActive())
			throw new CouponSystemException(ErrorMessages.COUPON_IS_INACTIVE);
		
		Long companyId=coupon.getCompany().getCompanyId();
		boolean active=coupon.getIsActive();
		
		//Checking if company exists
		validateCompany(clientId);
		
	    if(companyId!=null&&companyId!=clientId) 
			
			 throw new CouponSystemException
			 (ErrorMessages.UNATHORIZED_ACCES_ATTEMPT);
	    
		else if(!active)
				throw new CouponSystemException(ErrorMessages.COUPON_IS_INACTIVE);
		
			else {
				coupon.setIsActive(false);
				couponRepository.save(coupon);
					
		}
		
	}

	public List<Coupon> getCompanyCoupons(Long clientId) throws CouponSystemException{
		
		validateCompany(clientId);
		
		//List<Coupon> companyCoupons=couponRepository.findByCompanyId(clientId);
		
		return null;//companyCoupons;

	}
	
       public List<Coupon> getCompanyCouponsByCategory(Long clientId,Category category) throws CouponSystemException{
		
		validateCompany(clientId);
		
		Long categoryId=category.getId();
		
		//List<Coupon> companyCouponsByCategory=couponRepository.findByCompanyIdAndCategoryId(clientId,categoryId);
		
		return null; //companyCouponsByCategory;

	}
       
       public List<Coupon> getCompanyCouponsByMaxPrice(Long clientId,double maxPrice) throws CouponSystemException{
    	 
    	validateCompany(clientId);
   		
   		    	   
		return null; //couponRepository.findByCompanyIdAndUnitPriceLessThan(clientId, maxPrice);
    	   
    	   
       }

	
       
       public Company getCompanyDetails(Long clientId) throws CouponSystemException {
    	
    	   Company company=companyRepository.findById(clientId)
  					.orElseThrow(()->new CouponSystemException(ErrorMessages.CLIENT_ID_DOES_NOT_EXIST));
  		
  		if(!company.getIsActive())
     			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
  		
  		
  		return company;
   		
	}
       
       private void validateCompany(Long clientId) throws CouponSystemException {
    	   
    	   
   		if(!companyRepository.existsById(clientId))
   					throw new CouponSystemException(ErrorMessages.CLIENT_ID_DOES_NOT_EXIST);
   		
   		if(!companyRepository.getById(clientId).getIsActive())
      			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
   		
   		
   		
   	}  

	

}
