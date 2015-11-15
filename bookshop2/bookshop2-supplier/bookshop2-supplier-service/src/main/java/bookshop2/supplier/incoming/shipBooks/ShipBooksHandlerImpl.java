package bookshop2.supplier.incoming.shipBooks;

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
import bookshop2.supplier.SupplierContext;
import bookshop2.supplier.SupplierProcess;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManager;


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
	private SupplierProcess supplierProcess;
	
	@Inject
	private SupplierOrderCacheManager supplierOrderCacheManager;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "ShipBooks";
	}
	
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	public boolean prepare(String transactionId) {
		return supplierOrderCacheManager.prepare(transactionId);
	}
	
	public void commit(String transactionId) {
		supplierOrderCacheManager.commit(transactionId);
	}
	
	public void rollback(String transactionId) {
		supplierOrderCacheManager.rollback(transactionId);
	}
	
	protected SupplierContext getContext(AbstractMessage message) {
		return getContext(getCorrelationId(message));
	}

	protected SupplierContext getContext(String correlationId) {
		requestContext.setCorrelationId(correlationId);
		return SupplierContext.getInstance(correlationId);
	}
	
	@Override
	@Asynchronous
	@AccessTimeout(value = 60000)
	@TransactionAttribute(REQUIRED)
	public void shipBooks(ShipmentRequestMessage shipmentRequestMessage) {
		String correlationId = getCorrelationId(shipmentRequestMessage);
		log.info("#### [supplier]: ShipBooks request received: "+correlationId);
		TimeoutHandler timeoutHandler = null;
		
		try {
			SupplierContext supplierContext = getContext(correlationId);
			supplierContext.validate(shipmentRequestMessage);
			supplierContext.fire_ShipBooks_request_received();

			if (supplierContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", supplierContext);

			switch (getAction(shipmentRequestMessage)) {
			case CANCEL:
				supplierProcess.handle_ShipBooks_request_cancel(shipmentRequestMessage);
				break;
			case UNDO:
				supplierProcess.handle_ShipBooks_request_undo(shipmentRequestMessage);
				break;
			default:
				timeoutHandler = startTimeoutHandler(createTimeoutHandler(correlationId));
				supplierProcess.handle_ShipBooks_request(shipmentRequestMessage);
				timeoutHandler.reset();
				break;
			}
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			supplierProcess.handle_ShipBooks_request_exception(correlationId, e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	protected Runnable createTimeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				String reason = "ShipBooks timed-out";
				supplierProcess.handle_ShipBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
}
