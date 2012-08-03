package com.workshop.commerce.processor;

import com.workshop.commerce.ex.InvalidDataTypeException;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.parser.Parser;
import com.workshop.commerce.parser.ParserFactory;
import com.workshop.commerce.payload.Payload;
import com.workshop.commerce.utils.Database;

public class ProcessorFactory {
	
	private static ProcessorFactory me;
	
	private ProcessorFactory() {
		
	}
	
	public static synchronized ProcessorFactory getInstance() {
		
		if (me == null) {
			me = new ProcessorFactory();
		}
		
		return me;
	}
	
	public Processor getProcessor(Payload payload) {
		if (Payload.Type.JSON.equals(payload.getType())){
			String json = (String) payload.getPayload();
			if (json != null && json.contains("orderId")) {
				Parser<Order> parser = ParserFactory.getParser(Payload.Type.JSON, Order.class);
				OrderProcessor processor = new OrderProcessor(parser,payload);
				processor.setDatabase(Database.getInstance());
				return processor;
			} else {
				throw new InvalidDataTypeException("Unrecognized payload");
			}
		} else if (Payload.Type.XML.equals(payload.getType())) {
			throw new InvalidDataTypeException("Unimplemented");
		} else {
			throw new InvalidDataTypeException("Unsupported payload " + payload.getType().name());
		}
	}
	
}
