package org.aries.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ProcessUtil {
	
	private static Log log = LogFactory.getLog(ProcessUtil.class);

    private static final Object _lock = new Object();

    private static int _pid = -1;

    
    public static int getpid() {
        synchronized (_lock) {
            if (_pid == -1) {
                String cmd[] = null;
                File tempFile = null;

                if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) 
                	cmd = new String[] {"/bin/sh", "-c", "echo $$ $PPID"};
                else {
                    try {
                        // http://www.scheibli.com/projects/getpids/index.html (GPL)
                        tempFile = File.createTempFile("getpids", "ts");

                        byte[] bytes = new byte[1024];
                        int read;
                        InputStream in = ProcessUtil.class.getResourceAsStream("getpids.exe");
                        OutputStream out = new FileOutputStream(tempFile);

                        try {
                            while ((read = in.read(bytes)) != -1)
                                out.write(bytes, 0, read);
                        } finally {
                            in.close();
                            out.close();
                        }

                        cmd = new String[] { tempFile.getAbsolutePath() };
                        
                    } catch (Exception ex) {
                        throw new RuntimeException("Error", ex);
                    }
                }

                if (cmd != null) {
                    Process p = null;

                    try {
                        p = Runtime.getRuntime().exec(cmd);
                    } catch (IOException e) {
                        throw new RuntimeException("Error ", e);
                    }

                    ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int read;

                    try {
                        while ((read = p.getInputStream().read(bytes)) != -1)
                            bstream.write(bytes, 0, read);
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot read pid", e);
                    } finally {
                        try {
                            bstream.close();
                        } catch (Exception e) {
                            log.warn(e);
                        }
                    }

                    if (tempFile != null)
                        tempFile.delete();

                    StringTokenizer theTokenizer = new StringTokenizer(bstream.toString());
                    theTokenizer.nextToken();

                    String pid = theTokenizer.nextToken();

                    try {
                        _pid = Integer.parseInt(pid);
                    } catch (final Exception e) {
                    	log.error(e);
                    }
                }
            }
        }

        if (_pid == -1)
            throw new RuntimeException("Could not create pid");

        return _pid;
    }

}

