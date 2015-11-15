package bookshop2.buyer.ui;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.RuntimeContext;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.launcher.Bootstrap;
import org.aries.runtime.BeanContext;
import org.aries.util.ExceptionUtil;
import org.aries.util.FileUtil;


public class ServletListener implements ServletContextListener, HttpSessionListener {

	private static final Log log = LogFactory.getLog(ServletListener.class);
	

	public String getDomain() {
		return "bookshop2-buyer";
	}

	public String getModule() {
		return "bookshop2-buyer.view";
	}
	
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		RuntimeContext.getInstance().setServletContext(servletContext);
		String propertyName = getDomain() + ".application_runtime_home";

		try {
			String runtimeHome = System.getProperty(propertyName);
			Assert.notNull(runtimeHome, "External property not found: "+propertyName);
			Bootstrap bootstrapper = new Bootstrap();
			bootstrapper.setDomainName(getDomain());
			bootstrapper.setModuleName(getModule());
			BeanContext.set(getDomain() + ".bootstrapper", bootstrapper);
			bootstrapper.initialize(runtimeHome);
			runtimeHome = FileUtil.normalizePath(runtimeHome);

			JAXBSessionCache jaxbSessionCache = BeanContext.get(getDomain() + ".jaxbSessionCache");
			
			// Add schema for: http://admin
			String schemaFileName1 = "file://" + runtimeHome + "/model/admin/admin_model.xsd";
			jaxbSessionCache.addSchema(schemaFileName1, admin.ObjectFactory.class);
			
			// Add schema for: http://bookshop2
			String schemaFileName2 = "file://" + runtimeHome + "/model/bookshop/bookshop_information.xsd";
			jaxbSessionCache.addSchema(schemaFileName2, bookshop2.ObjectFactory.class);

			// Add schema for: http://bookshop2/seller
			String schemaFileName3 = "file://" + runtimeHome + "/model/bookshop/bookshop_seller_information.xsd";
			jaxbSessionCache.addSchema(schemaFileName3, bookshop2.seller.ObjectFactory.class);
			
			// Add schema for: http://www.aries.org/common
			String schemaFileName4 = "file://" + runtimeHome + "/model/common/aries-common-1.0.xsd";
			jaxbSessionCache.addSchema(schemaFileName4, org.aries.common.ObjectFactory.class);

		} catch (Exception e) {
			Throwable rootCause = ExceptionUtil.getRootCause(e);
			log.error("Error:", e);
			log.error("Caused by:", rootCause);
			throw new RuntimeException(rootCause);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		//nothing for now
	}

	public void sessionCreated(HttpSessionEvent event) {
		//nothing for now
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		//nothing for now
	}
	   
}
