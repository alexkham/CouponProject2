package spring_boot_coupon_system.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.repositories.CompanyRepository;

@Service
@Transactional
public class CompanyService extends GeneralService {
	
	
	public boolean isCompanyExists(Long clientId, String email,String password) throws CouponSystemException {
		//Case there is wrong client id provided
		Company company=companyRepository.findById(clientId).
				orElseThrow(()->new CouponSystemException
						("Unauthorized access attemt :There is no client with such an Id! "));
		//else
		String clientEmail=company.getEmail().trim();
		String clientPassword=company.getPassword().trim();
		if(!(clientEmail.equalsIgnoreCase(email.trim())&&clientPassword.equalsIgnoreCase(password))) {
			
			throw new CouponSystemException("Unauthorized access attemt:Data provided do not match!");
		}
		
		
		return company.equals(companyRepository.findByEmailAndPassword(email, password));
		
		
	}
	
	
	public void addCompany(Company company) {
		companyRepository.save(company);
		
	}
	
	public void updateCompany(Company company) {
		
	}
	
	public void deleteCompany(int companyId) {
		
	}
	
	public List<Company> getAllCompanies(){
		return null;
		
	}
	
	public Company getOneCompany(int companyId) {
		return null;
		
	}

}
