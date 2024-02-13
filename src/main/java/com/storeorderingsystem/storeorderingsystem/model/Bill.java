package com.storeorderingsystem.storeorderingsystem.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bill {
    
    private String billId;
    private String storeUserId;
    private List<ItemQuantity> items;
    private long createdDate;
    private float totalPrice;
    private int discountAmount; 
    private float billAmount;
    
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public float getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}

	public String getStoreUserId() {
		return storeUserId;
	}

	public void setStoreUserId(String storeUserId) {
		this.storeUserId = storeUserId;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ItemQuantity> getItems() {
		return items;
	}

	public void setItems(List<ItemQuantity> items) {
		this.items = items;
	}
}
