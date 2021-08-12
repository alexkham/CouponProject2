package spring_boot_coupon_system.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.exceptions.ErrorMessages;
/**
 * @author  Alex Khalamsky id 307767483
 * @version August 2021
 * 
 */
@Service
public class LoginManager {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CustomerService customerService;
	
	
	/**
	 * Performs client verification and check's credentials before allowing access to a facade layer of the app
	 * @param clientId Long Id number of user attempting to perform the action
	 * @param email String email address
	 * @param password String password
	 * @param userType enum value of type UserType
	 * @return ClientFacade object which fits the client who passed login procedure
	 * @throws CouponSystemException
	 */
	public ClientService login(Long clientId ,String email, String password, UserType userType) 
			throws CouponSystemException {
		
		ClientService clientService=null;
		
		switch (userType) {
		
		
		case ADMIN:
			
			if(adminService.login(email, password)) 
				clientService= adminService;
			
			
			break;
			
         case COMPANY:
        	 if(companyService.login(clientId,email, password))
        		 clientService=companyService;
			
			break;
			
         case CUSTOMER:
        	 if(customerService.login(clientId,email, password))
        		 clientService=customerService;
 			
 			break;	

		default:throw new CouponSystemException(ErrorMessages.WRONG_DATA_PROVIDED);
		}
		
		return clientService;
		
	}

}
