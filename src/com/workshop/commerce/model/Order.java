package com.workshop.commerce.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="orders")
public class Order {
	
	@DatabaseField(id = true)
	private long orderId;
	
	@DatabaseField
	private int amount;
	
	@DatabaseField
	private Date orderDate;
	
	@DatabaseField
	private long merchantId;
	
	public Order() {
		//required by ORMLite
	}

	public Order(int amount, Date orderDate, long merchantId, long orderId) {
		super();
		this.amount = amount;
		this.orderDate = orderDate;
		this.merchantId = merchantId;
		this.orderId = orderId;
	}

	public int getAmount() {
		return amount;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public long getMerchantId() {
		return merchantId;
	}

	public long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	@Override
	public String toString() {
		return "Order [amount=" + amount + ", orderDate=" + orderDate
				+ ", merchantId=" + merchantId + ", orderId=" + orderId + "]";
	}
	
}
