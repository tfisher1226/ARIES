package org.aries.workflow;

import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.aries.Assert;
import org.aries.nam.model.old.ServiceDescripter;
import org.aries.runtime.BeanContext;


public class ExecutionContext {

	//private String serviceId;
	
	private ServiceDescripter serviceDescripter;

	private ServletContext servletContext;

	private WebServiceContext webServiceContext;

	private boolean webServiceInitialized;


	public ExecutionContext() {
		//nothing for now
	}
	
	public ExecutionContext(ServiceDescripter serviceDescripter, WebServiceContext webServiceContext) {
		Assert.notNull(serviceDescripter, "Service descripter must be specified");
		this.serviceDescripter = serviceDescripter;
		if (webServiceContext != null) {
			servletContext = initializeContext(webServiceContext);
			Assert.notNull(servletContext, "ServletContext must exist");
		}
	}
	
	public boolean isWebServiceInitialized() {
		return webServiceInitialized;
	}

	public ServiceDescripter getServiceDescripter() {
		return serviceDescripter;
	}

	public void setServiceDescripter(ServiceDescripter serviceDescripter) {
		this.serviceDescripter = serviceDescripter;
	}

	public WebServiceContext getWebServiceContext() {
		return webServiceContext;
	}

	public void setWebServiceContext(WebServiceContext webServiceContext) {
		this.webServiceContext = webServiceContext;
	}

	public ServletContext initializeContext(ServiceDescripter serviceDescripter, WebServiceContext webServiceContext) {
		Assert.notNull(serviceDescripter, "Service descripter must be specified");
		this.serviceDescripter = serviceDescripter;
		servletContext = initializeContext(webServiceContext);
		//Assert.notNull(servletContext, "ServletContext must exist");
		return servletContext;
	}
	
	protected ServletContext initializeContext(WebServiceContext webServiceContext) {
		//Assert.notNull(webServiceContext, "WebServiceContext must be specified");
		ServletContext servletContext = null;
		try {
			if (webServiceContext != null && webServiceContext.getMessageContext() != null) {
				servletContext = (ServletContext) webServiceContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
				webServiceInitialized = true;
				return servletContext;
			}
		} catch (IllegalStateException e) {
			//do nothing
		}
		
		//if (servletContext == null)
		//	servletContext = ServletLifecycle.getCurrentServletContext();
		//if (servletContext == null)
		//	servletContext = ServletLifecycle.getServletContext();
		return servletContext;
	}
	
	public void beginContext() {
		if (webServiceInitialized)
			BeanContext.begin(servletContext);
		else BeanContext.begin();
	}

	public void endContext() {
		if (webServiceInitialized)
			BeanContext.end(servletContext);
		else BeanContext.end();
	}
	
}
