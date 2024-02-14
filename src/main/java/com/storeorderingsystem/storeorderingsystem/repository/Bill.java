package com.storeorderingsystem.storeorderingsystem.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="BILL")
public class Bill {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BILL_ID")
    private String billId;

	@Column(name = "STORE_USER_ID")
    private String storeUserId;
	
	@Column(name = "ITEM_QUANTITY_ID")
    private String itemsQuantityId;
	
	@Column(name = "CREATED_DATE")
    private long createdDate;
	
	@Column(name = "TOTAL_PRICE")
	private float totalPrice;
	
	@Column(name = "DISCOUNT_AMOUNT")
    private double discountAmount; 
    
	@Column(name = "BILL_AMOUNT")
    private double billAmount;
	
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

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public String getStoreUserId() {
		return storeUserId;
	}

	public void setStoreUserId(String storeUserId) {
		this.storeUserId = storeUserId;
	}
	
	public String getItemsQuantityId() {
		return itemsQuantityId;
	}

	public void setItemsQuantityId(String itemsQuantityId) {
		this.itemsQuantityId = itemsQuantityId;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

}
