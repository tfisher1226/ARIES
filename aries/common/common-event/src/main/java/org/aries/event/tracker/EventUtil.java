package org.aries.event.tracker;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.FileUtil;


public class EventUtil {

	private static Log log = LogFactory.getLog(EventUtil.class);

	
	public static void writeOutEventSequence(List<String> eventSequence, String fileName) throws Exception {
		FileUtil.removeFile(fileName);
		FileUtil.assureFileExists(fileName);
		File outputFile = new File(fileName);
		writeOutEventSequence(eventSequence, outputFile);
	}
	
	public static void writeOutEventSequence(List<String> eventSequence, File outputFile) throws Exception {
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		
		try {
			fileOutputStream = new FileOutputStream(outputFile);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			Iterator<String> iterator = eventSequence.iterator();
			while (iterator.hasNext()) {
				String message = iterator.next();
				bufferedOutputStream.write(message.getBytes());
			}
			bufferedOutputStream.flush();

		} finally {
			bufferedOutputStream.close();
		}
	}

	public static List<String> readInEventSequence(String fileName) throws Exception {
		FileUtil.assureFileExists(fileName);
		File inputFile = new File(fileName);
		List<String> lines = readInEventSequence(inputFile);
		return lines;
	}

	public static List<String> readInEventSequence(File inputFile) throws Exception {
		List<String> lines = new ArrayList<String>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(inputFile);
			bufferedReader = new BufferedReader(fileReader);
			while (true) {
				String line = bufferedReader.readLine();
				if (line != null)
					lines.add(line+"\n");
				else break;
			}
			return lines;

		} finally {
			bufferedReader.close();
		}
	}
}
