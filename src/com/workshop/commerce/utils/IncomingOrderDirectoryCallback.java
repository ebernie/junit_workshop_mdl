package com.workshop.commerce.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.workshop.commerce.payload.JsonPayload;
import com.workshop.commerce.payload.Payload;
import com.workshop.commerce.processor.ProcessorFactory;

public class IncomingOrderDirectoryCallback implements DirectoryChangeCallback {
	
	private static final Logger LOG = LoggerFactory.getLogger(IncomingOrderDirectoryCallback.class);
	
	@Override
	public void directoryChanged(File file) {
		String fileContent;
		try {
			fileContent = readFromFile(file);
			Payload payload = new JsonPayload(fileContent);
			ProcessorFactory.INSTANCE.getProcessor(payload).doWork();
			file.delete();
		} catch (Exception e) {
			LOG.error("Failure reading file " + file.getName(), e);
		}
	}

	private String readFromFile(File file) throws FileNotFoundException,
			IOException {
		BufferedReader br;
		String currentLine = null;
		br = new BufferedReader(new FileReader(file));
		StringBuffer json = new StringBuffer();
		while ((currentLine = br.readLine()) != null) {
			json.append(currentLine);
		}
		return json.toString();
	}

}
