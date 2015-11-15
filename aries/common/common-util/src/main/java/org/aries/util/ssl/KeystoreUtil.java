package org.aries.util.ssl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.aries.Assert;
import org.aries.util.FileUtil;
import org.aries.util.IOUtil;


public class KeystoreUtil {

	//private static Log log = LogFactory.getLog(KeystoreUtil.class);

	public static void readKeystoreFile(String keystoreFile) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream(keystoreFile);
		byte[] fileData = IOUtils.toByteArray(inputStream);
		String accessibleKeystoreFile = null;
		if (keystoreFile.endsWith("zip"))
			accessibleKeystoreFile = getUnzippedKeystoreFilename(fileData);
		else accessibleKeystoreFile = getKeystoreFile(fileData);
		initializeKeystoreProperties(accessibleKeystoreFile);
	}

    public static String getKeystoreFile(byte[] fileData) throws IOException {
    	File file = getTempFile("TempKeystoreFile.bin", fileData);
        String tempFileName = file.getAbsolutePath();
		InputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(tempFileName);
        IOUtil.copy(inputStream, outputStream);
        return tempFileName;
    }

	public static String getUnzippedKeystoreFilename(byte[] fileData) throws IOException {
    	File file = getTempFile("TempKeystoreFile.zip", fileData);
        String tempFileName = file.getAbsolutePath();
		ZipFile zipFile = new ZipFile(tempFileName);
        Enumeration<?> zipEntries = zipFile.entries();
        ZipEntry zipEntry = (ZipEntry) zipEntries.nextElement();
        tempFileName = tempFileName.replace(".zip", ".bin");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        FileOutputStream outputStream = new FileOutputStream(tempFileName);
        IOUtil.copy(inputStream, outputStream);
        return tempFileName;
    }

	public static byte[] getUnzippedKeystoreFileData(String fileName) throws IOException {
    	Assert.notNull(fileName, "Key-file name must be specified");
    	Assert.isTrue(fileName.endsWith("zip"), "Key-file must be in zip-format");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream(fileName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        byte[] unzippedFileData = getBytes(zipInputStream);
        return unzippedFileData;
    }

	protected static byte[] getBytes(ZipInputStream zipInputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new BufferedInputStream(zipInputStream);
        int b;
        while ((b = inputStream.read()) != -1) {
            outputStream.write(b);
        }
        byte[] byteArray = outputStream.toByteArray();
		return byteArray;
    }
	
	public static byte[] getUnzippedKeystoreFileData(byte[] fileData) throws IOException {
		String fileName = getUnzippedKeystoreFilename(fileData);
		InputStream inputStream = new FileInputStream(fileName);
		byte[] unzippedFileData = IOUtils.toByteArray(inputStream);
        return unzippedFileData;
    }

    protected static File getTempFile(String fileName, byte[] fileData) throws IOException {
    	Assert.notNull(fileData, "Key-file data must be specified");
    	Assert.isTrue(fileData.length > 0, "Key-file data not found");
    	String tempDir = System.getProperty("java.io.tmpdir");
    	String tempFileName = tempDir;
    	if (!tempDir.endsWith(File.separator))
    		tempFileName += File.separator;
		tempFileName += fileName;
    	File file = new File(tempFileName);
    	FileUtil.removeFile(file);
		FileUtils.writeByteArrayToFile(file, fileData);
		return file;
	}

    public static void initializeKeystoreProperties(String keystoreFile) {
    	Assert.notEmpty(keystoreFile, "Key-file must be specified");
    	Properties properties = System.getProperties();
    	properties.setProperty("javax.net.ssl.keyStore", keystoreFile);
    	properties.setProperty("javax.net.ssl.trustStore", keystoreFile);
    	properties.setProperty("javax.net.ssl.keyStorePassword", "client");
		System.setProperties(properties);
    }

}
