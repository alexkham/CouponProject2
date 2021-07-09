package spring_boot_coupon_system.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	@Id
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@OneToMany
	private Collection<Coupon> coupons;
	private Boolean isActive;

}
