package com.storeorderingsystem.storeorderingsystem.webservice;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storeorderingsystem.storeorderingsystem.model.Bill;
import com.storeorderingsystem.storeorderingsystem.model.BillAmount;
import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUser;
import com.storeorderingsystem.storeorderingsystem.service.BillProcessingService;
import com.storeorderingsystem.storeorderingsystem.service.ItemInventoryService;
import com.storeorderingsystem.storeorderingsystem.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderWebService {
	
	private final UserService userService;
	private final BillProcessingService billProcessingService;
	private final ItemInventoryService itemInventoryService;
	
	public OrderWebService(UserService userService, BillProcessingService billProcessingService, ItemInventoryService itemInventoryService) {
		this.userService = userService;
		this.billProcessingService = billProcessingService;
		this.itemInventoryService = itemInventoryService;
	}
	
	@GetMapping("/itemInventory")
    public Iterable<ItemQuantity> getItemInventory(){
        return this.itemInventoryService.lookup();
    }
	
	@GetMapping("/itemInventoryApi")
    public Collection<ItemQuantity> getItemInventoryReact(){
        return (Collection<ItemQuantity>) this.itemInventoryService.lookup();
    }
	
	@GetMapping("/userDetails")
    public Iterable<StoreUser> getUsers(){
        return this.userService.lookup();
    }
	
	@GetMapping("/userDetails/{userID}")
    public Optional<StoreUser> getUserById(String userIdReq){
		long userId = Long.valueOf(userIdReq);
		return userService.findById(userId);
    }
	
	@PostMapping("/generateBillAmount")
    public BillAmount generateBillAmount(@RequestBody Bill bill){
        return this.billProcessingService.processBill(bill);
    }
	
	@PostMapping("/createNewItem")
    public ItemQuantity createNewItem(@RequestBody ItemQuantity item){
        return this.itemInventoryService.createItemInventory(item.getItemId(), item.getName(), item.getPrice(), item.getQuantity(), item .getType());
    }
	
	@PostMapping("/createNewItemAPI")
	ResponseEntity<ItemQuantity> createNewItemAPI(@Validated @RequestBody ItemQuantity item) throws URISyntaxException {
		ItemQuantity result = itemInventoryService.save(item);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/items/{itemId}/details")
	public ItemQuantity updateWithPut(@PathVariable(value = "itemId") String itemId, @RequestBody @Validated ItemQuantity itemRequest) {
		ItemQuantity item = verifyItem(itemId, itemRequest.getName());
		item.setPrice(itemRequest.getPrice());
		return itemInventoryService.save(item);
	}

	private ItemQuantity verifyItem(String itemIdReq, String name) throws NoSuchElementException {
		long itemId = Long.valueOf(itemIdReq);
		return itemInventoryService.findById(itemId, name).orElseThrow(() -> new NoSuchElementException("Item for request("+ itemId + " for name" + name));
	}

    @PatchMapping("/items/{itemId}/details")
    public ItemQuantity updateWithPatch(@PathVariable(value = "itemId") String itemId, @RequestBody @Validated ItemQuantity itemRequest) {
    	try {
        	ItemQuantity item = verifyItem(itemId, itemRequest.getName());
        	item.setPrice(itemRequest.getPrice());
        	return itemInventoryService.save(item);
    	}catch(NoSuchElementException e) {
    		return null;
    	}
    }

    @DeleteMapping(path = "deleteUser/{userId}")
    public void delete(@PathVariable(value = "userId") String userId) {
    	try {
    		StoreUser user = verifyUser(userId);
        	userService.delete(user);
    	}catch(NoSuchElementException e) {
    		System.out.println("User does not exist");
    	}
    }
    
    private StoreUser verifyUser(String userIdReq) throws NoSuchElementException {
    	long userId = Long.valueOf(userIdReq);
		return userService.findById(userId).orElseThrow(() -> new NoSuchElementException("User for request("+ userId));
	}
}
