/**
* Author: Alex Khalamsky id 307767483
**/



Starting the application  does not require entering  user name or password by user at the moment;


The configuration parameters are loaded into java properties and extracted by respective objects automatically
when those (objects) are created ,thus at the current stage,everything is done in automatic and autonomic order without
need in any participation from the client's side


The Application can be started  by launching /spring-boot-coupon-system2/src/main/java/spring_boot_coupon_system/SpringBootCouponSystemApplication.java
file as java application
At start the initiation modules and test units will run automatically
The initiation and test results will be printed to the console
 
 The application will create initial data and populate the database at the beginning 
 Initiation modules are located in package:
 spring_boot_coupon_system.bootstrap.init
 
 Testing methods created for this project are located in package
 
 spring_boot_coupon_system.bootstrap.test
 
 Additional APIs used in this project:
 
 JUnit5 Jupiter;
 