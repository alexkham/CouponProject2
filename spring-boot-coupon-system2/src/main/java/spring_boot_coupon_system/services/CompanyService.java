package spring_boot_coupon_system.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		
		validateCompany(clientId);

		return (companyRepository.findByEmailAndPassword(email, password)!=null);


	}

	@Transactional
	public long addCoupon(Long clientId ,Coupon coupon) throws CouponSystemException {
		
		
		//Checking if company exists and active
		validateCompany(clientId);
		

		if(coupon.getCompany()!=null&& coupon.getCompany().getCompanyId()!=clientId) 

			throw new CouponSystemException
			(ErrorMessages.UNATHORIZED_ACCES_ATTEMPT);
		
		Coupon optionalCoupon=couponRepository.findById(coupon.getId()).get();
		String couponTitle=coupon.getTitle();
		

		
		//Checking if such coupon already exists
		//Cover the case argument coupon's id is null


		 if(optionalCoupon!=null) 
			throw new CouponSystemException(ErrorMessages.COUPON_ALREADY_EXISTS);
		
		
		else if(couponTitle!=null&&couponRepository.findByCompanyCompanyIdAndTitle(clientId, couponTitle) != null) 
			
			throw new CouponSystemException
			(ErrorMessages.COUPON_TITLE_EXISTS);

		
		return couponRepository.save(coupon).getId();


	}

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
		
		List<Coupon> companyCoupons=couponRepository.findByCompanyCompanyId(clientId)
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
		
		return companyCoupons;

	}
	
       public List<Coupon> getCompanyCouponsByCategory(Long clientId,Category category) throws CouponSystemException{
		
		validateCompany(clientId);
		
		Long categoryId=category.getId();
		
		List<Coupon> companyCouponsByCategory=couponRepository.findByCompanyCompanyIdAndCategoryId(clientId,categoryId)
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
		
		return companyCouponsByCategory;

	}
       
       public List<Coupon> getCompanyCouponsByMaxPrice(Long clientId,double maxPrice) throws CouponSystemException{
    	 
    	validateCompany(clientId);
   		
   		    	   
		return couponRepository.findByCompanyCompanyIdAndUnitPriceLessThan(clientId, maxPrice)
				.stream()
				.filter(c->c.getIsActive())
				.collect(Collectors.toList());
    	   
    	   
       }

	
       
       public Company getCompanyDetails(Long clientId) throws CouponSystemException {
    	
    	   Company company=companyRepository.findById(clientId)
  					.orElseThrow(()->new CouponSystemException(ErrorMessages.CLIENT_ID_DOES_NOT_EXIST));
  		
  		if(!company.getIsActive())
     			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
  		
  		
  		return company;
   		
	}
       
       public void validateCompany(Long clientId) throws CouponSystemException {
    	   
    	   
   		if(!companyRepository.existsById(clientId))
   					throw new CouponSystemException(ErrorMessages.CLIENT_ID_DOES_NOT_EXIST);
   		
   		if(!companyRepository.findById(clientId).get().getIsActive())
      			throw new CouponSystemException(ErrorMessages.COMPANY_IS_INACTIVE);
   		
   		
   		
   	}  

	

}
