package org.aries.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;


public class ResourceUtil {
    
    private static final Log log = LogFactory.getLog(ResourceUtil.class);
    
    
    /**
     * Get the specified resource as a string.
     * @param caller The caller's class.
     * @param resource The resource name.
     * @return The string or null if not found.
     * @throws IOException for read errors.
     */
    public static String getResourceAsString(Class<?> caller, final String resource) throws IOException {
        InputStream is = getResourceAsStream(caller, resource);
        if (is == null)
            return null;
        Reader reader = new InputStreamReader(is);
        StringBuffer stringBuffer = new StringBuffer();
        char[] buffer = new char[1024];
        while (true) {
            int count = reader.read(buffer);
            if (count == -1)
                break;
            stringBuffer.append(buffer, 0, count);
        }
        return stringBuffer.toString();
    }
    
    /**
     * Get the specified resource as an input stream.
     * @param caller The caller's class.
     * @param resource The resource name.
     * @return The input stream or null if not found.
     */
    public static InputStream getResourceAsStream(Class<?> classObject, final String resource) {
        if (resource == null || resource.length() == 0)
            return null;
        String absoluteResource;
        if (resource.charAt(0) == '/') {
            absoluteResource = resource;
        } else {
            String callerName = classObject.getName();
            int lastSeparator = callerName.lastIndexOf('.');
            if (lastSeparator == -1) {
                absoluteResource = '/' + resource;
            } else {
                String substring = callerName.substring(0, lastSeparator+1);
				absoluteResource = '/' + substring.replace('.', '/') + resource; 
            }
        }
        URL url = getResourceAsURL(classObject, absoluteResource);
        if (url != null) {
            try {
                InputStream stream = url.openStream();
				return stream;
            } catch (final IOException e) {
            	//ignore
            }
        }
        return null;
    }
    
    /**
     * Get the specified resource as a URL.
     * @param classObject The classObject to use.
     * @param resource The name of the resource.
     * @return The URL or null if not found.
     */
    public static URL getResourceAsURL(Class<?> classObject, String resource) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            final URL url = contextClassLoader.getResource(resource);
            if (url != null)
                return url;
        }
        URL url = classObject.getResource(resource);
        if (url != null)
            return url;
        url = ClassLoader.getSystemResource(resource);
		return url;
    }

    public static URL getResource(String source) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = getResource(loader, source);
        return url;
    }

    public static URL getResource(ClassLoader loader, String source) {
        if (log.isDebugEnabled())
            log.debug("Accessing resource: "+source);
        URL url = loader.getResource(resolveName(source));
        return url;
    }
    
    public static Enumeration<URL> getResources(String source) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls = getResources(loader, source);
        return urls;
    }

    public static Enumeration<URL> getResources(ClassLoader loader, String source) throws IOException {
        if (log.isDebugEnabled())
            log.debug("Accessing resource: "+source);
        Enumeration<URL> urls = loader.getResources(resolveName(source));
        return urls;
    }

    public static String resolveName(String name) {
        if (name != null) {
	        String fs = System.getProperty("file.separator");
	        if (name.startsWith(fs) || name.startsWith("/"))
	            name = name.substring(1);
        }
        return name;
    }
 

    public static InputStream getResourceAsStream(String source) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = getResourceAsStream(loader, source);
        return stream;
    }

    public static InputStream getResourceAsStream(ClassLoader loader, String source) {
        if (log.isDebugEnabled())
            log.debug("Accessing resource: "+source);
        InputStream stream = loader.getResourceAsStream(resolveName(source));
        return stream;
    }

	public static InputStream getResourceAsStream(String packageName, String resourceName) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();     

		packageName = packageName.replace('.', '/');
		Enumeration<URL> packageURLs = classLoader.getResources(packageName);

		URL packageURL = null;
		while (packageURLs.hasMoreElements()) {
			packageURL = packageURLs.nextElement();
			//System.out.println(">>>"+packageURL);
		}

		Assert.notNull(packageURL, "Package not found: "+packageName);
		
		if (packageURL.getProtocol().equals("jar")) {
			String jarFileName = packageURL.getFile();         
			jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));   
			//System.out.println(">>>"+jarFileName);         

			JarFile jarFile = new JarFile(jarFileName);  
			JarEntry jarEntry = jarFile.getJarEntry(resourceName);
			
			Assert.notNull(jarEntry, "Resource not found: "+resourceName);
			
			InputStream stream = jarFile.getInputStream(jarEntry); 
			return stream;

		} else {
			String filePath = packageURL.getFile();
			if (filePath.endsWith(packageName)) {
				filePath = filePath.replace(packageName, "");
				filePath += "/"+resourceName;
			}
			packageURL = new URL("file://"+filePath);
			InputStream stream = packageURL.openStream();
			return stream;
		}  
	}
	
	
}
