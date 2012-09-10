package com.workshop.commerce.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.workshop.commerce.ex.InvalidDataTypeException;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.payload.JsonPayload;
import com.workshop.commerce.payload.Payload;
import com.workshop.commerce.payload.XmlPayload;

public class JsonParserTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(JsonParserTest.class);

	@Before
	public void setUp() throws Exception {
		// factory setup
		ParserFactory.INSTANCE.addParser(new JsonParser<Order>());
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
		Parser<Order> orderParser = ParserFactory.INSTANCE.getParser(
				Payload.Type.JSON, Order.class);
		// JsonParser<Order> orderParser = new JsonParser<Order>();
		Order order = orderParser.parse(payload);
		assertTrue(sampleOrder.getOrderId() == order.getOrderId());
		assertTrue(sampleOrder.getMerchantId() == order.getMerchantId());
		assertTrue(sampleOrder.getAmount() == order.getAmount());

		Calendar sampleOrderCal = Calendar.getInstance();
		sampleOrderCal.setTimeInMillis(sampleOrder.getOrderDate().getTime());
		Calendar orderCal = Calendar.getInstance();
		orderCal.setTimeInMillis(order.getOrderDate().getTime());

		assertEquals(sampleOrderCal.get(Calendar.YEAR),
				orderCal.get(Calendar.YEAR));
		assertEquals(sampleOrderCal.get(Calendar.MONTH),
				orderCal.get(Calendar.MONTH));
		assertEquals(sampleOrderCal.get(Calendar.DATE),
				orderCal.get(Calendar.DATE));
		assertEquals(sampleOrderCal.get(Calendar.HOUR),
				orderCal.get(Calendar.HOUR));
		assertEquals(sampleOrderCal.get(Calendar.MINUTE),
				orderCal.get(Calendar.MINUTE));
		assertEquals(sampleOrderCal.get(Calendar.SECOND),
				orderCal.get(Calendar.SECOND));

		assertEquals(sampleOrder, order);
	}
	
	@Test(expected=InvalidDataTypeException.class)
	public void testParse_shouldNotParseInvalidDataType() {
		Date date = new Date();
		Order sampleOrder = new Order(100, date, 1001l, 2001l);
		Gson gson = new Gson();
		String json = gson.toJson(sampleOrder);
		LOG.debug("sampleOrder: " + sampleOrder);
		Payload payload = new XmlPayload(json);
		Parser<Order> orderParser = ParserFactory.INSTANCE.getParser(
				Payload.Type.JSON, Order.class);
		//expect an exception here
		orderParser.parse(payload);
	}

}
