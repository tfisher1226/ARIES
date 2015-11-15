package org.aries.runtime;

import org.aries.Assert;
import org.aries.runtime.BeanLocator;
import org.junit.Test;


public class BeanLocatorTest {

	@Test
	public void jndiName() {
		String expected = "java:global/applicationName/moduleName/beanName";
		//String expected = "java:global/applicationName/moduleName/beanName#java.io.Serializable";
		String actual = new BeanLocator.GlobalJNDIName().
				withAppLicationName("applicationName").
				withModuleName("moduleName").
				withBeanName("beanName").
				//withInterface(Serializable.class).
				asString();
		Assert.equals(expected, actual);
	}

}
