package com.workshop.commerce.utils;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.workshop.commerce.AppProps;
import com.workshop.commerce.Constants;
import com.workshop.commerce.model.Order;

public class Database {
	
	private final ConnectionSource connectionSource;
	private static final Logger LOG = LoggerFactory.getLogger(Database.class);
	private static Database me;
	
	
	public static synchronized final Database getInstance() {
		if (me == null) {
			me = new Database();
		}
		return me;
	}
	
	private Database() {
		super();
		String databaseUrl = AppProps.getInstance().getProp(Constants.PROP_DB_CONNECTION);
		try {
			connectionSource = new JdbcConnectionSource(
					databaseUrl);
		} catch (SQLException e) {
			LOG.error("Critical failure. Failed to create connection to " + databaseUrl);
			throw new RuntimeException("Critical failure. Failed to create connection to " + databaseUrl, e);
		}
		try {
			TableUtils.createTable(connectionSource, Order.class);
		} catch (SQLException e) {
			throw new RuntimeException("Can't create table");
		}
	}

	public ConnectionSource getConnection() {
		return connectionSource;
	}

}
