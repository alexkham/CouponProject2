package spring_boot_coupon_system.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import spring_boot_coupon_system.repositories.CouponRepository;

@Service
@Transactional
public class CouponService extends GeneralService {
	
	public static void createNewCoupon() {
		
	}

}
