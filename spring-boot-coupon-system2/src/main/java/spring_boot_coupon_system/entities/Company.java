package spring_boot_coupon_system.entities;


import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */

@Entity
@Table(name="Companies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyId;
	private String name;
	private String email;
	private String password;
	private Boolean isActive;
	@OneToMany(fetch = FetchType.EAGER) // cascade = CascadeType.MERGE)
	@Singular
	//@ToString.Exclude
	private List<Coupon> companyCoupons=new ArrayList<>();

}
