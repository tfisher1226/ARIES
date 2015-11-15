package org.aries.nam.util;

import javax.xml.bind.JAXBException;

import nam.model.Project;

import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.runtime.BeanContext;


public class ModelConverter {

	public String marshal(Project model) throws Exception {
		try {
			String serviceId = "default";
			JAXBSessionCache jaxbSessionCache = BeanContext.get(serviceId + ".jaxbSessionCache");
			JAXBWriter jaxbWriter = jaxbSessionCache.getWriter(serviceId);
			String xmlText = jaxbWriter.marshal(model);
			return xmlText;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
}
