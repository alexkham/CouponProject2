package spring_boot_coupon_system.services;

import spring_boot_coupon_system.exceptions.CouponSystemException;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
public interface ClientLoginService {
	
	public abstract boolean login(Long clientId,String email,String password) throws CouponSystemException;

}
