package org.aries.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;


public class PropertyManager {

	///** Name of the default application-level property file path. */
	//private static final String DEFAULT_PROPERTY_FILE = "/application.properties";

	///** Name of the default server-level property file path. */
	//private static final String SERVER_PROPERTY_FILE = "server.properties";

	///** Name of the Environment variable to check for the property location path. */
	//private static final String GLOBAL_PROPERTY_HOME_ENVIRONMENT_VARIABLE = "ARIES_PROPERTY_HOME";

	///** Name of the System variable to check for the property location path. */
	//private static final String GLOBAL_PROPERTY_HOME_SYSTEM_VARIABLE = "aries.property.home";

	///** Default value for the property location path (used only if not found in above variables). */
	//private static final String GLOBAL_PROPERTY_LOCATION = "c:/workspace/ARIES/aries/properties";

	private static Log log = LogFactory.getLog(PropertyManager.class);
	
	private static PropertyManager instance = new PropertyManager();
	
	public static PropertyManager getInstance() {
		return instance;
	}
	
	
	private Properties properties;
	
	private AtomicBoolean initialized;
	
	private String propertyLocation;
	
	//private String serverPropertyFile;

	//private String defaultPropertyFile;

	private Object mutex;

	
	public PropertyManager() {
		properties = new Properties();
		initialized = new AtomicBoolean();
		mutex = new Object();
		instance = this;
	}

	public String getPropertyLocation() {
		Assert.isTrue(initialized, "Not yet initialized");
		return propertyLocation;
	}

	public void setPropertyLocation(String propertyLocation) {
		this.propertyLocation = propertyLocation;
	}
	
//	public String getServerPropertyFile() {
//		Assert.isTrue(initialized, "Not yet initialized");
//		return serverPropertyFile;
//	}

//	public String getDefaultPropertyFile() {
//		//Assert.isTrue(initialized, "Not yet initialized");
//		//if (!initialized.get())
//		//	initialize();
//		return defaultPropertyFile;
//	}

	
	public void reset() {
		synchronized (mutex) {
			initialized.set(false);
			initialize();
		}
	}
	
	protected void assertInitialized() {
		Assert.isTrue(initialized.get(), "PropertyManager must be initialized");
	}

	public void initialize() {
		synchronized (mutex) {
			if (!initialized.get()) {
				initializeLocations();
				initializeProperties();
				integrateSystemProperties(properties);
				initialized.set(true);
			} else {
				//assuming globalPropertyLocation has changed
				initializeProperties();
			}
		}
	}

	void initializeLocations() {
		log.debug("Locations initializing...");
		//defaultPropertyFile = DEFAULT_PROPERTY_FILE;
		//serverPropertyFile = SERVER_PROPERTY_FILE;
		//if (propertyLocation == null)
		//	propertyLocation = System.getenv().get(GLOBAL_PROPERTY_HOME_ENVIRONMENT_VARIABLE);
		//if (propertyLocation == null)
		//	propertyLocation = System.getProperty(GLOBAL_PROPERTY_HOME_SYSTEM_VARIABLE);
		//if (propertyLocation == null)
		//	propertyLocation = GLOBAL_PROPERTY_LOCATION;
		Assert.notEmpty(propertyLocation, "Property location must be specified");
		log.info("Global Property Location: "+propertyLocation);
		log.debug("Locations initialized");
	}
	
	void initializeProperties() {
		log.debug("Properties initializing...");
		//loadPropertiesFromFile(defaultPropertyFile);
		loadPropertiesFromDirectory(propertyLocation);
		//loadPropertiesFromFile(serverPropertyFile);
		log.debug("Properties initialized");
	}

	protected void loadPropertiesFromDirectory(String sourceDirectory) {
		//System.out.println(">>"+sourceDirectory);
		File directory = new File(sourceDirectory);
		File[] files = directory.listFiles();
		loadPropertiesFromFiles(files);
	}

	protected void loadPropertiesFromClasspath(String sourceDirectory) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(sourceDirectory);
		File directory = new File(resource.getPath());
		File[] files = directory.listFiles();
		loadPropertiesFromFiles(files);
	}

	protected void loadPropertiesFromFiles(File[] sourceFiles) {
		if (sourceFiles != null) {
			for (int i=0; i < sourceFiles.length; i++) {
				File file = sourceFiles[i];
				String path = file.getPath();
				if (path.endsWith(".properties")) {
					loadPropertiesFromFile(file);
				}
			}
		}
	}
	
	protected void loadPropertiesFromFile(File sourceFile) {
		try {
			FileInputStream stream = new FileInputStream(sourceFile);
			properties.load(stream);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	protected void loadPropertiesFromFile(String sourceFile) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream(sourceFile);
			//FileInputStream stream = new FileInputStream(sourceFile);
			if (stream != null) {
				properties.load(stream);
			}
		} catch (IOException e) {
			log.error(e);
		}
	}

	
//	public void propagate() {
//		propagatePropertyTokensIntoConfigurationFiles();
//	}
//
//	protected void propagatePropertyTokensIntoConfigurationFiles() {
//		PropertyInitializer propertyInitializer = new PropertyInitializer();
//		propertyInitializer.initialize();
//	}


	public <T> T get(Object key) {
		assertInitialized();
		Assert.notNull(key);
		synchronized (mutex) {
			@SuppressWarnings("unchecked")
			T value = (T) properties.get(key);
			return value;
		}
	}

	public int getInt(String key) {
		assertInitialized();
		String string = get(key);
		if (string == null)
			return 0;
		int value = Integer.parseInt(string);
		return value;
	}

	public boolean getBoolean(String key) {
		assertInitialized();
		Object object = get(key);
		if (object == null)
			return false;
		if (object instanceof Boolean) {
			boolean value = (Boolean) object;
			return value;
		}
		if (object instanceof String) {
			String text = (String) object;
			boolean value = Boolean.parseBoolean(text);
			return value;
		}
		throw new IllegalArgumentException("Unrecognized type: "+object.getClass());
	}
	
	public void addProperty(Object key, Object value) {
		assertInitialized();
		Assert.notNull(key);
		synchronized (mutex) {
			properties.put(key, value);
		}
	}
	
	public Object getSystemProperty(String key) {
		assertInitialized();
		Assert.notEmpty(key);
		Object property = System.getProperty(key);
		return property;
	}
	
	public Object setSystemProperty(String key, String value) {
		assertInitialized();
		Assert.notEmpty(key);
		Object property = System.setProperty(key, value);
		return property;
	}
	
	void integrateSystemProperties(Properties properties) {
		log.debug("System properties integrating...");
		Properties systemProperties = System.getProperties();
		Enumeration<Object> keys = systemProperties.keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			if (key instanceof String) {
				String keyCode = (String) key;
				if (keyCode.indexOf("aries.") >= 0) {
					Object value = systemProperties.get(keyCode);
					properties.put(key, value);
				}
			}
		}
		log.debug("System properties integrated");
	}

}
