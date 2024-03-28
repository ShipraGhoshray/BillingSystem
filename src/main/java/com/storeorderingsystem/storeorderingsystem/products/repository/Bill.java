package com.storeorderingsystem.storeorderingsystem.products.repository;

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
    private long billId;

	@Column(name = "STORE_USER_ID")
    private long storeUserId;
	
	@Column(name = "ITEM_QUANTITY_ID")
    private long itemsQuantityId;
	
	@Column(name = "CREATED_DATE")
    private long createdDate;
	
	@Column(name = "TOTAL_PRICE")
	private float totalPrice;
	
	@Column(name = "DISCOUNT_AMOUNT")
    private double discountAmount; 
    
	@Column(name = "BILL_AMOUNT")
    private double billAmount;
	
	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
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

	public long getStoreUserId() {
		return storeUserId;
	}

	public void setStoreUserId(long storeUserId) {
		this.storeUserId = storeUserId;
	}
	
	public long getItemsQuantityId() {
		return itemsQuantityId;
	}

	public void setItemsQuantityId(long itemsQuantityId) {
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
