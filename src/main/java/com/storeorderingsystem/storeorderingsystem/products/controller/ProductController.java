package com.storeorderingsystem.storeorderingsystem.products.controller;

import java.net.URISyntaxException;
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

import com.storeorderingsystem.storeorderingsystem.products.dto.BillAmountDto;
import com.storeorderingsystem.storeorderingsystem.products.dto.BillDto;
import com.storeorderingsystem.storeorderingsystem.products.dto.ProductsDto;
import com.storeorderingsystem.storeorderingsystem.products.model.Products;
import com.storeorderingsystem.storeorderingsystem.products.service.BillProcessingService;
import com.storeorderingsystem.storeorderingsystem.products.service.ProductsService;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	
	Logger log = LoggerFactory.getLogger(ProductController.class);
	
	private final BillProcessingService billProcessingService;
	private final ProductsService productService;
	
	public ProductController(BillProcessingService billProcessingService, ProductsService productService) {
		this.billProcessingService = billProcessingService;
		this.productService = productService;
	}
	
	@PostMapping("/products")
    public ResponseEntity<String> createNewProduct(@RequestBody ProductsDto productReq){
        Products product = this.productService.createProducts(productReq.getName(), productReq.getPrice(), productReq .getType());
        if(product != null) {
            return new ResponseEntity<String>(Constants.RESPONSE_PRODUCT_CREATED, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(Constants.RESPONSE_PRODUCT_CREATED_FAILED, HttpStatus.OK);
        }
    }
	
	@GetMapping("/products")
    public Collection<Products> getProductsReact(){
        return (Collection<Products>) this.productService.lookup();
    }
    
    @PostMapping("/createNewProductAPI")
	ResponseEntity<Products> createNewProductAPI(@Validated @RequestBody Products product) throws URISyntaxException {
    	Products result = productService.save(product);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/bill")
    public BillAmountDto generateBillAmount(@RequestBody BillDto bill){
        return this.billProcessingService.processBill(bill);
    }
	
	@PutMapping("/products/{productId}/details")
	public Products updateWithPut(@PathVariable(value = "productId") String productId, @RequestBody @Validated Products productRequest) {
		Products product = verifyProduct(productId, productRequest.getName());
		product.setPrice(productRequest.getPrice());
		return productService.save(product);
	}

	private Products verifyProduct(String productIdReq, String name) throws NoSuchElementException {
		long productId = Long.valueOf(productIdReq);
		return productService.findById(productId, name).orElseThrow(() -> new NoSuchElementException("product for request("+ productId + " for name" + name));
	}

    @PatchMapping("/products/{productId}/details")
    public Products updateWithPatch(@PathVariable(value = "productId") String productId, @RequestBody @Validated Products productRequest) {
    	try {
        	Products product = verifyProduct(productId, productRequest.getName());
        	product.setPrice(productRequest.getPrice());
        	return productService.save(product);
    	}catch(NoSuchElementException e) {
    		return null;
    	}
    }
}