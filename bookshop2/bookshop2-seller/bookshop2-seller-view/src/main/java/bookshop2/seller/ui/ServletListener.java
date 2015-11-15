package bookshop2.seller.ui;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.RuntimeContext;
import org.aries.launcher.Bootstrap;
import org.aries.runtime.BeanContext;
import org.jboss.seam.log.LogProvider;
import org.jboss.seam.log.Logging;


public class ServletListener implements ServletContextListener, HttpSessionListener {

	private static final LogProvider log = Logging.getLogProvider(ServletListener.class);

	private static String PROPERTY_PREFIX = "bookshop2.seller";

	/**
	 * Notification that the web application is ready to process requests.
	 * Loads properties from potential/expected property sources.
	 * @param servletContextEvent
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		RuntimeContext.getInstance().setServletContext(servletContext);
		String propertyName = PROPERTY_PREFIX + ".application_runtime_home";

		try {
			String runtimeHome = System.getProperty(propertyName);
			Assert.notNull(runtimeHome, "External property not found: "+propertyName);
			Bootstrap bootstrapper = new Bootstrap();
			BeanContext.set(PROPERTY_PREFIX+".bootstrapper", bootstrapper);
			bootstrapper.initialize(runtimeHome);

		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			log.error("Error:", e);
			log.error("Caused by:", rootCause);
			throw new RuntimeException(rootCause);
		}
	}

	/**
	 * Notification that the servlet context is about to be shut down.
	 * Shuts down all cache managers known to {@link CacheManager#ALL_CACHE_MANAGERS}
	 * @param servletContextEvent
	 */
	public void contextDestroyed(ServletContextEvent event) {
//		super.contextDestroyed(event);
	}

	
	public void sessionCreated(HttpSessionEvent event) {
//		ServletLifecycle.beginSession(event.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
//		JBossClusterMonitor monitor = JBossClusterMonitor.getInstance(event.getSession().getServletContext());
//		if (monitor != null && monitor.failover()) {
//			// If application is unfarmed or all nodes shutdown simultaneously, cluster cache may still fail to retrieve SFSBs to destroy
//			log.debug("Detected fail-over, not destroying session context");
//		} else {
//			ServletLifecycle.endSession(event.getSession());
//		}
	}
	   
}
