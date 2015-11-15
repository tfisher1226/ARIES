package org.aries.node;

import org.aries.registry.LinkStateRegistry;
import org.aries.registry.ServiceLocator;
import org.aries.registry.ServiceProxyFactory;
import org.aries.registry.ServiceRegistry;
import org.aries.runtime.BeanContext;


public class NodeManager {

	public void initialize() throws Exception {
		BeanContext.set("org.aries.namespaceContext", new NamespaceContext(null));
		BeanContext.set("org.aries.serviceRegistry", new ServiceRegistry());
		BeanContext.set("org.aries.serviceLocator", new ServiceLocator());
		BeanContext.set("org.aries.serviceProxyFactory", new ServiceProxyFactory());
		BeanContext.set("org.aries.linkStateRegistry", new LinkStateRegistry());
	}
	
}
