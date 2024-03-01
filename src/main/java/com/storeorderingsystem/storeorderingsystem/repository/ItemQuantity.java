package com.storeorderingsystem.storeorderingsystem.repository;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ITEM_QUANTITY")
public class ItemQuantity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_ID")
    private long itemId;

	@Column(name = "NAME")
    private String name;
	
	@Column(name = "PRICE")
    private int price;
	
	@Column(name = "QUANTITY")
    private int quantity;
	
	@Column(name = "TYPE")
    private String type;
	
	public ItemQuantity() {
	}

	public ItemQuantity(long itemId, String name, int price, int quantity, String type) {
		this.itemId = itemId;
	    this.name = name;
	    this.price = price;
	    this.quantity = quantity;
	    this.type = type;
	}
	    
	    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "ItemQuantity{" +
				"itemId='" + itemId + '\'' +
				", name='" + name + '\'' +
				", price='" + price + '\'' +
				", quantity='" + quantity + '\'' +
				", type='" + type + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (o == null || getClass() != o.getClass()) 
			return false;
		ItemQuantity that = (ItemQuantity) o;
		return Objects.equals(itemId, that.itemId) &&
				Objects.equals(name, that.name)&&
				Objects.equals(price, that.price)&&
				Objects.equals(quantity, that.quantity)&&
				Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, name, price, quantity, type);
	}
}
