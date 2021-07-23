package spring_boot_coupon_system.services;

import spring_boot_coupon_system.exceptions.CouponSystemException;

public interface ClientLoginService {
	
	public abstract boolean login(Long clientId,String email,String password) throws CouponSystemException;

}
