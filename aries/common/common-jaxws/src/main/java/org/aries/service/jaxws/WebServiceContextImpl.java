package org.aries.service.jaxws;

import java.security.Principal;

import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;


// Implementation of javax.xml.ws.WebServiceContext
public class WebServiceContextImpl implements WebServiceContext {
    
	private static final Log LOG = LogFactory.getLog(WebServiceContext.class);

    private static ThreadLocal<MessageContext> globalContext = new ThreadLocal<MessageContext>();

    
    public WebServiceContextImpl() {
    	//nothing for now
    }

    public WebServiceContextImpl(MessageContext messageContext) { 
        setMessageContext(messageContext);
    } 

    @Override
    public final MessageContext getMessageContext() {
        return globalContext.get();
    }

    public void setMessageContext(MessageContext messageContext) {
    	globalContext.set(messageContext);
    }

    protected SecurityContext getSecurityContext() {
        SecurityContext context = (SecurityContext) getMessageContext().get(SecurityContext.class.getName());
        return context;
    }
    
    @Override
    public final Principal getUserPrincipal() {
        return getSecurityContext().getUserPrincipal();
    }

    @Override
    public final boolean isUserInRole(final String role) {
        return getSecurityContext().isUserInRole(role);
    }
    
    @Override
    public EndpointReference getEndpointReference(Element... referenceParameters) {
        return null;
    }

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element... referenceParameters) {
        return null;
    }

    public static void clear() {
    	globalContext.set(null);
    }

}
