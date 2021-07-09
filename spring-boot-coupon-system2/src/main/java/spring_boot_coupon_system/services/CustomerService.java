package spring_boot_coupon_system.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import spring_boot_coupon_system.entities.Customer;

@Service
@Transactional
public class CustomerService extends GeneralService {
	
	public void addNewCustomer(Customer customer) {
		customerRepository.save(customer);
	}

}
