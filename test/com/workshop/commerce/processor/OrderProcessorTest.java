package com.workshop.commerce.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.workshop.commerce.model.Order;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DaoManager.class })
public class OrderProcessorTest {

	@SuppressWarnings("rawtypes")
	@Mock
	private Dao orderDao;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		//setup PowerMockito to mock DaoManager
		//jumping through hoops because of static method
		PowerMockito.mockStatic(DaoManager.class);
		// define behavior of DaoManagor
		Mockito.when(
				DaoManager.createDao(any(ConnectionSource.class),
						eq(Order.class))).thenReturn(orderDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateOrder() throws Exception {
		//define behavior for our orderDao mock
		when(orderDao.createOrUpdate(any(Order.class))).thenReturn(
				new CreateOrUpdateStatus(true, false, 1));
		Order order = new Order(100, new Date(), 1001l, 2010l);
		OrderProcessor orderProcessor = (OrderProcessor) ProcessorFactory.INSTANCE
				.getProcessor(order);
		orderProcessor.setOrderDao(orderDao);
		assertNull(order.getProcessedDate());
		// do work, verify result
		WorkResult result = orderProcessor.doWork();
		assertTrue(result.isSuccessful());
		assertEquals(WorkResult.ErrCode.NO_ERROR, result.getErrCode());
		assertNull(result.getErrMsg());
		assertNotNull(order.getProcessedDate());
		assertTrue(order.getProcessedDate().after(order.getOrderDate()));

		// to prove nothing was saved, we query for the object
		assertTrue(orderDao.queryForMatching(order).isEmpty());
	}

}
