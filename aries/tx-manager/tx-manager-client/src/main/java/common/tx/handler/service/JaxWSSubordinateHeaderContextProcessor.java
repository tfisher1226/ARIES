package common.tx.handler.service;

import javax.xml.ws.handler.soap.SOAPMessageContext;


/**
 * specialised version which creates and interposes a subordinate AT transaction when
 * it finds an incoming AT context in the message headers
 */
public class JaxWSSubordinateHeaderContextProcessor extends JaxWSHeaderContextProcessor {
	
    /**
     * Process the tx context header that is attached to the received message.
     * @param msgContext
     * @return true
     */
    protected boolean handleInboundMessage(SOAPMessageContext soapMessageContext) {
        return handleInboundMessage(soapMessageContext, true);
    }

}
