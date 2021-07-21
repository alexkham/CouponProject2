package spring_boot_coupon_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
