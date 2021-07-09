package spring_boot_coupon_system.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.Data;
import spring_boot_coupon_system.entities.Coupon;

@Service
public class DailyJob extends GeneralService {
	
	
	private final long PERIOD=86_400_000;
	
	@Scheduled(fixedRate = PERIOD)
	public void deleteExpiredCoupons() {
		
		Date now=new Date(System.currentTimeMillis());
		
		List<Coupon> expiredCoupons=couponRepository.findByEndDateBefore(now);
		
		for(Coupon coupon:expiredCoupons) {
			
			couponRepository.delete(coupon);
		}
			
	}
	
	

}
