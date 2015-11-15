package common.tx.handler.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transaction;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionRegistry;
import org.aries.tx.message.MessageUtil;

import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import com.arjuna.ats.jta.TransactionManager;

import common.tx.CoordinationConstants;


public class JaxwsTransactionInterceptor implements SOAPHandler<SOAPMessageContext> {

	private static Log log = LogFactory.getLog(JaxwsTransactionInterceptor.class);
	
	
	/**
	 * Gets the header blocks that can be processed by this Handler instance.
	 */
	public Set<QName> getHeaders() {
		Set<QName> headerSet = new HashSet<QName>();
		headerSet.add(new QName(CoordinationConstants.WSCOOR_NAMESPACE, CoordinationConstants.WSCOOR_ELEMENT_COORDINATION_CONTEXT));
		return headerSet;
	}

	/**
	 * Sets the header blocks that can be processed by this Handler instance.
	 * Note: this impl ignores this function's args as the values are hardcoded.
	 */
	public void setHeaders(Set headers) {
	}

	/**
	 * Process a message. Determines if it is inbound or outbound and dispatches accordingly.
	 * @param msgContext
	 * @return true
	 */
	public boolean handleMessage(SOAPMessageContext soapMessageContext) {
		Boolean outbound = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound == null)
			throw new IllegalStateException("Outbound property not found");
		if (outbound) 
			return handleOutboundMessage(soapMessageContext);
		return handleInboundMessage(soapMessageContext);
	}

	/**
	 * Process the tx context header that is attached to the received message.
	 * @param messageContext The current message context.
	 * @return true
	 */
	protected boolean handleInboundMessage(SOAPMessageContext soapMessageContext) {
		String transactionId = MessageUtil.getTransactionId(soapMessageContext);
		if (transactionId == null)
			return true;
		try {
			Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
			javax.transaction.TransactionManager transactionManager = TransactionManager.transactionManager();
			transactionManager.resume(transaction);
			boolean transactionExists = transaction != null;
			if (!transactionExists) {
				transactionManager.suspend();
				transactionManager.begin();
				Transaction childTransaction = transactionManager.getTransaction();
				transactionId = ThreadActionData.currentAction().get_uid().toString();
				TransactionRegistry.getInstance().registerTransaction(transactionId, childTransaction);
			}
			return true;
			
		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}


	/**
	 * Tidy up the Transaction/Thread association before response is returned to the client.
	 * @param messageContext The current message context.
	 * @return true
	 */
	protected boolean handleOutboundMessage(SOAPMessageContext soapMessageContext) {
		String transactionId = MessageUtil.getTransactionId(soapMessageContext);
		if (transactionId == null)
			return true;
		try {
			Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
			javax.transaction.TransactionManager transactionManager = TransactionManager.transactionManager();
			boolean transactionExists = transaction != null;
			if (!transactionExists) {
				transactionManager.commit();
				transactionManager.resume(transaction);
			}
			//suspendTransaction();
			return true;

		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}

	/**
	 * Tidy up the Transaction/Thread association before faults are thrown back to the client.
	 * @param messageContext
	 * @return true
	 */
	public boolean handleFault(SOAPMessageContext soapMessageContext) {
		try {
			String transactionId = MessageUtil.getTransactionId(soapMessageContext);
			Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
			javax.transaction.TransactionManager transactionManager = TransactionManager.transactionManager();
			boolean transactionExists = transaction != null;
			if (!transactionExists) {
				try {
					transactionManager.rollback();
					transactionManager.resume(transaction);
				} catch (Exception e2) {
					log.error(e2);
				}
			}
			//suspendTransaction();
			return true;

		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}

	public void close(MessageContext messageContext) {
	}
	
}
