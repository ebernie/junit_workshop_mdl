package com.workshop.commerce.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.workshop.commerce.model.Order;
import com.workshop.commerce.parser.Parser;
import com.workshop.commerce.parser.ParserFactory;
import com.workshop.commerce.payload.JsonPayload;
import com.workshop.commerce.payload.Payload;
import com.workshop.commerce.processor.OrderProcessor;

public class JsonDirectoryCallback implements DirectoryChangeCallback {
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonDirectoryCallback.class);
	
	@Override
	public void directoryChanged(File file) {
		String json;
		try {
			json = readFromFile(file);
			Payload jsonPayload = new JsonPayload(json);
			Parser<Order> orderParser = ParserFactory.getParser(
					jsonPayload.getType(), Order.class);
			OrderProcessor processor = new OrderProcessor(orderParser, jsonPayload);
			processor.createOrder();
		} catch (Exception e) {
			LOG.error("Failure reading file " + file.getName(), e);
		}
	}

	private String readFromFile(File file) throws FileNotFoundException,
			IOException {
		BufferedReader br;
		String currentLine = null;
		br = new BufferedReader(new FileReader(file));
		StringBuffer json = new StringBuffer();
		while ((currentLine = br.readLine()) != null) {
			json.append(currentLine);
		}
		return json.toString();
	}

}
