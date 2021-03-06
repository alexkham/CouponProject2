package spring_boot_coupon_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Purchase;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	
	
	@Query(value=" SELECT p  FROM Purchase p  WHERE p.coupon.company.companyId=companyId")
    List<Purchase> findByCouponGetCompany(@Param("companyId") Long companyId);
	
	List<Purchase> findByCustomerId(Long customerId);
	List<Purchase> findByCouponId(Long couponId);

}
