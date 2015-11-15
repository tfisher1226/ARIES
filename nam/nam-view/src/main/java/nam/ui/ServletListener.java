package nam.ui;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import nam.ui.design.WorkspaceManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.RuntimeContext;
import org.aries.runtime.BeanContext;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;


@WebListener
public class ServletListener extends Bootstrapper implements ServletContextListener, HttpSessionListener {

	private static final Log log = LogFactory.getLog(ServletListener.class);
	

	@Override
	public String getDomain() {
		return "aries.org";
	}

	@Override
	public String getApplication() {
		return "nam";
	}
	
	@Override
	public String getModule() {
		return "nam-view";
	}

	public String getContextId() {
		return getDomain() + "." + getApplication() + "." + getModule();
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		RuntimeContext.getInstance().setServletContext(servletContext);
		
		servletContext.setAttribute("domainId", getDomain());
		servletContext.setAttribute("applicationId", getApplication());
		servletContext.setAttribute("moduleId", getModule());
		servletContext.setAttribute("contextId", getContextId());

		try {
			//CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
			//CheckpointManager.addConfiguration("nam-service-checks.xml");
			loadSchema("/model/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
			loadSchema("/model/nam/nam-1.0.xsd",  nam.model.ObjectFactory.class);
			super.initializeAsClientModule();
			setInitialized(true);

			String propertyPrefix = getPropertyPrefix();
			String modelFileName = getProperty(propertyPrefix + ".application_model_file");
			servletContext.setAttribute("modelFileName", modelFileName);
			
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
