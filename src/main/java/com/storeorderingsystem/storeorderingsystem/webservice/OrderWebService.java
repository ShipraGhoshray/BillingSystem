package com.storeorderingsystem.storeorderingsystem.webservice;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.storeorderingsystem.storeorderingsystem.model.Bill;
import com.storeorderingsystem.storeorderingsystem.model.BillAmount;
import com.storeorderingsystem.storeorderingsystem.service.OrderServiceImpl;

@RestController
@RequestMapping("/api")
public class OrderWebService {
	
	private final OrderServiceImpl orderServiceImpl;
	
	public OrderWebService(OrderServiceImpl orderServiceImpl) {
		this.orderServiceImpl = orderServiceImpl;
	}
	
	@RequestMapping(value = "/getBillAmount/{Bill}", method = RequestMethod.POST)
    //@ResponseStatus(HttpStatus.CREATED)
    public BillAmount getBillDetails(@RequestBody Bill bill){
        return this.orderServiceImpl.processBill(bill);
    }
	
}
