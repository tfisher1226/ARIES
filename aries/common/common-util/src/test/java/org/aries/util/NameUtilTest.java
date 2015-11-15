package org.aries.util;

import org.aries.Assert;

import junit.framework.TestCase;


public class NameUtilTest extends TestCase {

	public void setUp() throws Exception {
		super.setUp();
	}

    public void tearDown() throws Exception {
		super.tearDown();
	}

    public void testNormalizePath_Unix() throws Exception {
    	String inputFilePath = "/www/ddd/xxx";
    	String expectedFilePath = "\\www\\ddd\\xxx";
		String normalizedPath = NameUtil.normalizePath(inputFilePath);
		Assert.equals(expectedFilePath, normalizedPath);
    }
    
    public void testNormalizePath_Win32() throws Exception {
    	String inputFilePath = "c:/www/ddd/xxx";
    	String expectedFilePath = "c:\\www\\ddd\\xxx";
		String normalizedPath = NameUtil.normalizePath(inputFilePath);
		Assert.equals(expectedFilePath, normalizedPath);
    }

    public void testNormalizePath_Win32_WithDrive() throws Exception {
    	String inputFilePath = "/www/ddd/xxx";
    	String expectedFilePath = "\\www\\ddd\\xxx";
		String normalizedPath = NameUtil.normalizePath(inputFilePath);
		Assert.equals(expectedFilePath, normalizedPath);
    }

    public void testNormalizePath_Mixed() throws Exception {
    	String inputFilePath = "/www\\ddd/xxx\\aaa";
    	String expectedFilePath = "\\www\\ddd\\xxx\\aaa";
		String actualFilePath = NameUtil.normalizePath(inputFilePath);
		Assert.equals(expectedFilePath, actualFilePath);

    	inputFilePath = "\\www/ddd\\xxx/aaa";
    	expectedFilePath = "\\www\\ddd\\xxx\\aaa";
		actualFilePath = NameUtil.normalizePath(inputFilePath);
		Assert.equals(expectedFilePath, actualFilePath);
    }
    
    public void testNormalizePath_Mixed_WithDrive() throws Exception {
    	String inputFilePath = "c:/www\\ddd/xxx\\aaa";
    	String expectedFilePath = "c:\\www\\ddd\\xxx\\aaa";
		String actualFilePath = NameUtil.normalizePath(inputFilePath);
		Assert.equals(expectedFilePath, actualFilePath);
   
    	inputFilePath = "c:\\www/ddd\\xxx/aaa";
    	expectedFilePath = "c:\\www\\ddd\\xxx\\aaa";
		actualFilePath = NameUtil.normalizePath(inputFilePath);
		Assert.equals(expectedFilePath, actualFilePath);
    }
    
    
    
}
