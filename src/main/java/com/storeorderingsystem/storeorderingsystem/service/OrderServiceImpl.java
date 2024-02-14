package com.storeorderingsystem.storeorderingsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.storeorderingsystem.storeorderingsystem.model.BillAmount;
import com.storeorderingsystem.storeorderingsystem.repository.Bill;
import com.storeorderingsystem.storeorderingsystem.repository.BillRepository;
import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantityRepository;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUser;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUserRepository;
import com.storeorderingsystem.storeorderingsystem.util.Constants;
import com.storeorderingsystem.storeorderingsystem.util.DateUtils;

public class OrderServiceImpl implements OrderService{

	private RestTemplate restTemplate;
	private BillRepository billRepository;
	private StoreUserRepository  storeUserRepository;
	
	@Autowired
	private ItemQuantityRepository  itemQuantityRepository;
	
	@Autowired
	public OrderServiceImpl() {
		
	}
	@Autowired
	public OrderServiceImpl(RestTemplate restTemplate, BillRepository billRepository, ItemQuantityRepository itemQuantityRepository) {
		this.restTemplate = restTemplate;
		this.billRepository = billRepository;
		this.itemQuantityRepository = itemQuantityRepository;
	}

	@Override
	public BillAmount processBill(com.storeorderingsystem.storeorderingsystem.model.Bill billInfo) {
		
        System.out.println("Process Bill");
        double price = 0.0;
        double billAmount = 0.0;
        double discountAmt = 0.0;
        String userType = Constants.EMPTY_STRING;
        List<ItemQuantity> itemRepositoryList = new ArrayList<ItemQuantity>();
        
        if(billInfo!= null && (billInfo.getStoreUserId() != null)) {
        	if(billInfo.getStoreUserId() != null) {	
        		StoreUser user = storeUserRepository.findById(Long.valueOf(billInfo.getStoreUserId())).get();
        		if(user.getUserType() != null) {
        			userType = user.getUserType();
        		}
        		
        		if(billInfo.getItems()!= null && billInfo.getItems().size() > 0) {
            		for(com.storeorderingsystem.storeorderingsystem.model.ItemQuantity item:billInfo.getItems()) {

            			itemRepositoryList.add(populateItemQuantity(item));
            			price = item.getPrice() * item.getQuantity();
            			discountAmt = getDiscountAmountOnBill(userType, user.getJoiningDate(), item.getType(), price);
                   		billAmount = billAmount + (price - discountAmt);
            		}
            		
                	if(Double.compare(billAmount, 0) > 0) {
                    	BillAmount billAmountObj = getBillAmount(billInfo, billAmount); 
                    	saveBillAndItemRepository(billInfo, billAmount, billAmount, itemRepositoryList);
                    	return billAmountObj;
                	}
            	}
        	}        	
        }else {
            String errorMessage = "Failed to process bill";
            sendErrorMessage(errorMessage);
        }
        return null;
   }
	
	private BillAmount getBillAmount(com.storeorderingsystem.storeorderingsystem.model.Bill billInfo, double billAmount) {
		BillAmount billAmountObj = new BillAmount();
		billAmountObj.setBillId(billInfo.getBillId());
		billAmountObj.setBillAmount(billAmount);
		return billAmountObj;
	}
	
	private ItemQuantity populateItemQuantity(com.storeorderingsystem.storeorderingsystem.model.ItemQuantity item) {
		
		ItemQuantity itemRepoObj = new ItemQuantity();
		itemRepoObj.setName(item.getName());
		itemRepoObj.setPrice(item.getPrice());
		itemRepoObj.setQuantity(item.getQuantity());
		itemRepoObj.setType(item.getType());
		return itemRepoObj;
	}
	
	private Bill saveBillAndItemRepository(com.storeorderingsystem.storeorderingsystem.model.Bill billInfo, double billAmount, 
			double discountAmount, List<com.storeorderingsystem.storeorderingsystem.repository.ItemQuantity> itemRepositoryList) {
		Bill billRepoObj = null;
    	if(itemRepositoryList.size() > 0) {
    		for(ItemQuantity item: itemRepositoryList) {
    			
    			item = itemQuantityRepository.save(item);        	
    			billRepoObj = new Bill();
    			billRepoObj.setBillId(billInfo.getBillId());
    			billRepoObj.setStoreUserId(billInfo.getStoreUserId());
    			billRepoObj.setCreatedDate(billInfo.getCreatedDate());
    			billRepoObj.setTotalPrice(billInfo.getTotalPrice());
    			billRepoObj.setDiscountAmount(discountAmount);
    			billRepoObj.setBillAmount(billAmount);
    			billRepoObj.setItemsQuantityId(item.getItemId());
    			billRepository.save(billRepoObj);
    		}		
		}    	
		return billRepoObj;
	}

	private double getDiscountAmountOnBill(String userType, String joiningDate, String itemType, double billAmount) {
		
		double discountAmount = 0.0;
		boolean discountApplied = false;
		if(userType != null) {
			
			if(!itemType.equalsIgnoreCase(Constants.ITEM_TYPE_GROCERIES)) {
				if(userType.equalsIgnoreCase(Constants.USER_TYPE_EMPLOYEE) ) {
					discountAmount = calculateDiscountAmount(Constants.USER_TYPE_EMPLOYEE_DISCOUNT_PERCENT, billAmount);
					discountApplied = true;
					
				}else if(userType.equalsIgnoreCase(Constants.USER_TYPE_AFFILIATE)){
					discountAmount = calculateDiscountAmount(Constants.USER_TYPE_AFFILIATE_DISCOUNT_PERCENT, billAmount);
					discountApplied = true;
				
				}else if(joiningDate != null && DateUtils.isDateGreatThanTwoYears(DateUtils.createDateFromDateString(joiningDate))){
					discountAmount = calculateDiscountAmount(Constants.USER_TYPE_2YEAR_DISCOUNT_PERCENT, billAmount);
					discountApplied = true;
				}
			}
			if(!discountApplied) {
				discountAmount = ( billAmount / 100 ) * 5;
			}
		}
		return discountAmount;
	}
	
	private double calculateDiscountAmount(int discountPercentage, double billAmount) {
		
		return discountPercentage / 100 * billAmount;
	}
	
	private void sendErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
        restTemplate.postForLocation("/api/errors", errorMessage);
    }
	
}
