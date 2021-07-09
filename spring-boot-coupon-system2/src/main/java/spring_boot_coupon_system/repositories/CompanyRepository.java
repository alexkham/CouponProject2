package spring_boot_coupon_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
	
	boolean findByEmailAndPassword(String email,String password);

}
