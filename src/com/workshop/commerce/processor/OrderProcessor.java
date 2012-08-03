package com.workshop.commerce.processor;

import java.sql.SQLException;
import java.util.Calendar;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.workshop.commerce.ex.InvalidOrderException;
import com.workshop.commerce.model.Merchant;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.parser.Parser;
import com.workshop.commerce.payload.Payload;
import com.workshop.commerce.utils.Database;

public class OrderProcessor {
	
	private final Order order;
	
	private Database database;
	
	public OrderProcessor(Parser<Order> parser, Payload payload){
		this.order = parser.parse(payload);
	}
	
	public void setDatabase(Database database) {
		this.database = database;
	}

	public void validateOrder() {
		// do simple validation
		if (this.order.getOrderDate().after(Calendar.getInstance().getTime())) {
			throw new InvalidOrderException("Orders date is invalid: " + this.order);
		}
		
		if (!Merchant.isValidMerchant(order.getMerchantId())) {
			throw new InvalidOrderException("Invalid merchant ID: " + this.order);
		}
	}
	
	public void createOrder() throws SQLException {
		// persist order somewhere
		Dao<Order, String> orderDao = DaoManager.createDao(this.database.getConnection(), Order.class);
		orderDao.update(this.order);
	}
	
	public void updateOrder(Order order) throws SQLException {
		Dao<Order, String> orderDao = DaoManager.createDao(this.database.getConnection(), Order.class);
		orderDao.update(order);
	}
	
	public Order findOrderById(String orderId) throws SQLException {
		Dao<Order, String> orderDao = DaoManager.createDao(this.database.getConnection(), Order.class);
		return orderDao.queryForId(orderId);
	}

}
