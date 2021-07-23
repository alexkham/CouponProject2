package spring_boot_coupon_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring_boot_coupon_system.exceptions.CouponSystemException;
import spring_boot_coupon_system.exceptions.ErrorMessages;

@Service
public class LoginManager {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CustomerService customerService;
	
	
	
	public ClientService login(Long clientId ,String email, String password, UserType userType) throws CouponSystemException {
		
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
