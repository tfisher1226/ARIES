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
import org.aries.runtime.RequestContext;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentScheduledMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.supplier.SupplierProcess;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManager;


@Stateful
@Local(ShipmentScheduledHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class ShipmentScheduledHandlerImpl extends AbstractServiceHandler implements ShipmentScheduledHandler {
	
	private static final Log log = LogFactory.getLog(ShipmentScheduledHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private SupplierProcess supplierProcess;
	
	@Inject
	private SupplierOrderCacheManager supplierOrderCacheManager;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	

	public String getName() {
		return "ShipmentScheduled";
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
	public void shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage) {
		String correlationId = getCorrelationId(shipmentScheduledMessage);
		log.info("#### [supplier]: ShipmentScheduled response received: "+correlationId);
		SupplierContext supplierContext = getContext(correlationId);
		
		try {
			supplierContext.validate(shipmentScheduledMessage);
			supplierContext.fire_ShipmentScheduled_response_received();

			if (supplierContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", supplierContext);

			supplierProcess.handle_ShipmentScheduled_response(shipmentScheduledMessage);
		
		} catch (Throwable e) {
			log.error("Error", e);
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			supplierContext.fire_ShipmentScheduled_incoming_response_aborted(e);
		}
	}
	
}
