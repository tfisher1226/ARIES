package org.aries.jaxb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;


public class JAXBSessionClassLoader extends ClassLoader {

	private Map<String, Object> objectFactories = null;

	private JAXBSessionClassLoader(ClassLoader parent, Map<String, Object> objectFactories) {
		super(parent);
		this.objectFactories = objectFactories;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException
	{
		Class<?> ret;
		try
		{
			ret = super.loadClass(name);
		}
		catch (ClassNotFoundException e)
		{
			Object objectFactory = objectFactories.get(name);
			if (objectFactory != null)
			{
				ret = objectFactory.getClass();
			}
			else
			{
				throw new ClassNotFoundException(name + " class not found");
			}
		}
		return ret;
	}
	

	@Override
	public InputStream getResourceAsStream(String name)
	{
		if (name!=null && name.equals("META-INF/services/javax.xml.bind.JAXBContext"))
		{
			return new ByteArrayInputStream("com.sun.xml.bind.v2.ContextFactory".getBytes());
		}
		return super.getResourceAsStream(name);
	}
	
}