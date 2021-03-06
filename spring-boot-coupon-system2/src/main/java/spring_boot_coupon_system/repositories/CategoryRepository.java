package spring_boot_coupon_system.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring_boot_coupon_system.entities.Category;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	
}
