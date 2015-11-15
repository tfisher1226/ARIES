package org.sgiusa.boot;

import javax.ejb.Stateless;

import org.aries.jaxb.JAXBSessionCache;
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
		JAXBSessionCache.INSTANCE.addSchema("/schema/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		JAXBSessionCache.INSTANCE.addSchema("/schema/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
		JAXBSessionCache.INSTANCE.addSchema("/schema/aries-validate-1.0.xsd", org.aries.validate.ObjectFactory.class);
		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-1.0.xsd", org.aries.nam.model.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-common-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-security-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-execution-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-operation-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-messaging-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-information-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-persistence-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
//		JAXBSessionCache.INSTANCE.addSchema("/schema/nam-application-1.0.xsd", org.aries.nam.model.old.ObjectFactory.class);
	}
}
