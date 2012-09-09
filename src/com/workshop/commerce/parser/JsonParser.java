package com.workshop.commerce.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.workshop.commerce.ex.InvalidDataTypeException;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.payload.Payload;


public class JsonParser<T> implements Parser<T>{
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonParser.class);

	@Override
	public com.workshop.commerce.payload.Payload.Type getPayloadType() {
		return Payload.Type.JSON;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T parse(Payload payload) {
		LOG.debug("Parsing " + payload.getPayload());
		if (Payload.Type.JSON != payload.getType()) {
			throw new InvalidDataTypeException("Unable to process type " + payload.getType().name());
		}
		String jsonPayload = (String) payload.getPayload();
		Gson gson = new Gson();
		Order order = null;
		if (jsonPayload.contains("orderId")) {
			order = gson.fromJson(jsonPayload, Order.class);
		}
		LOG.debug("Parsed result: " + order);
		return (T) order;
	}

	

}
