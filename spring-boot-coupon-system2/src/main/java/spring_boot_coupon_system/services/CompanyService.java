package spring_boot_coupon_system.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.repositories.CompanyRepository;

@Service
public class CompanyService extends GeneralService {


	public boolean isCompanyExists(Long clientId, String email,String password) throws CouponSystemException {
		//Case there is wrong client id provided
		Company company=companyRepository.findById(clientId).
				orElseThrow(()->new CouponSystemException
						("Unauthorized access attemt :There is no client with such an Id! "));
		//else
		//Comparing actual data with arguments
		String clientEmail=company.getEmail().trim();
		String clientPassword=company.getPassword().trim();
		if(!(clientEmail.equalsIgnoreCase(email.trim())&&clientPassword.equalsIgnoreCase(password))) {

			throw new CouponSystemException("Unauthorized access attemt:Data provided do not match!");
		}

		//Might be redundant :may just return true
		return company.equals(companyRepository.findByEmailAndPassword(email, password));


	}

	@Transactional
	public long addCoupon(Long clientId ,Coupon coupon) throws CouponSystemException {
		Optional<Coupon> optionalCoupon=couponRepository.findById(coupon.getId());
		String couponTitle=coupon.getTitle();
		//Checking if company exists
		if(!companyRepository.existsById(clientId)) 
			throw new CouponSystemException("Unathorized access attempt:the credentials provided aren't valid");
		

		else if(coupon.getCompanyId()!=null&& coupon.getCompanyId()!=clientId) 

			throw new CouponSystemException
			("You are not allowed to perform that action:the coupon you are trying to add belongs to another company");

		
		//Checking if such coupon already exists
		//Cover the case argument coupon's id is null


		else if(optionalCoupon.get()!=null) 
			throw new CouponSystemException("Cannot perform action: Such coupon already exists in the database!");
		
		
		else if(couponTitle!=null&&couponRepository.findByCompanyIdAndTitle(clientId, couponTitle) != null) 
			
			throw new CouponSystemException
			("Cannot perform action:Coupon with such a title already exists in the database!");

		


		Coupon addedCoupon=couponRepository.save(coupon);


		return addedCoupon.getId();

	}

	public void updateCoupon(Long clientId,Coupon coupon) {

	}

	public void deleteCoupon(int couponId) {

	}

	public List<Company> getAllCoupons(){
		return null;

	}

	public Company getOneCoupon(int couponId) {
		return null;

	}

}
