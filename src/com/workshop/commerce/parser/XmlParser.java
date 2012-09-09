package com.workshop.commerce.parser;

import com.workshop.commerce.payload.Payload;
import com.workshop.commerce.payload.Payload.Type;

public class XmlParser<T> implements Parser<T> {

	@Override
	public Type getPayloadType() {
		throw new UnsupportedOperationException("Unimplemented");
	}

	@Override
	public T parse(Payload payload) {
		throw new UnsupportedOperationException("Unimplemented");
	}

}
