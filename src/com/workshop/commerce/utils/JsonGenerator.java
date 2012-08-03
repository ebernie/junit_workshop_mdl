package com.workshop.commerce.utils;

import java.util.Date;

import com.google.gson.Gson;
import com.workshop.commerce.model.Order;

public class JsonGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Order order = new Order();
		order.setAmount(200);
		order.setMerchantId(2001);
		order.setOrderDate(new Date());
		order.setOrderId(1001);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(order));
		
	}

}
