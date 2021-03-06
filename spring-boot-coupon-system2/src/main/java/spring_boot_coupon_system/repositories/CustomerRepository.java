package spring_boot_coupon_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Customer;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	List<Customer> findByIsActiveTrue();
	
	Customer findByEmail(String email);
	

}
