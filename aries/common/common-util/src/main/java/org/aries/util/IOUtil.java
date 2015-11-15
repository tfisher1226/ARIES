package org.aries.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import javax.activation.DataHandler;
import javax.xml.ws.WebServiceException;


public class IOUtil {

	public static final String NL = System.getProperty("line.separator");

	public static final String FS = System.getProperty("file.separator");

	public static final String USER_HOME = System.getProperty("user.home");

	public static final String USER_DIR = System.getProperty("user.dir");

	private IOUtil() {
		// This class is not meant to be instantiated. It is a groups static helper methods.
    }
	
	public static String toString(File file) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		copy(inputStream, outputStream);
		return outputStream.toString();

	}

	/**
	 * Reads the bytes from the input and writes them to the
	 * output.
	 *
	 * @param input - the input stream
	 * @param output - the output stream
	 *
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] b = new byte[10240];  // 10K buffer
		int result = in.read(b);
		while (result != -1) {
			out.write(b,0,result);
			result = in.read(b);
		}
	}

	/**
	 * Returns the stack trace of an <code>Throwable</code>
	 * as a <code>String</code>.
	 *
	 * @param e - an instance of <code>Throwable</code>
	 *
	 * @return the exception's stack trace as a
	 *    <code>String</code>
	 */
	public static String stackTrace(Throwable exception) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		exception.printStackTrace(printStream);
		return outputStream.toString();

	}

	public static Writer getCharsetFileWriter(File file, String charset) throws IOException {
		return new OutputStreamWriter(new FileOutputStream(file), charset);
	}

	/** 
	 * Copies specified input stream to specified output stream.
	 */
	public static void copyStream(OutputStream outStream, InputStream inStream) throws IOException {
		try {
			byte[] bytes = new byte[1024];
			int count = inStream.read(bytes);
			while (count > 0) {
				outStream.write(bytes, 0, count);
				count = inStream.read(bytes);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			inStream.close();
		}
	}

	/** 
	 * Copies specified reader to specified output stream.
	 */
	public static void copyReader(OutputStream stream, Reader reader) throws IOException {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(stream);
			char[] bytes = new char[1024];
			int length = reader.read(bytes);
			while (length > 0) {
				writer.write(bytes, 0, length);
				length = reader.read(bytes);
			}
		} catch (IOException e) {
			throw e;
		} finally{
			reader.close();
		}
	}

	public static byte[] convertToBytes(DataHandler dh) {
		try {
			ByteArrayOutputStream buffOS = new ByteArrayOutputStream();
			dh.writeTo(buffOS);
			return buffOS.toByteArray();
		} catch (IOException e) {
			throw new WebServiceException("Unable to convert DataHandler to byte[]: " + e.getMessage());
		}
	}

	/**
	 * Transform a Reader to an InputStream
	 * Background is that DocumentBuilder.parse() cannot take the Reader directly
	 */
	public static InputStream transformReader(Reader reader) throws IOException {
		try {
			int capacity = 1024;
			char[] charBuffer = new char[capacity];
			StringBuffer strBuffer = new StringBuffer(capacity);

			int len = reader.read(charBuffer, 0, capacity);
			while (len > 0) {
				strBuffer.append(charBuffer, 0, len);
				len = reader.read(charBuffer, 0, capacity);
			}
			return new ByteArrayInputStream(strBuffer.toString().getBytes());
		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
	}

}
