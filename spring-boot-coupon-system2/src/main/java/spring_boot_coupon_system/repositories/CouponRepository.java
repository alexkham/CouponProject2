package spring_boot_coupon_system.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Coupon;



@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	List<Coupon> findByEndDateBefore(Date date);
	
	Coupon findByCompanyIdAndTitle(Long companyId,String title);

}
