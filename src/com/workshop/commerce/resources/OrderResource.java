package com.workshop.commerce.resources;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.workshop.commerce.model.Order;
import com.workshop.commerce.processor.ProcessorFactory;

@Path("/order")
public class OrderResource {
	
	@POST
	@Produces("application/json")
	public String createOrder(@QueryParam("amount") int amount, @QueryParam("merchantId")long merchantId) {
		Order order = new Order();
		order.setAmount(amount);
		order.setMerchantId(merchantId);
		order.setOrderDate(new Date());
		
		ProcessorFactory.INSTANCE.getProcessor(order).doWork();

		return "{\"ok\"}";
	}

}
