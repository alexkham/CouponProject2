package spring_boot_coupon_system.bootstrap;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ch.qos.logback.core.util.Duration;
import spring_boot_coupon_system.entities.Category;
import spring_boot_coupon_system.entities.Company;
import spring_boot_coupon_system.entities.Coupon;
import spring_boot_coupon_system.entities.Customer;

public class TestUtils {


	protected static String starSeparator=printManyTimes(50, "*");
	protected static String warning=printManyTimes(50, "!");
	protected static String simpleSeparator=printManyTimes(50,"-");
	protected static int couponCount;
	protected static  int companiesCount;
	protected static int customerCount;
	protected static Date now=new Date(System.currentTimeMillis());
	protected static Random random=new Random();


	public static Company createRandomCompanyNoCoupons() {

		Random random=new Random();
		String companyName="Company"+random.nextInt(1000000);
		String companyEmail=createRandomEmail();
		String password=createRandomString(10).toLowerCase();
		boolean  isActive=true;

		return  Company.builder()
				.name(companyName)
				.email(companyEmail)
				.password(password)
				.isActive(true)
				.companyCoupons( new ArrayList<Coupon>())
				.build(); 

	}

	public static Customer createRandomCustomer() {
		String firstName=createRandomString(7);
		String lastName=createRandomString(10);
		String email=createRandomEmail();
		String password=createRandomString(6);
		boolean isActive=true;
		


		return Customer.builder()
				.firstName(createRandomString(new Random().nextInt(10)+2))
				.lastName(createRandomString(new Random().nextInt(10)+2))
				.email(createRandomEmail())
				.password(createRandomString(new Random().nextInt(10)+2))
				.isActive(true)
				.coupons(new ArrayList<Coupon>())
				.build();
	}

	public static Category createRandomCategory() {

		return Category.builder()
				.categoryName(createRandomString(new Random().nextInt(7)))
				.build();

	}

	public static Coupon createRandomCoupon(Company company,Category category) {

		Random random=new 	Random();

		String title=createRandomString(8);
		String description=createRandomString(12);
		boolean isActive=true;
		String imageUrl=createRandomString(10);
		double unitPrice=random.nextDouble();
		int quantity=random.nextInt(10000);
		Date startDate= TestUtils.now;
		Date endDate= createFuturePastDate(random.nextInt(2), random.nextInt(12), random.nextInt(30));


		return Coupon.builder()
				.title(createRandomString(random.nextInt(5)+3))
				.description(createRandomString(random.nextInt(5)+3))
				.startDate(startDate)
				.endDate(endDate)
				.isActive(true)
				.imageUrl(createRandomString(random.nextInt(10)+3))
				.quantity(random.nextInt(100))
				.unitPrice(random.nextDouble()*10)
				.category(category)
				.company(company)
				.build(); 
	}

	public static List<Coupon> createCompanyCoupons(int howMany, Company company){
        Category category=createRandomCategory();
		List<Coupon> companyCoupons=new ArrayList<>();
		for(int i=0;i<howMany;i++)
			companyCoupons.add(createRandomCoupon(company,category));

		return companyCoupons;

	}

	private static String printManyTimes(int num,String text) {
		String myStr="";
		for(int i=0;i<num;i++) {
			myStr+=text;
		}
		return myStr;
	}
	/**
	 * Generates random string
	 * @param num Number of characters expected
	 * @return
	 */
	public static String createRandomString(int num) {
		Random random=new Random();
		String alphabet="abcdefghijklmnopqrstuvwxyz";
		String myString="";
		for(int i=0;i<num;i++) {
			myString+=alphabet.charAt(random.nextInt(26));
		}
		myString=myString.substring(0,1).toUpperCase().concat(myString.substring(1));

		return myString;

	}
	/**
	 * Generates random string in email format
	 * @return String dummy non existing email like string
	 */
	public static String createRandomEmail() {
		String email=createRandomString(new Random().nextInt(10)+2).toLowerCase();

		email+="@";

		email+=createRandomString(new Random().nextInt(5)+2).toLowerCase();

		email+=".com";


		return email;

	}

	/**
	 * Creates random date either in future or in past depending on parameter values(negative parameters will create past date)
	 * @param years
	 * @param months
	 * @param days
	 * @return
	 */
	public static Date createFuturePastDate(int years,int months,int days) {

		long aDay=TimeUnit.DAYS.toMillis(1);
		long aMonth=TimeUnit.DAYS.toMillis(30);
		long aYear=TimeUnit.DAYS.toMillis(365);
		Date date=new Date(System.currentTimeMillis()+aYear*years+aMonth*months+aDay*days);


		return date;

	}



}
