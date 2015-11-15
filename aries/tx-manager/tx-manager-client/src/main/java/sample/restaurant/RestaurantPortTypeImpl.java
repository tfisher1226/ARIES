/*
 * RestaurantServiceAT.java
 * Copyright (c) 2003, 2004 Arjuna Technologies Ltd
 * $Id: RestaurantServiceAT.java,v 1.3 2004/12/01 16:26:44 kconner Exp $
 */

package sample.restaurant;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.model.Header;


@WebService(name = "RestaurantPortType", 
		portName = "RestaurantPortType",
		serviceName = "RestaurantService",
        wsdlLocation = "/META-INF/wsdl/sample-restaurant-service.wsdl",
        targetNamespace = "http://www.aries.com/demo/Restaurant")
@SOAPBinding(style=SOAPBinding.Style.RPC)
@HandlerChain(file = "/context-handlers.xml")
public class RestaurantPortTypeImpl implements RestaurantPortType {

	private static Log log = LogFactory.getLog(RestaurantPortType.class);
	
	@Resource
	private WebServiceContext webServiceContext;


    @WebMethod
    public void bookSeats(
            @WebParam(name = "howMany", partName = "howMany")
            int howMany) {
    	
		MessageContext messageContext = webServiceContext.getMessageContext();
		Header header = Header.getHeader(messageContext);

        String transactionId = header.getInstanceIdentifier().getInstanceIdentifier();
        log.info("["+transactionId+"] Request received for table of "+howMany);

        RestaurantProcessor processor = new RestaurantProcessorImpl();
		processor.bookSeats(transactionId, howMany);

		//TheatreClient.INSTANCE.bookSeats(6, 1);
    }

}
