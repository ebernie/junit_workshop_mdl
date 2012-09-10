package com.workshop.commerce.parser;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.payload.JsonPayload;
import com.workshop.commerce.payload.Payload;

public class JsonParserTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonParserTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse() {
		Date date = new Date();
		Order sampleOrder = new Order(100, date, 1001l, 2001l);
		Gson gson = new Gson();
		String json = gson.toJson(sampleOrder);
		LOG.debug("sampleOrder: " + sampleOrder);
		Payload payload = new JsonPayload(json);
		JsonParser<Order> orderParser = new JsonParser<Order>();
		Order order = orderParser.parse(payload);
		assertTrue(sampleOrder.getOrderId() == order.getOrderId());
		assertTrue(sampleOrder.getMerchantId() == order.getMerchantId());
		assertTrue(sampleOrder.getAmount() == order.getAmount());
		assertEquals(sampleOrder.getOrderDate(), order.getOrderDate());
		assertEquals(sampleOrder, order);
	}

}
