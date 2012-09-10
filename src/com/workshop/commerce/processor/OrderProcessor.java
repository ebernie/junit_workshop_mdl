package com.workshop.commerce.processor;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.workshop.commerce.ex.InvalidOrderException;
import com.workshop.commerce.model.Merchant;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.parser.Parser;
import com.workshop.commerce.payload.Payload;

public class OrderProcessor implements Processor {

	private final static Logger LOG = LoggerFactory
			.getLogger(OrderProcessor.class);

	private final Order order;

	private Dao<Order, String> orderDao;

	public OrderProcessor(Parser<Order> parser, Payload payload) {
		this.order = parser.parse(payload);
	}

	public OrderProcessor(Order order) {
		this.order = order;
	}

	public void setOrderDao(Dao<Order, String> orderDao) {
		this.orderDao = orderDao;
	}

	public void validateOrder() {
		// do simple validation
		if (this.order.getOrderDate().after(Calendar.getInstance().getTime())) {
			throw new InvalidOrderException("Orders date is invalid: "
					+ this.order);
		}

		if (!Merchant.isValidMerchant(order.getMerchantId())) {
			throw new InvalidOrderException("Invalid merchant ID: "
					+ this.order);
		}
	}

	public Order findOrderById(String orderId) throws SQLException {
		return this.orderDao.queryForId(orderId);
	}

	@Override
	public WorkResult doWork() {
		try {
			validateOrder();
			this.order.setProcessedDate(new Date());
			CreateOrUpdateStatus status = this.orderDao
					.createOrUpdate(this.order);
			LOG.debug("Created order " + order);
			if (status.isCreated()) {
				return new WorkResult(true, null, WorkResult.ErrCode.NO_ERROR);
			} else {
				return new WorkResult(false,
						"Failed to create order: " + order,
						WorkResult.ErrCode.UNKNOWN_ERROR);
			}
		} catch (SQLException e) {
			LOG.error("Can't create order ", e);
			return new WorkResult(false, "Failed to create order: " + order,
					WorkResult.ErrCode.UNKNOWN_ERROR);
		}
	}

}
