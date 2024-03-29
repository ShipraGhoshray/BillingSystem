package com.storeorderingsystem.storeorderingsystem.products.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {
    
    private String billId;
    private String storeUserId;
    private List<ProductsDto> items;
    private long createdDate;
    private float totalPrice;
    
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

	public String getStoreUserId() {
		return storeUserId;
	}

	public void setStoreUserId(String storeUserId) {
		this.storeUserId = storeUserId;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ProductsDto> getItems() {
		return items;
	}

	public void setItems(List<ProductsDto> items) {
		this.items = items;
	}
}
