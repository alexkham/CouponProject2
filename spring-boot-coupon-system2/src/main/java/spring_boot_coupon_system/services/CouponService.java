package spring_boot_coupon_system.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import spring_boot_coupon_system.repositories.CouponRepository;

@Service
@Transactional
public class CouponService extends ClientService {
	
	public static void createNewCoupon() {
		
	}

	@Override
	public boolean login(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
