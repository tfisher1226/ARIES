package bookshop2.shipper.incoming.shipBooks;

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
import org.aries.process.TimeoutHandler;
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentRequestMessage;
import bookshop2.shipper.ShipperContext;
import bookshop2.shipper.ShipperProcess;


@Stateful
@Local(ShipBooksHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class ShipBooksHandlerImpl extends AbstractServiceHandler implements ShipBooksHandler {
	
	private static final Log log = LogFactory.getLog(ShipBooksHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
		
	@Inject
	private ShipperProcess shipperProcess;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "ShipBooks";
	}
	
	public String getDomain() {
		return "bookshop2.shipper";
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
	
	protected ShipperContext getContext(AbstractMessage message) {
		return getContext(getCorrelationId(message));
	}

	protected ShipperContext getContext(String correlationId) {
		requestContext.setCorrelationId(correlationId);
		return ShipperContext.getInstance(correlationId);
	}
	
	@Override
	@Asynchronous
	@AccessTimeout(value = 60000)
	@TransactionAttribute(REQUIRED)
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage) {
		String correlationId = getCorrelationId(shipmentRequestMessage);
		log.info("#### [shipper]: ShipBooks request received: "+correlationId);
		TimeoutHandler timeoutHandler = null;
		
		try {
			ShipperContext shipperContext = getContext(correlationId);
			shipperContext.validate(shipmentRequestMessage);
			shipperContext.fire_ShipBooks_request_received();
			
			if (shipperContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", shipperContext);

			switch (getAction(shipmentRequestMessage)) {
			case CANCEL:
				shipperProcess.handle_ShipBooks_request_cancel(shipmentRequestMessage);
				break;
			case UNDO:
				shipperProcess.handle_ShipBooks_request_undo(shipmentRequestMessage);
				break;
			default:
				timeoutHandler = startTimeoutHandler(createTimeoutHandler(correlationId));
				shipperProcess.handle_ShipBooks_request(shipmentRequestMessage);
				timeoutHandler.reset();
				break;
			}
			
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			shipperProcess.handle_ShipBooks_request_exception(correlationId, e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	protected Runnable createTimeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				String reason = "ShipBooks timed-out";
				shipperProcess.handle_ShipBooks_request_timeout(correlationId, reason);
		}
		};
	}
	
}
