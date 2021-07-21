package spring_boot_coupon_system.services;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.Data;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.repositories.CouponRepository;

@Component
@Transactional
public class DailyJob  {
	
	
	@Autowired
	private CouponRepository couponRepository;
	
	
	private final long PERIOD=86_400_000;
	
	@Scheduled(fixedRate = PERIOD)
	public void deleteExpiredCoupons() {
		
		Date now=new Date(System.currentTimeMillis());
		
		List<Coupon> expiredCoupons=couponRepository.findByEndDateBefore(now);
		
		for(Coupon coupon:expiredCoupons) {
			
			coupon.setIsActive(false);
						
		}
		
		couponRepository.saveAll(expiredCoupons);
			
	}
	
	

}
