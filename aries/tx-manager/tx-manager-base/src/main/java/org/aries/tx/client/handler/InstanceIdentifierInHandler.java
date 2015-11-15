package org.aries.tx.client.handler;

import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.soap.SOAPMessageContext;


public class InstanceIdentifierInHandler extends InstanceIdentifierHandler {

	protected boolean handleMessageOutbound(SOAPMessageContext context) throws ProtocolException {
        return true;
    }

}
