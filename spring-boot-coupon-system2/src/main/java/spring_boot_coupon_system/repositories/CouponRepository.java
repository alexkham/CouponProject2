package spring_boot_coupon_system.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Purchase;

/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	List<Coupon> findByIsActiveTrue();
	
	
	
	List<Coupon> findByEndDateBefore(Date date);
	
	List<Coupon> findByCompanyCompanyId(Long companyId);
	
	Coupon findByCompanyCompanyIdAndTitle(Long companyId,String title);
	
	List<Coupon> findByCompanyCompanyIdAndCategoryId(Long companyId,Long categoryId);
	
	List<Coupon> findByCompanyCompanyIdAndUnitPriceLessThan(Long clientId, double maxPrice);
	
	
	
	
	

	
	
	

}
