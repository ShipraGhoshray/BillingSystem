package com.storeorderingsystem.storeorderingsystem.products.controller;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

import com.storeorderingsystem.storeorderingsystem.authentication.model.User;
import com.storeorderingsystem.storeorderingsystem.authentication.service.UserService;
import com.storeorderingsystem.storeorderingsystem.products.model.Bill;
import com.storeorderingsystem.storeorderingsystem.products.model.BillAmount;
import com.storeorderingsystem.storeorderingsystem.products.repository.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.products.service.BillProcessingService;
import com.storeorderingsystem.storeorderingsystem.products.service.ProductsService;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	
	Logger log = LoggerFactory.getLogger(ProductController.class);
	
	private final UserService userService;
	private final BillProcessingService billProcessingService;
	private final ProductsService itemInventoryService;
	
	public ProductController(UserService userService, BillProcessingService billProcessingService, ProductsService itemInventoryService) {
		this.userService = userService;
		this.billProcessingService = billProcessingService;
		this.itemInventoryService = itemInventoryService;
	}
	
	@GetMapping("/inventory")
    public Collection<ItemQuantity> getItemInventoryReact(){
        return (Collection<ItemQuantity>) this.itemInventoryService.lookup();
    }
	
	@GetMapping("/users")
	public ResponseEntity getUsers(){
		List<User> userList = (List<User>) this.userService.lookup();
		log.info("User List size:" + userList.size());
		if(userList != null && userList.size() > 0) {
			return new ResponseEntity<Iterable<User>>(userList, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(Constants.RESPONSE_MSG_NO_DATA_FOUND, HttpStatus.OK);
		}
    }
	
	@GetMapping("/users/{userID}")
    public ResponseEntity getUserById(@Validated String userIdReq){
		Optional<User> user = userService.findById(Long.valueOf(userIdReq));
		if(user != null) {
			return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(Constants.RESPONSE_MSG_NO_DATA_FOUND, HttpStatus.OK);
		}
    }

	@PostMapping("/products")
    public ResponseEntity<String> createNewProduct(@RequestBody ItemQuantity item){
        ItemQuantity product = this.itemInventoryService.createItemInventory(item.getItemId(), item.getName(), item.getPrice(), item.getQuantity(), item .getType());
        if(product != null) {
            return new ResponseEntity<String>(Constants.RESPONSE_PRODUCT_CREATED, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(Constants.RESPONSE_PRODUCT_CREATED_FAILED, HttpStatus.OK);
        }
    }
	
	@PostMapping("/createNewItemAPI")
	ResponseEntity<ItemQuantity> createNewItemAPI(@Validated @RequestBody ItemQuantity item) throws URISyntaxException {
		ItemQuantity result = itemInventoryService.save(item);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/generateBillAmount")
    public BillAmount generateBillAmount(@RequestBody Bill bill){
        return this.billProcessingService.processBill(bill);
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
    		User user = verifyUser(userId);
        	userService.delete(user);
    	}catch(NoSuchElementException e) {
    		System.out.println("User does not exist");
    	}
    }
    
    private User verifyUser(String userIdReq) throws NoSuchElementException {
    	long userId = Long.valueOf(userIdReq);
		return userService.findById(userId).orElseThrow(() -> new NoSuchElementException("User for request("+ userId));
	}
}