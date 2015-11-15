package org.sgiusa.boot;

import javax.ejb.Stateless;

import org.aries.jaxb.JAXBSessionCache;
import org.aries.runtime.BeanContext;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;


//@Startup
@Stateless
@Scope(ScopeType.APPLICATION)
@Name("applicationBootstrap")
public class ApplicationBootstrapBean implements ApplicationBootstrap {

	@Logger
	private Log log;
	
	//@Create
	@Observer("org.jboss.seam.postInitialization")
	public final void init() throws Exception {
		JAXBSessionCache jaxbSessionCache = BeanContext.get("infosgi.jaxbSessionCache");
		jaxbSessionCache.addSchema("/schema/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/aries-validate-1.0.xsd", org.aries.validate.ObjectFactory.class);
		jaxbSessionCache.addSchema("/schema/nam-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-common-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-security-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-execution-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-operation-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-messaging-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-information-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-persistence-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		jaxbSessionCache.addSchema("/schema/nam-application-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
	}
}
