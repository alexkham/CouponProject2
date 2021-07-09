package spring_boot_coupon_system.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public class DailyJob {
	
	
	private final long PERIOD=86_400_000;
	
	@Scheduled(fixedRate = PERIOD)
	public void deleteExpiredCoupons() {
		
	}
	
	public static void newOne() {
		
	System.out.println("New one");
	
	var x="Very new";
	
	var y="the very .... new";
		
	}

}
