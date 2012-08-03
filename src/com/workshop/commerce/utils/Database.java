package com.workshop.commerce.utils;

import com.j256.ormlite.support.ConnectionSource;

public class Database {
	
	private final ConnectionSource connection;
	
	public Database(ConnectionSource connection) {
		super();
		this.connection = connection;
	}

	public ConnectionSource getConnection() {
		return this.connection;
	}

}
