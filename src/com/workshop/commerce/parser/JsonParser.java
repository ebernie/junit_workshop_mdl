package com.workshop.commerce.parser;

import com.workshop.commerce.payload.Payload;


public class JsonParser<T> implements Parser<T>{

	@Override
	public com.workshop.commerce.payload.Payload.Type getPayloadType() {
		return null;
	}

	@Override
	public T parse(Payload payload) {
		return null;
	}

	

}
