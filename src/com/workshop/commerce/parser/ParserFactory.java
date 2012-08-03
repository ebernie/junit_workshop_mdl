package com.workshop.commerce.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.workshop.commerce.ex.ParserNotFoundException;
import com.workshop.commerce.payload.Payload;

public class ParserFactory {

	private static Map<Payload.Type, Parser> PARSERS;

	public static final <T>Parser<T> getParser(Payload.Type pt, Class<T> classOfT) {
		Parser<T>  parsr = PARSERS.get(pt);
		if (parsr == null) {
			throw new ParserNotFoundException("No parser found for " + pt.name());
		}
		return parsr;
	}

	public static final void addParser(Parser parser) {
		if (PARSERS == null ) {
			PARSERS = new ConcurrentHashMap<Payload.Type, Parser>();
		}
		PARSERS.put(parser.getPayloadType(), parser);
	}

}
