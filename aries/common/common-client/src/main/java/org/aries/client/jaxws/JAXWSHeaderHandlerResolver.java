package org.aries.client.jaxws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;


public class JAXWSHeaderHandlerResolver implements HandlerResolver {
	
    private String userName;
    
    private String password;
    
    private String systemId;
    
    //private String correlationId;

	private JAXWSHeaderHandler headerHandler;

    
    public JAXWSHeaderHandlerResolver(String userName, String password, String systemId) {
          this.userName = userName;
          this.password = password;
          this.systemId = systemId;
    }

	public void setCorrelationId(Object correlationId) {
		headerHandler.setCorrelationId(correlationId);
	}
	
    public List<Handler> getHandlerChain(PortInfo portInfo) {
          List<Handler> handlerChain = new ArrayList<Handler>();
          headerHandler = new JAXWSHeaderHandler(userName, password, systemId, null);
          //headerHandler = new WSSEHeaderHandler();
          handlerChain.add(headerHandler);
          return handlerChain;
    }

}