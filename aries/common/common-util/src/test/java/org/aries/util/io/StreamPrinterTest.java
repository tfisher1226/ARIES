package org.aries.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import junit.framework.TestCase;

import org.aries.util.text.MockWriter;


public class StreamPrinterTest extends TestCase {

    private StreamPrinter fixture;
       
    
	public void setUp() throws Exception {
		super.setUp();
		fixture = new StreamPrinter(createInputStreamForTest(), createOutputStreamForTest(), "threadGroup");
	}

    public void tearDown() throws Exception {
		fixture = null;
		super.tearDown();
	}
    
    private String getTextForTest() {
        return "foobar";
    }

    private String createPrefixForTest() {
        return "prefix";
    }

    private String createPostfixForTest() {
        return "postfix";
    }

    private InputStream createInputStreamForTest() throws Exception {
        return new ByteArrayInputStream(getTextForTest().getBytes());
    }

    private OutputStream createOutputStreamForTest() throws Exception {
        return new ByteArrayOutputStream();
    }

    public void testConstructor1() throws Exception {
        InputStream inputStream = createInputStreamForTest();
        OutputStream outputStream = createOutputStreamForTest();
		fixture = new StreamPrinter(inputStream, outputStream, "threadGroup");
		assureValuesCorrect(inputStream, outputStream, "", "\n");
    }
    
    public void testConstructor2() throws Exception {
        InputStream inputStream = createInputStreamForTest();
        OutputStream outputStream = createOutputStreamForTest();
        String prefix = createPrefixForTest();
        String postfix = createPostfixForTest();
		fixture = new StreamPrinter(inputStream, outputStream, "threadGroup", prefix, postfix);
		assureValuesCorrect(inputStream, outputStream, prefix, postfix);
    }

    private void assureValuesCorrect(InputStream inputStream, OutputStream outputStream, String prefix, String postfix) {
        assertNotNull("Writer should not be null", fixture.getWriter());
        assertNotNull("InputStream should not be null", fixture.getInputStream());
        assertNotNull("OutputStream should not be null", fixture.getOutputStream());
        assertNotNull("Prefix should not be null", fixture.getPrefix());
        assertNotNull("Postfix should not be null", fixture.getPostfix());
        assertTrue("Writer class should be correct", fixture.getWriter() instanceof PrintWriter);
        assertEquals("InputStream should be equal", inputStream, fixture.getInputStream());
        assertEquals("OutputStream should be equal", outputStream, fixture.getOutputStream());
        assertEquals("Prefix should be equal", prefix, fixture.getPrefix());
        assertEquals("Postfix should be equal", postfix, fixture.getPostfix());
    }
    
    public void testInitialize() throws Exception {
        fixture.setOutputStream(null);
        fixture.initialize();
        assertNotNull("OutputStream should not be null", fixture.getOutputStream());
        assertEquals("OutputStream should be equal", System.out, fixture.getOutputStream());
        assertTrue("Writer class should be correct", fixture.getWriter() instanceof PrintWriter);
    }
    
    public void testRun() throws Exception {
        MockWriter writer = new MockWriter(fixture.getOutputStream());
        fixture.setWriter(writer);
        fixture.run();
        assertEquals("Output line count should be correct", 1, writer.getData().size());
        String output = (String) writer.getData().get(0);
        assertEquals("Output line count should be correct", getTextForTest()+"\n", output);
    }
    
    //TODO test usage of buffer in run
    
}
