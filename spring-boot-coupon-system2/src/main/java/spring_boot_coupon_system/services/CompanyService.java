package spring_boot_coupon_system.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.repositories.CompanyRepository;

@Service
@Transactional
public class CompanyService extends GeneralService {
	
	
	public boolean isCompanyExists(String email,String password) {
		
		
		return companyRepository.findByEmailAndPassword(email, password);
		
		
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
