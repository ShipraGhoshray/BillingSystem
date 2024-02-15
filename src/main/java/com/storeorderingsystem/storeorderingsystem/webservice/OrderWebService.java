package com.storeorderingsystem.storeorderingsystem.webservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storeorderingsystem.storeorderingsystem.model.Bill;
import com.storeorderingsystem.storeorderingsystem.model.BillAmount;
import com.storeorderingsystem.storeorderingsystem.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderWebService {
	
	private final OrderService orderService;
	
	public OrderWebService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("/generateBillAmount")
    public BillAmount generateBillAmount(@RequestBody Bill bill){
        return this.orderService.processBill(bill);
    }
}
