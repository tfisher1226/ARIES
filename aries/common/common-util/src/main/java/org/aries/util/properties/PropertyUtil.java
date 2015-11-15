package org.aries.util.properties;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class PropertyUtil {

    /**
     * Adds environment variables to specified properties where 
     * keys are prefixed with {@code env.}, e.g. {@code env.PATH}.
     * @param properties The properties to add environment variables to.
     */
    public static void addEnvironmentVariables(Properties properties) {
    	assert properties != null;
    	Properties newProperties = new Properties();
    	for (Map.Entry<String, String> entry: System.getenv().entrySet()) {
    		String key = "env." + entry.getKey();
    		newProperties.setProperty(key, entry.getValue());
    	}
    	properties.putAll(newProperties);
    }
    
    public static void assureAbsolutePath(String propertyName) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue != null) {
            File file = new File(propertyValue);
			String absolutePath = file.getAbsolutePath();
			System.setProperty(propertyName, absolutePath);
        }
    }
    
    public static void insertProperty(String property, Properties properties) {
        String name = null;
        String value = null;

        int i = property.indexOf("=");
        if (i <= 0) {
            name = property.trim();
            value = "true";
        } else {
            name = property.substring(0, i).trim();
            value = property.substring(i + 1);
        }

        properties.setProperty(name, value);
        System.setProperty(name, value);
    }

    @SuppressWarnings("unchecked")
	public static void listProperties() {
		//Collections.sort(new ArrayList(System.getProperties().keySet())).list(System.out);
		Set<Object> keySet = System.getProperties().keySet();
		@SuppressWarnings("rawtypes") List properties = new ArrayList<Object>(keySet);
		Collections.sort(properties);
		Iterator<Object> iterator = properties.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = System.getProperty(key);
			System.out.println("Property: "+key+"="+value);
		}
    }
    
	public static Properties loadProperties(URL url) {
		//log.info("loadJNDIProperties() started: "+name);
		Properties props = null;
		try {
			//log.info("loadJNDIProperties() opening file: "+name);
			InputStream stream = url.openStream();
			props = new Properties();
			//log.info("loadJNDIProperties() loading file: "+stream);
			props.load(stream);
		} catch (Exception e) {
			//log.error(e);
		} finally {
			//log.info(props);
		}
		//log.info("loadJNDIProperties() returns: "+props);
		return props;
	}

}
