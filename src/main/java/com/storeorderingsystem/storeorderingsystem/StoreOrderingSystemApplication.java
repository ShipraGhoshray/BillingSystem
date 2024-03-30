
package com.storeorderingsystem.storeorderingsystem;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.storeorderingsystem.storeorderingsystem.authentication.model.Role;
import com.storeorderingsystem.storeorderingsystem.authentication.service.impl.RoleServiceImpl;
import com.storeorderingsystem.storeorderingsystem.authentication.service.impl.UserServiceImpl;
import com.storeorderingsystem.storeorderingsystem.products.service.impl.ProductsServiceImpl;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

@SpringBootApplication
public class StoreOrderingSystemApplication implements CommandLineRunner{

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
		createInventory();
		addRoles();
		createUsers();
		long inventoryCount = productsService.total();
		System.out.println("Total inventory in the system is : "+ inventoryCount);
		
		long userCount = userService.total();
		System.out.println("Total users in the system is : "+ userCount);
	}
	
	private void addRoles() {
		roleService.addRole(1, "ADMIN", null);
		roleService.addRole(2, "USER", null);
		roleService.addRole(3, "AFFILIATE", null);
	}
	
	private void createUsers() {
		
		userService.createStoreUser(1L, 
				List.of(new Role(1, Constants.USER_ROLE_ADMIN),
						new Role(2, Constants.USER_ROLE_USER)), 
				"Joey", "Tribiani", "joey", passwordEncoder.encode("password") , "joey.tribiani@gmail.com", 971565678901L);
		userService.createStoreUser(2L, List.of(new Role(1, Constants.USER_ROLE_USER)), 
				"Rachel", "Green", "rachel", passwordEncoder.encode("password"), "Rachel.Green@gmail.com", 971566789012L);
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
	