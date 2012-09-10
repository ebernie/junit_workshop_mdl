package com.workshop.commerce.processor;

import java.sql.SQLException;

import org.slf4j.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.workshop.commerce.ex.InvalidDataTypeException;
import com.workshop.commerce.ex.ProcessorException;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.parser.Parser;
import com.workshop.commerce.parser.ParserFactory;
import com.workshop.commerce.payload.Payload;
import com.workshop.commerce.utils.Database;

public enum ProcessorFactory {

	INSTANCE;

	private static final Logger LOG = org.slf4j.LoggerFactory
			.getLogger(ProcessorFactory.class);

	public Processor getProcessor(Payload payload) {
		if (Payload.Type.JSON.equals(payload.getType())) {
			String json = (String) payload.getPayload();
			if (json != null && json.contains("orderId")) {
				Parser<Order> parser = ParserFactory.INSTANCE.getParser(
						Payload.Type.JSON, Order.class);
				OrderProcessor processor = new OrderProcessor(parser, payload);
				Dao<Order, String> dao;
				try {
					dao = DaoManager.createDao(Database.getInstance()
							.getConnection(), Order.class);
					processor.setOrderDao(dao);
					return processor;
				} catch (SQLException e) {
					LOG.error("Couldn't create database: ", e);
					throw new ProcessorException(e);
				}
			} else {
				throw new InvalidDataTypeException("Unrecognized payload");
			}
		} else if (Payload.Type.XML.equals(payload.getType())) {
			throw new ProcessorException("Unimplemented");
		} else {
			throw new ProcessorException("Unsupported payload "
					+ payload.getType().name());
		}
	}

	public Processor getProcessor(Object obj) {
		if (obj instanceof Order) {
			OrderProcessor processor = new OrderProcessor((Order) obj);
			Dao<Order, String> dao;
			try {
				dao = DaoManager.createDao(Database.getInstance()
						.getConnection(), Order.class);
				processor.setOrderDao(dao);
				return processor;
			} catch (SQLException e) {
				LOG.error("Couldn't create database: ", e);
				throw new ProcessorException(e);
			}
		} else {
			throw new ProcessorException("Unrecognized  class type");
		}
	}

}
