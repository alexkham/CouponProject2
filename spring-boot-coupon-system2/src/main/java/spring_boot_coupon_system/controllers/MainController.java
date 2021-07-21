package spring_boot_coupon_system.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
public class MainController {
	
	@GetMapping
	public String getCoupon( ) {
		return "Coupons";
	}
	
	
	@PostMapping
	public String getCouponPost( ) {
		return "Coupons+posts";
	}

}
