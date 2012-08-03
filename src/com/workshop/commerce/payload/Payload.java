package com.workshop.commerce.payload;

public interface Payload {
	
	enum Type {
		XML, JSON, CSV;
	}
	
	Payload.Type getType();
	
	Object getPayload();
		
}
