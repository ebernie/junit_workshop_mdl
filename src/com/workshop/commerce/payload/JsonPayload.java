package com.workshop.commerce.payload;

public class JsonPayload implements Payload {
	
	private final String json;
	
	public JsonPayload(String json) {
		super();
		//TODO: should probably try to determine if this is JSON and not other type of data
		this.json = json;
	}
	
	@Override
	public Type getType() {
		return Payload.Type.JSON;
	}

	@Override
	public Object getPayload() {
		return json;
	}

}
