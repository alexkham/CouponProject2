package spring_boot_coupon_system.services;

import java.sql.Date;
import java.util.ArrayList;
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
import spring_boot_coupon_system.entities.Purchase;
import spring_boot_coupon_system.repositories.CouponRepository;
import spring_boot_coupon_system.repositories.PurchaseRepository;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Component
@Transactional
public class DailyJob  {
	
	
	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	private final long PERIOD=86_400_000;
	
	
	/**
	 * Runs in repeated circles once in 24 hours ,picks up and deactivates expired 
	 * coupons and these coupon's purchase history
	 */
	@Scheduled(fixedRate = PERIOD)
	public void deleteExpiredCoupons() {
		
		Date now=new Date(System.currentTimeMillis());
		
		List<Coupon> expiredCoupons=couponRepository.findByEndDateBefore(now);
		List<Purchase> overAllPurchases=new ArrayList<>();
		
		for(Coupon coupon:expiredCoupons) {
			
			coupon.setIsActive(false);
			List<Purchase> couponPurchaseHistory=purchaseRepository.findByCouponId(coupon.getId());
			for(Purchase purchase:couponPurchaseHistory) {
				purchase.setActive(false);
			}
				
			overAllPurchases.addAll(couponPurchaseHistory);
						
		}
		
		couponRepository.saveAll(expiredCoupons);
		purchaseRepository.saveAll(overAllPurchases);	
	}
	
	

}
