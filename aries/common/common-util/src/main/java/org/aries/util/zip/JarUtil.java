package org.aries.util.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class JarUtil {

    public static List<String> getJarContents(URL url) throws IOException {
        return getJarContents(url.openStream());
    }

    public static List<String> getJarContents(InputStream inputStream) throws IOException {
        return readJar(inputStream);
    }
    
    public static List<String> getJarContents(String fileName) throws IOException {
        return getJarContents(new File(fileName));
    }

    public static List<String> getJarContents(File jarFile) throws IOException {
        return readJar(jarFile);
    }

    protected static List<String> readJar(File jarFile) throws IOException {
        List<String> jarContents = new ArrayList<String>();
        try {
            ZipFile zipFile = new ZipFile(jarFile);
            for (Enumeration<?> entry = zipFile.entries(); entry.hasMoreElements();) {
                ZipEntry zipEntry = (ZipEntry) entry.nextElement();
                if (zipEntry.isDirectory())
                    continue;
                String name = zipEntry.getName();
				jarContents.add(name);
            }
        } catch (ZipException e) {
        	throw new RuntimeException(e);
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
        return jarContents;
    }
    
    protected static List<String> readJar(InputStream is) throws IOException {
        List<String> jarContents = new ArrayList<String>();
        try {
            ZipInputStream zipInputStream = new ZipInputStream(is);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.isDirectory())
                    continue;
                jarContents.add(zipEntry.getName());
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }

        return jarContents;
    }
}
