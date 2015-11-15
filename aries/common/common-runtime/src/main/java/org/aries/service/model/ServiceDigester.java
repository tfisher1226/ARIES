package org.aries.service.model;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.nam.model.old.ActionDefinition;
import org.aries.nam.model.old.ParameterDefinition;
import org.aries.nam.model.old.ResultDefinition;
import org.aries.nam.model.old.ServiceDefinition;
import org.aries.util.ResourceUtil;


public class ServiceDigester {

    private static Log log = LogFactory.getLog(ServiceDigester.class);
    
    private String _servicesFile;

    private ClassLoader _classLoader;

    
    public ServiceDigester(String servicesFile) {
    	_servicesFile = servicesFile;
    }
    
    public ServiceDigester(ClassLoader classLoader, String servicesFile) {
		this(servicesFile);
		_classLoader = classLoader;
	}

	public String getSource() {
        return _servicesFile;
    }

    public ServiceDescripterMap execute() throws Exception {
        InputStream stream = null;
        try {
            String source = getSource();
            if (_classLoader != null)
            	Thread.currentThread().setContextClassLoader(_classLoader);
            URL url = ResourceUtil.getResource(source);
			if (url == null) {
				String message = "Service configuration file not found: "+source;
				log.warn(message);
				//throw new Exception(message);
				return null;
			}
            stream = url.openStream();
            ServiceDescripterMap configuration = digest(stream);
            return configuration;
        } finally {
        	if (stream != null)
        		stream.close();
        }
    }
    
    public ServiceDescripterMap digest(InputStream stream) throws Exception {
    	Digester digester = new Digester();
    	digester.setClassLoader(Thread.currentThread().getContextClassLoader());
    	digester.addObjectCreate("services", ServiceDescripterMap.class);
    	
    	digester.addObjectCreate("services/service", ServiceDefinition.class);
    	digester.addSetProperties("services/service", "group", "groupName");
    	digester.addSetProperties("services/service", "name", "serviceName");
    	digester.addSetProperties("services/service", "class", "className");
    	digester.addSetProperties("services/service", "description", "description");
    	
    	digester.addObjectCreate("services/service/action", ActionDefinition.class);
    	digester.addSetProperties("services/service/action", "name", "actionName");
    	digester.addSetProperties("services/service/action", "class", "className");
    	digester.addSetProperties("services/service/action", "description", "description");

    	digester.addObjectCreate("services/service/action/parameter", ParameterDefinition.class);
    	digester.addSetProperties("services/service/action/parameter", "name", "name");
    	digester.addSetProperties("services/service/action/parameter", "type", "type");

    	digester.addObjectCreate("services/service/action/result", ResultDefinition.class);
    	digester.addSetProperties("services/service/action/result", "name", "name");
    	digester.addSetProperties("services/service/action/result", "type", "type");

    	digester.addSetNext("services/service/action/parameter", "addParameterDescriptor");
    	digester.addSetNext("services/service/action/result", "setResultDescriptor");
    	digester.addSetNext("services/service/action", "addActionDescriptor");
    	digester.addSetNext("services/service", "addServiceDescriptor");
    	digester.parse(stream);

    	ServiceDescripterMap configuration = (ServiceDescripterMap) digester.getRoot();
    	return configuration;
    }

}
