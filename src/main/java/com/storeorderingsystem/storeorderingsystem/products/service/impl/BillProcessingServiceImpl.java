package com.storeorderingsystem.storeorderingsystem.products.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.User;
import com.storeorderingsystem.storeorderingsystem.products.dto.BillAmountDto;
import com.storeorderingsystem.storeorderingsystem.products.model.Bill;
import com.storeorderingsystem.storeorderingsystem.products.model.Products;
import com.storeorderingsystem.storeorderingsystem.products.repository.BillRepository;
import com.storeorderingsystem.storeorderingsystem.products.service.BillProcessingService;
import com.storeorderingsystem.storeorderingsystem.repository.UserRepository;
import com.storeorderingsystem.storeorderingsystem.util.Constants;
import com.storeorderingsystem.storeorderingsystem.util.DateUtils;

@Service
public class BillProcessingServiceImpl implements BillProcessingService{

	private BillRepository billRepository;
	private UserRepository  storeUserRepository;
	//private ItemQuantityRepository  itemQuantityRepository;
	
	public BillProcessingServiceImpl(
			BillRepository billRepository/* , ItemQuantityRepository itemQuantityRepository */,
			UserRepository storeUserRepository) {
		this.billRepository = billRepository;
		//this.itemQuantityRepository = itemQuantityRepository;
		this.storeUserRepository = storeUserRepository;
	}

	@Override
	public BillAmountDto processBill(com.storeorderingsystem.storeorderingsystem.products.dto.BillDto billInfo) {
		
        System.out.println("Process Bill");
        double price = 0.0;
        double billAmount = 0.0;
        double discountAmt = 0.0;
        String userType = Constants.USER_ROLE_USER;
        //Date joiningDate = new Date();
        List<Products> itemRepositoryList = new ArrayList<Products>();
        User user = null;
        
        if(billInfo!= null && (billInfo.getStoreUserId() != null)) {
        	if(billInfo.getStoreUserId() != null) {	
        		Optional<User> optionalStoreUser = storeUserRepository.findById(Long.valueOf(billInfo.getStoreUserId()));
        		if(optionalStoreUser != null && optionalStoreUser.isPresent()) {
        			user = optionalStoreUser.get();
        		}
        		if(user!= null) {
        			/*if(user.getRole() != null) {
            			userType = user.getRole();        				
        			}
        			if(user.getJoiningDate() != null) {
            			joiningDate = DateUtils.createDateFromDateString(user.getJoiningDate());        				
        			}*/
        		}
        		
        		if(billInfo.getItems()!= null && billInfo.getItems().size() > 0) {
            		for(com.storeorderingsystem.storeorderingsystem.products.dto.ProductsDto item:billInfo.getItems()) {

            			itemRepositoryList.add(populateItemQuantity(item));
            			price = item.getPrice() * item.getQuantity();
            			discountAmt = getDiscountAmountOnBill(userType, user.getJoiningDate(), item.getType(), price);
                   		billAmount = billAmount + (price - discountAmt);
            		}
            		
                	if(Double.compare(billAmount, 0) > 0) {
                    	BillAmountDto billAmountObj = getBillAmount(billInfo, billAmount); 
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
	
	private BillAmountDto getBillAmount(com.storeorderingsystem.storeorderingsystem.products.dto.BillDto billInfo, double billAmount) {
		BillAmountDto billAmountObj = new BillAmountDto();
		billAmountObj.setBillId(billInfo.getBillId());
		billAmountObj.setBillAmount(billAmount);
		return billAmountObj;
	}
	
	private Products populateItemQuantity(com.storeorderingsystem.storeorderingsystem.products.dto.ProductsDto item) {
		
		Products itemRepoObj = new Products();
		itemRepoObj.setName(item.getName());
		itemRepoObj.setPrice(item.getPrice());
		itemRepoObj.setQuantity(item.getQuantity());
		itemRepoObj.setType(item.getType());
		return itemRepoObj;
	}
	
	private Bill saveBillAndItemRepository(com.storeorderingsystem.storeorderingsystem.products.dto.BillDto billInfo, double billAmount, 
			double discountAmount, List<com.storeorderingsystem.storeorderingsystem.products.model.Products> itemRepositoryList) {
		Bill billRepoObj = null;
    	if(itemRepositoryList.size() > 0) {
    		for(Products item: itemRepositoryList) {       	
    			billRepoObj = new Bill();
    			//billRepoObj.setBillId(billInfo.getBillId());
    			billRepoObj.setStoreUserId(Long.valueOf(billInfo.getStoreUserId()));
    			billRepoObj.setCreatedDate(billInfo.getCreatedDate());
    			billRepoObj.setTotalPrice(billInfo.getTotalPrice());
    			billRepoObj.setDiscountAmount(discountAmount);
    			billRepoObj.setBillAmount(billAmount);
    			billRepoObj.setItemsQuantityId(Long.valueOf(item.getItemId()));
    			billRepository.save(billRepoObj);
    		}		
		}    	
		return billRepoObj;
	}

	private double getDiscountAmountOnBill(String userType, Date joiningDate, String itemType, double billAmount) {
		
		double discountAmount = 0.0;
		boolean discountApplied = false;
		if(userType != null) {
			
			if(!itemType.equalsIgnoreCase(Constants.PRODUCT_TYPE_GROCERIES)) {
				if(userType.equalsIgnoreCase(Constants.USER_ROLE_ADMIN) ) {
					discountAmount = calculateDiscountAmount(Constants.USER_TYPE_EMPLOYEE_DISCOUNT_PERCENT, billAmount);
					discountApplied = true;
					
				}else if(userType.equalsIgnoreCase(Constants.USER_ROLE_AFFILIATE)){
					discountAmount = calculateDiscountAmount(Constants.USER_TYPE_AFFILIATE_DISCOUNT_PERCENT, billAmount);
					discountApplied = true;
				
				}else if(joiningDate != null && DateUtils.isDateGreatThanTwoYears(joiningDate)){
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
    }	
	
    @Override
    public Iterable<com.storeorderingsystem.storeorderingsystem.products.model.Bill> lookup(){
        return billRepository.findAll();
    }

    @Override
    public long total() {
        return billRepository.count();
    }
	
}
