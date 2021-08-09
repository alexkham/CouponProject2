package spring_boot_coupon_system.entities;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Coupons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne//(cascade = CascadeType.MERGE)
	@ToString.Exclude
	private Company company;
	@ManyToOne(cascade = CascadeType.MERGE)
	@ToString.Exclude
	private Category category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int quantity;
	private double unitPrice;
	private String imageUrl;
	private Boolean isActive;
	

}
