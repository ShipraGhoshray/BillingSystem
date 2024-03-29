package com.storeorderingsystem.storeorderingsystem.products.controller;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storeorderingsystem.storeorderingsystem.products.dto.Bill;
import com.storeorderingsystem.storeorderingsystem.products.dto.BillAmount;
import com.storeorderingsystem.storeorderingsystem.products.model.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.products.service.BillProcessingService;
import com.storeorderingsystem.storeorderingsystem.products.service.ProductsService;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	
	Logger log = LoggerFactory.getLogger(ProductController.class);
	
	private final BillProcessingService billProcessingService;
	private final ProductsService itemInventoryService;
	
	public ProductController(BillProcessingService billProcessingService, ProductsService itemInventoryService) {
		this.billProcessingService = billProcessingService;
		this.itemInventoryService = itemInventoryService;
	}
	
	@GetMapping("/products")
    public Collection<ItemQuantity> getItemInventoryReact(){
        return (Collection<ItemQuantity>) this.itemInventoryService.lookup();
    }
	
	@PostMapping("/products")
    public ResponseEntity<String> createNewProduct(@Validated @RequestBody ItemQuantity item){
        ItemQuantity product = this.itemInventoryService.createItemInventory(item.getItemId(), item.getName(), item.getPrice(), item.getQuantity(), item .getType());
        if(product != null) {
            return new ResponseEntity<String>(Constants.RESPONSE_PRODUCT_CREATED, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(Constants.RESPONSE_PRODUCT_CREATED_FAILED, HttpStatus.OK);
        }
    }
	
	/*@PostMapping("/createNewItemAPI")
	ResponseEntity<ItemQuantity> createNewItemAPI(@Validated @RequestBody ItemQuantity item) throws URISyntaxException {
		ItemQuantity result = itemInventoryService.save(item);
		return ResponseEntity.ok().body(result);
	}*/
	
	@PostMapping("/bill")
    public BillAmount generateBillAmount(@RequestBody Bill bill){
        return this.billProcessingService.processBill(bill);
    }
	
	@PutMapping("/products/{productId}/details")
	public ItemQuantity updateWithPut(@PathVariable(value = "productId") String itemId, @RequestBody @Validated ItemQuantity itemRequest) {
		ItemQuantity item = verifyItem(itemId, itemRequest.getName());
		item.setPrice(itemRequest.getPrice());
		return itemInventoryService.save(item);
	}

	private ItemQuantity verifyItem(String itemIdReq, String name) throws NoSuchElementException {
		long itemId = Long.valueOf(itemIdReq);
		return itemInventoryService.findById(itemId, name).orElseThrow(() -> new NoSuchElementException("Item for request("+ itemId + " for name" + name));
	}

    @PatchMapping("/products/{productId}/details")
    public ItemQuantity updateWithPatch(@PathVariable(value = "productId") String itemId, @RequestBody @Validated ItemQuantity itemRequest) {
    	try {
        	ItemQuantity item = verifyItem(itemId, itemRequest.getName());
        	item.setPrice(itemRequest.getPrice());
        	return itemInventoryService.save(item);
    	}catch(NoSuchElementException e) {
    		return null;
    	}
    }
}