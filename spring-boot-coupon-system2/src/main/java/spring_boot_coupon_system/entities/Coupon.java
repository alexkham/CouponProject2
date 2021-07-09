package spring_boot_coupon_system.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long companyId;
	private Long categoryId;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Integer quantity;
	private Double unitPrice;
	private String imageUrl;
	private Boolean isActive;
	

}
