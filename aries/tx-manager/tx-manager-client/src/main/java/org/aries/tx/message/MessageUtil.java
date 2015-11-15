package org.aries.tx.message;

import javax.ejb.MessageDrivenContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.aries.message.util.MessageConstants;


public class MessageUtil implements MessageConstants {
	
	public static String getCorrelationId(WebServiceContext webServiceContext) {
        return getCorrelationId(webServiceContext.getMessageContext());
    }

	public static String getCorrelationId(MessageContext messageContext) {
        return (String) messageContext.get(PROPERTY_CORRELATION_ID);
    }

	public static void putCorrelationId(MessageContext messageContext, String correlationId) {
        messageContext.put(PROPERTY_CORRELATION_ID, correlationId);
        messageContext.setScope(PROPERTY_CORRELATION_ID, MessageContext.Scope.APPLICATION);
    }
    
	public static String getTransactionId(WebServiceContext webServiceContext) {
        return getTransactionId(webServiceContext.getMessageContext());
    }

	public static String getTransactionId(MessageContext messageContext) {
        return (String) messageContext.get(PROPERTY_TRANSACTION_ID);
    }

	public static String getTransactionId(MessageDrivenContext messageDrivenContext) {
		return (String) messageDrivenContext.lookup(PROPERTY_TRANSACTION_ID);
	}
	
	public static void putTransactionId(MessageContext messageContext, String transactionId) {
        messageContext.put(PROPERTY_TRANSACTION_ID, transactionId);
        messageContext.setScope(PROPERTY_TRANSACTION_ID, MessageContext.Scope.APPLICATION);
    }

//	public static void putTransactionId(MessageDrivenContext messageDrivenContext, String transactionId) {
//		messageDrivenContext.put(PROPERTY_TRANSACTION_ID, transactionId);
//    }

}
