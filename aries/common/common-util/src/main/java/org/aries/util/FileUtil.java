package org.aries.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.aries.Assert;


public class FileUtil {

	//private static Log log = LogFactory.getLog(FileUtil.class);

	private FileUtil() {
		// This class is not meant to be instantiated.
	}

	public static boolean isAccessible(String filename) {
		URL url = FileUtil.class.getResource(filename);
		return url != null;
	}

	public static boolean directoryExists(String fileName) {
		File file = new File(fileName);
		return file != null && file.exists() && file.isDirectory();
	}

	public static boolean fileExists(String fileName) {
		File file = new File(fileName);
		return file != null && file.exists();
	}

	public static void assureDirectoryExists(String targetPath) throws IOException {
		targetPath = targetPath.replace("/", File.separator);
		assureDirectoryExists(new File(targetPath));
	}

	public static void assureDirectoryExists(File targetPath) throws IOException {
		if (targetPath.exists())
			Assert.isTrue(targetPath.isDirectory(), "Path is not a directory: "+targetPath);
		if (!targetPath.exists())
			targetPath.mkdirs();
	}

	public static void assureFileExists(String targetPath) throws IOException {
		assureFileExists(new File(targetPath));
	}

	public static void assureFileExists(File targetPath) throws IOException {
		if (targetPath.exists())
			Assert.isTrue(targetPath.isFile(), "Path is not a file: "+targetPath);
		if (!targetPath.exists()) {
			assureDirectoryExists(targetPath.getParentFile());
			targetPath.createNewFile();
		}
	}

	public static InputStream openFileAsStream(String filePath) {
		try {
			FileInputStream stream = new FileInputStream(filePath);
			return stream;
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public static String readStreamAsString(InputStream inputStream) throws IOException { 
		Assert.notNull(inputStream, "Stream must be specified");
		/* 
		 * To convert the InputStream to String we use the 
		 * Reader.read(char[] buffer) method. We iterate until the 
		 * Reader return -1 which means there's no more data to 
		 * read. We use the StringWriter class to produce the string. 
		 */
		try { 
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			Reader reader = new BufferedReader(inputStreamReader); 

			int n = 0; 
			char[] buffer = new char[1024]; 
			Writer writer = new StringWriter(); 
			while ((n = reader.read(buffer)) != -1)
				writer.write(buffer, 0, n); 
			return writer.toString(); 
		} finally { 
			inputStream.close(); 
		}
	}

	public static void removeFile(String fileName) throws IOException {
		removeFile(new File(fileName));
	}

	public static void removeFile(File file) throws IOException {
		try {
			FileUtils.forceDelete(file);
		} catch (FileNotFoundException e) {
			//ignore
		}
	} 

	public static void removeDirectory(String fileName) throws IOException {
		removeDirectory(new File(fileName));
	}

	public static void removeDirectory(File file) throws IOException {
		try {
			FileUtils.forceDelete(file);
		} catch (FileNotFoundException e) {
			//ignore
		}
	} 

	public static String normalizePath(String path) {
		String fileSeparator = System.getProperty("file.separator");
		String newPath = path.replace("/", fileSeparator);
		//if (newPath.substring(0, 1).equals(fileSeparator))
		//	newPath = newPath.substring(1);
		return newPath;
	}

	public static String getDirectory(String fileName) {
		int lastIndexOf = fileName.lastIndexOf("/");
		if (lastIndexOf > 0)
			return fileName.substring(0, lastIndexOf);
		String fileSeparator = System.getProperty("file.separator");
		lastIndexOf = fileName.lastIndexOf(fileSeparator);
		if (lastIndexOf > 0)
			return fileName.substring(0, lastIndexOf);
		return null;
	}

	public static String getFileName(String fileName) {
		int lastIndexOf = fileName.lastIndexOf("/");
		if (lastIndexOf > 0)
			return fileName.substring(lastIndexOf + 1);
		String fileSeparator = System.getProperty("file.separator");
		lastIndexOf = fileName.lastIndexOf(fileSeparator);
		if (lastIndexOf > 0)
			return fileName.substring(lastIndexOf + 1);
		return null;
	}

	public static void saveStreamToFile(InputStream resourceAsStream, String tmpFilePath) {
		
	}


}
