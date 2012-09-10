package com.workshop.commerce;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.workshop.commerce.model.Order;
import com.workshop.commerce.parser.JsonParser;
import com.workshop.commerce.parser.ParserFactory;
import com.workshop.commerce.utils.DirectoryWatcher;
import com.workshop.commerce.utils.IncomingOrderDirectoryCallback;

public class App {

	private static final long REPEAT = 15000;

	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String args[]) throws IllegalArgumentException, IOException {
		LOG.info("Workshop demo app started");
		// setup
		ParserFactory.INSTANCE.addParser(new JsonParser<Order>());
		org.hsqldb.util.DatabaseManagerSwing.main(new String[] { "--url",
				"jdbc:hsqldb:mem:demo", "--noexit" });

		// setup directory watcher
		DirectoryWatcher directoryWatcher = new DirectoryWatcher(new File(
				AppProps.getInstance().getProp(Constants.PROP_APP_JSON_DIR)),
				new IncomingOrderDirectoryCallback());
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(directoryWatcher, Calendar.getInstance()
				.getTime(), REPEAT);
		System.in.read();
		System.exit(0);
	}

}
