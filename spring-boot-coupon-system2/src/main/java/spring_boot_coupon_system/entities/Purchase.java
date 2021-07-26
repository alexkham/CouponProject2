package spring_boot_coupon_system.entities;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customers_vs_coupons")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Coupon coupon;
	
	private boolean isActive;
	
	private Date purchaseDate;
	
	
	
	
	
	

}
