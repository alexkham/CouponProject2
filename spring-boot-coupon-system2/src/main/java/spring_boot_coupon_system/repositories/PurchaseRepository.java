package spring_boot_coupon_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	
	public List<Purchase> findByCompany(Company company);

}
