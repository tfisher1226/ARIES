package org.aries.util.text;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class MockWriter extends Writer {

    private List data;
    
    private boolean echo;
    
    private boolean flushCalled;
    
    private boolean closeCalled;
    
    
    public MockWriter(OutputStream outputStream) {
        super(outputStream);
        data = new ArrayList();
    }

    public List getData() {
        return data;
    }
    
    public void setEcho(boolean echo) {
        this.echo = echo;
    }
    
    public void flush() throws IOException {
        flushCalled = true;
    }

    public void close() throws IOException {
        closeCalled = true;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i < len; i++)
            buf.append(cbuf[i+off]);
        data.add(buf.toString());
        if (echo) {
            System.out.print(buf.toString());
	        System.out.print("\n");
        }
    }

}
