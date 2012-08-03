package com.workshop.commerce;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.workshop.commerce.utils.DirectoryWatcher;

public class App {

	private static final String DB_PROP_FILE = "database.properties";
	private static final String APP_PROP_FILE = "application.properties";

	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String args[]) {
		// read properties
		Properties propfile = new Properties();
		try {
			propfile.load(new FileInputStream(DB_PROP_FILE));
			propfile.load(new FileInputStream(APP_PROP_FILE));
			String databaseUrl = (String) propfile
					.get(Constants.PROP_DB_CONNECTION);

			try {
				ConnectionSource connectionSource = new JdbcConnectionSource(
						databaseUrl);
				DirectoryWatcher directoryWatcher = new DirectoryWatcher(new File((String)propfile
					.get(Constants.PROP_APP_JSON_DIR)), pattern, callback);
			} catch (SQLException e) {
				LOG.error("Error obtaining connection to " + databaseUrl, e);
			}
		} catch (Exception e) {
			LOG.error("Can't read database properties file: " + DB_PROP_FILE, e);
		}
	}

}
