package spring_boot_coupon_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
	
	
	
	List<Company> findByEmailAndPassword(String email,String password);
	
	boolean existsByEmail(String email);
	
	boolean existsByName(String name);
	
	boolean existsByEmailAndPassword(String email,String password);

}
