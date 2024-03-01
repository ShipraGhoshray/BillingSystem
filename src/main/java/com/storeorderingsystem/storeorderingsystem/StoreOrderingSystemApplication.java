package com.storeorderingsystem.storeorderingsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.storeorderingsystem.storeorderingsystem.service.impl.ItemInventoryServiceImpl;
import com.storeorderingsystem.storeorderingsystem.service.impl.UserServiceImpl;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

@SpringBootApplication
//@ComponentScan(basePackageClasses=OrderWebService.class)
public class StoreOrderingSystemApplication implements CommandLineRunner{

	private UserServiceImpl userService;
    private ItemInventoryServiceImpl itemInventoryService;
    
    public StoreOrderingSystemApplication(ItemInventoryServiceImpl itemInventoryService, UserServiceImpl userService) {
    	this.itemInventoryService = itemInventoryService;
    	this.userService = userService;
    }
    
	public static void main(String[] args) {
		//DateUtils.isDateGreatThanTwoYears(DateUtils.)
		SpringApplication.run(StoreOrderingSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create item inventory here
		createItemInventory();
		createStoreUsers();
		long inventoryCount = itemInventoryService.total();
		System.out.println("Total inventory in the system is : "+ inventoryCount);
		
		long userCount = userService.total();
		System.out.println("Total users in the system is : "+ userCount);
		
	}

	private void createItemInventory(){
		itemInventoryService.createItemInventory(1L, "Bread", 10, 5, Constants.ITEM_TYPE_GROCERIES);
		itemInventoryService.createItemInventory(2L, "Eggs", 10, 2, Constants.ITEM_TYPE_GROCERIES);
		itemInventoryService.createItemInventory(3L, "Milk", 10, 1, Constants.ITEM_TYPE_GROCERIES);
		itemInventoryService.createItemInventory(4L, "Chicken", 1, 5, Constants.ITEM_TYPE_GROCERIES);
		itemInventoryService.createItemInventory(5L, "Garbage Bags", 3, 5, Constants.ITEM_TYPE_UTILITIES);
		itemInventoryService.createItemInventory(6L, "Shampoo", 10, 1, Constants.ITEM_TYPE_UTILITIES);
		itemInventoryService.createItemInventory(7L, "Stapler", 10, 1, Constants.ITEM_TYPE_STATIONARY);
	}
	
	private void createStoreUsers() {
		
		userService.createStoreUser(1L, Constants.USER_TYPE_EMPLOYEE, "Joey", "Tribiani", "Joey.Tribiani@gmail.com", 971565678901L);
		userService.createStoreUser(2L, Constants.USER_TYPE_AFFILIATE, "Rachel", "Green", "Rachel.Green@gmail.com", 971566789012L);
		userService.createStoreUser(3L, Constants.USER_TYPE_CUSTOMER, "Ross", "Gellar", "Ross.Gellar@gmail.com", 971567890123L);
		userService.createStoreUser(4L, Constants.USER_TYPE_CUSTOMER, "Monica",	"Gellar", "Monica.Gellar@gmail.com", 971568901234L);
		userService.createStoreUser(5L, Constants.USER_TYPE_CUSTOMER, "Chandler", "Bing", "Chandler.Bing@gmail.com", 971560123456L);
	}
}
	