package spring_boot_coupon_system.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Category {
	
	
	@Id
	private Long id;
	private String categoryName;

}
