package com.storeorderingsystem.storeorderingsystem.products.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCTS")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID")
    private long productId;

	@Column(name = "NAME")
    private String name;
	
	@Column(name = "PRICE")
    private int price;
	
	@Column(name = "TYPE")
    private String type;
	
	@ManyToOne
    @JoinColumn(name = "BILL_ID", insertable = false, updatable = false)
    private Bill bill;
	
	public Products() {
	}

	public Products(String name, int price, String type) {
	    this.name = name;
	    this.price = price;
	    this.type = type;
	}
	
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	@Override
	public String toString() {
		return "Products{" +
				"productId='" + productId + '\'' +
				", name='" + name + '\'' +
				", price='" + price + '\'' +
				", type='" + type + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (o == null || getClass() != o.getClass()) 
			return false;
		Products that = (Products) o;
		return Objects.equals(productId, that.productId) &&
				Objects.equals(name, that.name)&&
				Objects.equals(price, that.price)&&
				Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, name, price, type);
	}
}
