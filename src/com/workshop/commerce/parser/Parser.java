package com.workshop.commerce.parser;

import com.workshop.commerce.payload.Payload;

public interface Parser<T>{
	
	public T parse(Payload payload);
	
	public Payload.Type getPayloadType();
	
}
