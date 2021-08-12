package spring_boot_coupon_system.exceptions;

/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */

public class CouponSystemException extends Exception {

	public CouponSystemException() {
		super();
	}

	public CouponSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CouponSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponSystemException(ErrorMessages errorMessage) {
		super(errorMessage.getMessage());
	}
	
	public CouponSystemException(String message) {
		super(message);
	}

	public CouponSystemException(Throwable cause) {
		super(cause);
	}
	
	

}
