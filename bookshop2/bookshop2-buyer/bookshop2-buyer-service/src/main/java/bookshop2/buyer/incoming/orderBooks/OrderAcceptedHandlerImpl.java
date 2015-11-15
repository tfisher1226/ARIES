package bookshop2.buyer.incoming.orderBooks;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.common.AbstractMessage;
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderAcceptedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.buyer.BuyerProcess;


@Stateful
@Local(OrderAcceptedHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class OrderAcceptedHandlerImpl extends AbstractServiceHandler implements OrderAcceptedHandler {
	
	private static final Log log = LogFactory.getLog(OrderAcceptedHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private BuyerProcess buyerProcess;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "OrderAccepted";
	}
	
	public String getDomain() {
		return "bookshop2.buyer";
	}
	
	public boolean prepare(String transactionId) {
		return true;
	}
	
	public void commit(String transactionId) {
		//nothing for now
	}
	
	public void rollback(String transactionId) {
		//nothing for now
	}
	
	protected BuyerContext getContext(AbstractMessage message) {
		return getContext(getCorrelationId(message));
	}

	protected BuyerContext getContext(String correlationId) {
		requestContext.setCorrelationId(correlationId);
		return BuyerContext.getInstance(correlationId);
	}
	
	@Override
	@Asynchronous
	@AccessTimeout(value = 60000)
	@TransactionAttribute(REQUIRED)
	public void orderAccepted(OrderAcceptedMessage orderAcceptedMessage) {
		String correlationId = getCorrelationId(orderAcceptedMessage);
		log.info("#### [buyer]: OrderAccepted response received: "+correlationId);
		BuyerContext buyerContext = getContext(correlationId);
		
		try {
			buyerContext.validate(orderAcceptedMessage);
			buyerContext.fire_OrderAccepted_response_received();

			if (buyerContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", buyerContext);
			
			buyerProcess.handle_OrderAccepted_response(orderAcceptedMessage);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			buyerContext.fire_OrderAccepted_incoming_response_aborted(e);
		}
	}
	
}
