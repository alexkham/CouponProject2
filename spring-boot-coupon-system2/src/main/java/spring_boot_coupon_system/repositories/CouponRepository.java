package spring_boot_coupon_system.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Coupon;



@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	List<Coupon> findByIsActiveTrue();
	
	List<Coupon> findByEndDateBefore(Date date);
	
/*	List<Coupon> findByCompanyId(Long companyId);
	
	/*Coupon findByCompanyIdAndTitle(Long companyId,String title);

	List<Coupon> findByCompanyIdAndCategoryId(Long companyId,Long categoryId);
	
	List<Coupon> findByCustomerId(Long customerId);

	List<Coupon> findByCustomerIdAndCategory(Long clientId, Long categoryId);
	*/

	/*List<Coupon> findByCustomerIdAndUnitPriceLessThan(Long clientId, double maxPrice);
	
	List<Coupon> findByCompanyIdAndUnitPriceLessThan(Long clientId, double maxPrice);
	
	*/
	
	

}
