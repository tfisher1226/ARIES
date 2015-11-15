package org.aries.util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.ExecutorService;

import org.aries.util.concurrent.ExecutorFactory;


public class StreamPrinter implements Runnable {
    
    private boolean inputEnabled;

    private boolean outputEnabled;
    
    private InputStream inputStream;

    private OutputStream outputStream;

    private StreamListener streamListener;
    
    private Writer writer;

    private String threadGroup;

    private String prefix;

    private String postfix;

    
    public StreamPrinter(InputStream inputStream, OutputStream outputStream, String threadGroup) {
        this(inputStream, outputStream, threadGroup, "", "\n");
    }
    
    public StreamPrinter(InputStream inputStream, OutputStream outputStream, String threadGroup, String prefix, String postfix) {
        setInputStream(inputStream);
        setOutputStream(outputStream);
        setThreadGroup(threadGroup);
        setPrefix(prefix);
        setPostfix(postfix);
        initialize();
    }
    
    protected void initialize() {
        inputEnabled = true;
        outputEnabled = true;
        if (outputStream == null)
            outputStream = System.out;
        writer = new PrintWriter(outputStream);
    }

    
    public boolean isInputEnabled() {
        return inputEnabled;
    }

    public void setInputEnabled(boolean enabled) {
        inputEnabled = enabled;
    }
    
    public boolean isOutputEnabled() {
        return outputEnabled;
    }

    public void setOutputEnabled(boolean enabled) {
        this.outputEnabled = enabled;
    }
    
    public StreamListener getStreamListener() {
        return streamListener;
    }
    
    public void setStreamListener(StreamListener streamListener) {
    	this.streamListener = streamListener;
    }

    protected Writer getWriter() {
        return writer;
    }
    
    protected void setWriter(Writer writer) {
    	this.writer = writer;
    }

    protected InputStream getInputStream() {
        return inputStream;
    }

    protected void setInputStream(InputStream inputStream) {
    	this.inputStream = inputStream;
    }
    
    protected OutputStream getOutputStream() {
        return outputStream;
    }

    protected void setOutputStream(OutputStream outputStream) {
    	this.outputStream = outputStream;
    }
    
    protected String getThreadGroup() {
        return threadGroup;
    }

   protected void setThreadGroup(String threadGroup) {
    	this.threadGroup = threadGroup;
    }

    protected String getPrefix() {
        return prefix;
    }

    protected void setPrefix(String prefix) {
    	this.prefix = prefix;
    }

    protected String getPostfix() {
        return postfix;
    }

    protected void setPostfix(String postfix) {
    	this.postfix = postfix;
    }

    public void start() {
		ExecutorService executer = ExecutorFactory.createSingleThreadExecutor(getThreadGroup());
		executer.execute(this);
    }

    public void run() {
        if (inputEnabled) try {
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader buffer = new BufferedReader(reader);
            String line = null;
            while ((line = buffer.readLine()) != null) {
                if (outputEnabled) {
                    String s = prefix + line + postfix;
                    writer.write(s);
                    writer.flush();
                    if (streamListener != null)
                    	streamListener.writeLine(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

}