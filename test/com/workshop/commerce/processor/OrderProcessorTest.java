package com.workshop.commerce.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.workshop.commerce.model.Order;

@RunWith(MockitoJUnitRunner.class)
public class OrderProcessorTest {

	@SuppressWarnings("rawtypes")
	@Mock
	private Dao orderDao;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateOrderViaFactory() throws Exception {
		//define behavior for our orderDao mock
		when(orderDao.createOrUpdate(any(Order.class))).thenReturn(
				new CreateOrUpdateStatus(true, false, 1));
		Order order = new Order(100, new Date(), 1001l, 2010l);
		OrderProcessor orderProcessor = new OrderProcessor(order, orderDao);
		assertNull(order.getProcessedDate());
		// do work, verify result
		WorkResult result = orderProcessor.doWork();
		assertTrue(result.isSuccessful());
		assertEquals(WorkResult.ErrCode.NO_ERROR, result.getErrCode());
		assertNull(result.getErrMsg());
		assertNotNull(order.getProcessedDate());
		assertTrue(order.getProcessedDate().after(order.getOrderDate()));
	}
	
	public void testCreateOrder() {
		
	}

}
