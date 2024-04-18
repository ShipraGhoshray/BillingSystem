
package com.storeorderingsystem.storeorderingsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.model.User;
import com.storeorderingsystem.storeorderingsystem.products.service.impl.ProductsServiceImpl;
import com.storeorderingsystem.storeorderingsystem.service.impl.RoleServiceImpl;
import com.storeorderingsystem.storeorderingsystem.service.impl.UserServiceImpl;
import com.storeorderingsystem.storeorderingsystem.util.Constants;
import com.storeorderingsystem.storeorderingsystem.util.DateUtils;

@SpringBootApplication
public class StoreOrderingSystemApplication implements CommandLineRunner{

	Logger log = LoggerFactory.getLogger(StoreOrderingSystemApplication.class);
	
	private final UserServiceImpl userService;
    private final ProductsServiceImpl productsService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    
    public StoreOrderingSystemApplication(PasswordEncoder passwordEncoder, ProductsServiceImpl productsService, 
    		RoleServiceImpl roleService, UserServiceImpl userService) {
    	this.passwordEncoder = passwordEncoder;
    	this.productsService = productsService;
    	this.roleService = roleService;
    	this.userService = userService;
    }
    
	public static void main(String[] args) {
		//DateUtils.isDateGreatThanTwoYears(DateUtils.)
		SpringApplication.run(StoreOrderingSystemApplication.class, args);
		try {
			//JSONObject parentJson = new JSONObject();
			/*ItemQuantity item = new ItemQuantity(1L, "Bread", 10, 5, Constants.ITEM_TYPE_GROCERIES);
			ObjectMapper objectMapper = new ObjectMapper () ;
			File itemFile = new File("target/itemJson.json");
			objectMapper.writeValue (itemFile, item);
			
			ItemQuantity result = objectMapper.readValue(itemFile, ItemQuantity.class);
			System.out.println(result);
				
			String json = "{ \"itemId\" : \"2\", \"name\": \"Milk\", \"price\": \"33\"}";
			result = objectMapper.readValue(json, ItemQuantity.class);
			System.out.println(result);
			
		} catch (StreamWriteException | DatabindException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		*/}  catch (Exception e) {
			e.printStackTrace();
		} 		
	}

	@Override
	public void run(String... args) throws Exception {
		/*createInventory();
		addRoles();
		createUsers();*/
		Iterable<Role> roles = roleService.lookup();
		log.info("User-Roles in the system: ");
		for(Role role: roles) {
			log.info(role.getName());
		}

		long userCount = userService.total();
		log.info("Total users in the system is : "+ userCount);
		Iterable<User> userList = userService.lookup();
		log.info("Users in the system: ");
		for(User user: userList) {
			log.info(user.getUsername() + "--" + user.getJoiningDate());
		}

		long inventoryCount = productsService.total();
		log.info("Total inventory in the system is : "+ inventoryCount);
	}
	
	private void addRoles() {
		roleService.addRole(1, Constants.USER_ROLE_ADMIN);
		roleService.addRole(2, Constants.USER_ROLE_USER);
		roleService.addRole(3, Constants.USER_ROLE_AFFILIATE);
	}
	
	private void createUsers() {
		
		userService.createStoreUser("joey", passwordEncoder.encode("password"), 
			roleService.getRolesByName(Constants.USER_ROLE_ADMIN, Constants.USER_ROLE_USER), 
			"Joey", "Tribiani",  "joey.tribiani@gmail.com", 971565678901L, DateUtils.getCurrentDate());
		
		userService.createStoreUser("rachel", passwordEncoder.encode("password"), roleService.getRolesByName(Constants.USER_ROLE_USER),
			"Rachel", "Green",  "Rachel.Green@gmail.com", 971566789012L, DateUtils.getCurrentDate());

		//userService.createStoreUser(3L, Constants.USER_ROLE_CUSTOMER, "Ross", "Gellar", "Ross.Gellar@gmail.com", 971567890123L);
		//userService.createStoreUser(4L, Constants.USER_ROLE_CUSTOMER, "Monica",	"Gellar", "Monica.Gellar@gmail.com", 971568901234L);
		//userService.createStoreUser(5L, Constants.USER_ROLE_CUSTOMER, "Chandler", "Bing", "Chandler.Bing@gmail.com", 971560123456L);
	}

	private void createInventory(){
		productsService.createProducts("Bread", 10, Constants.PRODUCT_TYPE_GROCERIES);
		productsService.createProducts("Eggs", 10, Constants.PRODUCT_TYPE_GROCERIES);
		productsService.createProducts("Milk", 10, Constants.PRODUCT_TYPE_GROCERIES);
		productsService.createProducts("Chicken", 12, Constants.PRODUCT_TYPE_GROCERIES);
		productsService.createProducts("Garbage Bags", 32, Constants.PRODUCT_TYPE_UTILITIES);
		productsService.createProducts("Shampoo", 10, Constants.PRODUCT_TYPE_UTILITIES);
		productsService.createProducts("Stapler", 10, Constants.PRODUCT_TYPE_STATIONARY);
	}
}