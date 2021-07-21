package spring_boot_coupon_system.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@OneToMany(cascade = CascadeType.PERSIST)
	@ToString.Exclude
	private List<Coupon> coupons=new ArrayList<>();
	private Boolean isActive;

}
