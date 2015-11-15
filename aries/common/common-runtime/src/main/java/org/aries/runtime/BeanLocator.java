package org.aries.runtime;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;


public class BeanLocator {

	public static <T> T lookup(Class<T> classObject) {
		GlobalJNDIName jndiName = new GlobalJNDIName();
		return lookup(classObject, jndiName.asString());
	}

    /**
     * @param clazz the type (Business Interface or Bean Class)
     * @param jndiName the global JNDI name with the pattern: java:global[/]//#
     * @return The local or remote reference to the bean.
     */
    public static <T> T lookup(Class clazz, String jndiName) {
            Object bean = lookup(jndiName);
            return (T) clazz.cast(PortableRemoteObject.narrow(bean, clazz));
    }

    public static Object lookup(String jndiName) {
        Context context = null;
        try {
            context = new InitialContext();
            return context.lookup(jndiName);
        } catch (NamingException ex) {
            throw new IllegalStateException("Cannot connect to bean: " + jndiName + " Reason: " + ex, ex.getCause());
        } finally {
            try {
            	if (context != null)
            		context.close();
            } catch (NamingException ex) {
                throw new IllegalStateException("Cannot close InitialContext. Reason: " + ex, ex.getCause());
            }
        }
    }
    
    
	public static class GlobalJNDIName {

		private final static String PREFIX = "java:global";

		private StringBuilder builder;

		private String applicationName = "bookshop2-shipper";

		private String moduleName = "bookshop2-shipper-service-0.0.1-SNAPSHOT";

		private String beanName = "ShipperProcess";
		

		public GlobalJNDIName() {
			//loading the configuration
		}

		public GlobalJNDIName withAppLicationName(String applicationName) {
			this.applicationName = applicationName;
			return this;
		}

		public GlobalJNDIName withModuleName(String moduleName) {
			this.moduleName = moduleName;
			return this;
		}

		public GlobalJNDIName withBeanName(String beanName) {
			this.beanName = beanName;
			return this;
		}

//		public GlobalJNDIName withInterface(Class<Serializable> classObject) {
//			this.beanName = classObject.getName();
//			return this;
//		}


		String computeBeanName(Class beanClass) {
			return null;
		}

		private boolean isNotEmpty(String name){
			return (name != null && !name.isEmpty());
		}

		public String asString() {
			return PREFIX + "/" + applicationName + "/" + moduleName + "/" + beanName;
			//return "java:global/bookshop2-shipper/bookshop2-shipper-service-0.0.1-SNAPSHOT/ShipperProcess";
		}

		public <T> T locate(Class clazz) {
			return BeanLocator.lookup(clazz, asString());
		}

		public Object locate() {
			return BeanLocator.lookup(asString());
		}

	}

}
