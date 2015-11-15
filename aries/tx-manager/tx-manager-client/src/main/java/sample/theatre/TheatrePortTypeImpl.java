/*
 * TheatreServiceAT.java
 * Copyright (c) 2003, 2004 Arjuna Technologies Ltd.
 * $Id: TheatreServiceAT.java,v 1.3 2004/12/01 16:27:21 kconner Exp $
 *
 */

package sample.theatre;

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


@WebService(name = "TheatrePortType", 
		portName = "TheatrePortType",
		serviceName = "TheatreService",
		wsdlLocation = "/META-INF/wsdl/sample-theatre-service.wsdl",
		targetNamespace = "http://www.aries.com/demo/Theatre")
@HandlerChain(file = "/context-handlers.xml")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class TheatrePortTypeImpl implements TheatrePortType {

	private static Log log = LogFactory.getLog(TheatrePortType.class);

	@Resource
	private WebServiceContext webServiceContext;


	@WebMethod
	public void bookSeats(
			@WebParam(name = "howMany", partName = "howMany")
			int howMany,
			@WebParam(name = "which_area", partName = "which_area")
			int whichArea) {

		MessageContext messageContext = webServiceContext.getMessageContext();
		Header header = Header.getHeader(messageContext);

//		UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
//		try {
//			userTransaction.beginSubordinate(0);
//		} catch (SystemException e) {
//			e.printStackTrace();
//		}

		String transactionId = header.getInstanceIdentifier().getInstanceIdentifier();
		
		
        log.info("Request received, transactionId: "+transactionId);

        TheatreProcessor processor = new TheatreProcessorImpl();
		processor.bookSeats(transactionId, howMany, whichArea);

//		try {
//			userTransaction.beginSubordinate(0);
//		} catch (SystemException e) {
//			e.printStackTrace();
//		}

	}
}
