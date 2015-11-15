package bookshop2.supplier.incoming.queryBooks;

import javax.ejb.SessionContext;
import javax.xml.rpc.handler.MessageContext;

import org.aries.Assert;
import org.aries.tx.message.MessageUtil;
import org.aries.validate.util.Check;

import bookshop2.OrderRequestMessage;


public class QueryBooksValidator {

	
	public QueryBooksValidator() {
		
	}

	public void validateCorrelationId(String correlationId) {
		Assert.notNull(correlationId, "CorrelationId null");
		Assert.notEmpty(correlationId, "CorrelationId empty");
	}
	
	public void validateTransactionId(String transactionId) {
		Assert.notNull(transactionId, "TransactionId null");
		Assert.notEmpty(transactionId, "TransactionId empty");
	}

	public void validate(MessageContext messageContext) {
		String correlationId = (String) messageContext.getProperty(MessageUtil.PROPERTY_CORRELATION_ID);
		String transactionId = (String) messageContext.getProperty(MessageUtil.PROPERTY_TRANSACTION_ID);
		validateCorrelationId(correlationId);
		validateTransactionId(transactionId);
	}
	
	public void validate(SessionContext sessionContext) {
		validate(sessionContext.getMessageContext());
	}

	public void validate(OrderRequestMessage orderRequestMessage) {
		Check.isValid("orderRequestMessage", orderRequestMessage);
	}
	
}
