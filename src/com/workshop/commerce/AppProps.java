package com.workshop.commerce;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppProps {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppProps.class);
	private static final String DB_PROPS = "database.properties";
	private static final String APP_PROPS = "application.properties";
	
	private static AppProps me = new AppProps();
	private Properties propfile = null;
	
	private AppProps() {
		propfile = new Properties();
		try {
			propfile.load(this.getClass().getClassLoader().getResourceAsStream(DB_PROPS));
			propfile.load(this.getClass().getClassLoader().getResourceAsStream(APP_PROPS));
		} catch (Exception e) {
			LOG.error("Can't read properties file", e);
		}
	}
	
	public void load(File ...files) {
		for (int i = 0; i < files.length; i++) {
			try {
				propfile.load(new FileInputStream(files[i]));
			} catch (Exception e) {
				LOG.error("Can't read properties file: " + files[i], e);
			}
		}
	}
	
	public static final AppProps getInstance() {
		return me;
	}
	
	public final String getProp(String key) {
			return propfile.getProperty(key);
	}

	@Override
	public String toString() {
		return "AppProps [propfile=" + propfile + "]";
	}
	
}
