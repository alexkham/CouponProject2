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
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Entity
@Data
@Table(name = "purchases")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Coupon coupon;
	
	private boolean isActive;
	
	private Date purchaseDate;
	
	
	
	
	
	

}
