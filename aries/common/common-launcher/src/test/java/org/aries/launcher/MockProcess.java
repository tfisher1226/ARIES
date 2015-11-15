package org.aries.launcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class MockProcess extends Process {

    private InputStream errorStreamToReturn;
    
    private InputStream inputStreamToReturn;

    private OutputStream outputStreamToReturn;

    private boolean wasDestroyed;

    
    public int exitValue() {
        return 0;
    }

    public int waitFor() {
        return 0;
    }

    public void destroy() {
        wasDestroyed = true;
    }

    public boolean wasDestroyed() {
        return wasDestroyed;
    }

    public InputStream getErrorStream() {
        if (errorStreamToReturn != null)
            return errorStreamToReturn;
        String data = "errorStream";
        return new ByteArrayInputStream(data.getBytes());
    }

    public InputStream setErrorStreamToReturn() {
        return errorStreamToReturn;
    }

    public InputStream getInputStream() {
        if (inputStreamToReturn != null)
            return inputStreamToReturn;
        String data = "inputStream";
        return new ByteArrayInputStream(data.getBytes());
    }

    public InputStream setInputStreamToReturn() {
        return inputStreamToReturn;
    }

    public OutputStream getOutputStream() {
        if (outputStreamToReturn != null)
            return outputStreamToReturn;
        return new ByteArrayOutputStream();
    }

    public OutputStream setOutputStreamToReturn() {
        return outputStreamToReturn;
    }

}
