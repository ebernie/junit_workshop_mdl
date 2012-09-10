package com.workshop.commerce.processor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.sql.SQLException;
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
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.workshop.commerce.model.Order;
import com.workshop.commerce.utils.Database;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DaoManager.class, Database.class })
public class ProcessorFactoryTest {
	
	@SuppressWarnings("rawtypes")
	@Mock
	private Dao orderDao;
	@Mock
	private Database database;


	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		//jumping through hoops because of static method
		PowerMockito.mockStatic(DaoManager.class);
		PowerMockito.mockStatic(Database.class);
		// define behavior of mock DaoManager & Database
		Mockito.when(Database.getInstance()).thenReturn(database);
		Mockito.when(
				DaoManager.createDao(any(ConnectionSource.class),
						eq(Order.class))).thenReturn(orderDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetJsonProcessor() throws SQLException {
		Order order = new Order(100, new Date(), 1001l, 2010l);
		Processor orderProcessor = ProcessorFactory.INSTANCE
				.getProcessor(order);
		assertTrue(orderProcessor instanceof OrderProcessor);
	}

}
