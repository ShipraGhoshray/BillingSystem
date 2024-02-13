package com.storeorderingsystem.storeorderingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.storeorderingsystem.storeorderingsystem.model.Bill;
import com.storeorderingsystem.storeorderingsystem.model.BillAmount;
import com.storeorderingsystem.storeorderingsystem.model.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.repository.BillRepository;
import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantityRepository;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUser;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUserRepository;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

public class OrderServiceImpl implements OrderService{

	private RestTemplate restTemplate;
	private BillRepository billRepository;
	private StoreUserRepository  storeUserRepository;
	
	@Autowired
	private ItemQuantityRepository  itemQuantityRepository;
	
	@Autowired
	public OrderServiceImpl(RestTemplate restTemplate, BillRepository billRepository, ItemQuantityRepository itemQuantityRepository) {
		this.restTemplate = restTemplate;
		this.billRepository = billRepository;
		this.itemQuantityRepository = itemQuantityRepository;
	}

	@Override
	public BillAmount processBill(Bill billInfo) {
		
        System.out.println("Process Bill");
        double billAmount = 0.0;
        
        if(billInfo!= null) {
        	if(billInfo.getItems()!= null && billInfo.getItems().size() > 0) {
        		for(ItemQuantity item:billInfo.getItems()) {
        			billAmount = billAmount + ( item.getPrice() * item.getQuantity() );
        			//itemQuantityRepository.save(item);
        		}
        	}
        	
        	if(billInfo.getStoreUserId() != null) {
        		double discountAmt = getDiscountAmountOnBill(billInfo.getStoreUserId(), billAmount);
        		billAmount = billAmount - discountAmt ;
        	}
        	
        	if(Double.compare(billAmount, 0) > 0) {
            	BillAmount billAmountObj = new BillAmount();
            	billAmountObj.setBillId(billInfo.getBillId());
            	billAmountObj.setBillAmount(billAmount); 
            	//billRepository.save(billInfo);
            	return billAmountObj;
        	}
        }else {
            String errorMessage = "Failed to process bill";
            sendErrorMessage(errorMessage);
        }
        return null;
   }

	private double getDiscountAmountOnBill(String userId, double billAmount) {
		
		double discountAmount = 0.0;
		boolean discountApplied = false;
		StoreUser user = storeUserRepository.findById(Long.valueOf(userId)).get();
		if(user.getUserType() != null) {

			if(user.getUserType().equalsIgnoreCase(Constants.USER_TYPE_EMPLOYEE)) {
				discountAmount = Constants.USER_TYPE_EMPLOYEE_DISCOUNT_PERCENT / 100 * billAmount;
				discountApplied = true;
				
			}else if(user.getUserType().equalsIgnoreCase(Constants.USER_TYPE_AFFILIATE)){
				discountAmount = Constants.USER_TYPE_AFFILIATE_DISCOUNT_PERCENT / 100 * billAmount;
				discountApplied = true;
			
			}else if(user.getJoiningDate() != null){
				discountAmount = Constants.USER_TYPE_2YEAR_DISCOUNT_PERCENT / 100 * billAmount;
				discountApplied = true;
			}
			
			if(!discountApplied) {
				discountAmount = ( billAmount / 100 ) * 5;
			}
		}
		return discountAmount;
	}
	
	private void sendErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
        restTemplate.postForLocation("/api/errors", errorMessage);
    }
	
}
