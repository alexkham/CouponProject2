package spring_boot_coupon_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
