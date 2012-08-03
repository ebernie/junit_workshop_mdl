package com.workshop.commerce.utils;

import java.io.File;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryWatcher extends TimerTask {

	private static final Logger LOG = LoggerFactory
			.getLogger(DirectoryWatcher.class);

	private File directoryToWatch;
	private DirectoryChangeCallback callback;

	public DirectoryWatcher(String directoryToWatch, DirectoryChangeCallback callback) {
		this(new File(directoryToWatch), callback);
	}

	public DirectoryWatcher(File directoryToWatch, DirectoryChangeCallback callback) {
		this.directoryToWatch = directoryToWatch;
		this.callback = callback;
	}

	@Override
	public void run() {
		LOG.info("Scanning " + directoryToWatch.getAbsolutePath());
		File[] files = directoryToWatch.listFiles();
		// Get list of files.
		if (files == null || files.length == 0) {
			return;
		}
		for (File file : files) {
			try {
				callback.directoryChanged(file);
			} catch (Exception e) {
				LOG.error("Failure reading file " + file.getName(), e);
			}
		}
	}
}
