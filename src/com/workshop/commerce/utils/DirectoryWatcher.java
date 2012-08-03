package com.workshop.commerce.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryWatcher extends TimerTask {

	private static final Logger LOG = LoggerFactory
			.getLogger(DirectoryWatcher.class);

	private final Map<String, Long> fileHistory = new HashMap<String, Long>();
	private File directoryToWatch;
	private Pattern pattern;
	private DirectoryChangeCallback callback;

	public DirectoryWatcher(String directoryToWatch, Pattern pattern, DirectoryChangeCallback callback) {
		this(new File(directoryToWatch), pattern, callback);
	}

	public DirectoryWatcher(File directoryToWatch, Pattern pattern, DirectoryChangeCallback callback) {
		this.directoryToWatch = directoryToWatch;
		this.pattern = pattern;
		this.callback = callback;
	}

	@Override
	public void run() {
		File[] files = directoryToWatch.listFiles();
		// Get list of files.
		if (files == null || files.length == 0) {
			return;
		}
		for (File file : files) {
			try {
				if (isNewFile(file) && isFileUpdated(file)) {
					fileHistory.put(file.getAbsolutePath(), file.lastModified());
					callback.directoryChanged(file);
				}
			} catch (Exception e) {
				LOG.error("Failure reading file " + file.getName(), e);
			}
		}
	}

	private boolean isNewFile(File file) {
		return fileHistory.containsKey(file.getAbsolutePath());
	}

	private boolean isFileUpdated(File file) {
		Long lastModified = fileHistory.get(file.getAbsolutePath());
		return (file.lastModified() > lastModified);
	}

}
