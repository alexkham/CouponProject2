package spring_boot_coupon_system.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

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
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "Companies")
	@Singular
	@ToString.Exclude
	private List<Coupon> companyCoupons=new ArrayList<>();

}
