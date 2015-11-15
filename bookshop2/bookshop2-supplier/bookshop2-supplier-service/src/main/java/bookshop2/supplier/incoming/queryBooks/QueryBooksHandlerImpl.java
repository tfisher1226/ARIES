package bookshop2.supplier.incoming.queryBooks;

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

import bookshop2.QueryRequestMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.supplier.SupplierProcess;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManager;


@Stateful
@Local(QueryBooksHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class QueryBooksHandlerImpl extends AbstractServiceHandler implements QueryBooksHandler {
	
	private static final Log log = LogFactory.getLog(QueryBooksHandlerImpl.class);
	
	@Inject
	private RequestContext requestContext;
	
	@Inject
	private SupplierProcess supplierProcess;
	
	@Inject
	private SupplierOrderCacheManager supplierOrderCacheManager;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public String getName() {
		return "QueryBooks";
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
	public void queryBooks(QueryRequestMessage queryRequestMessage) {
		String correlationId = getCorrelationId(queryRequestMessage);
		log.info("#### [supplier]: QueryBooks request received: "+correlationId);
		TimeoutHandler timeoutHandler = null;
		
		try {
			SupplierContext supplierContext = getContext(correlationId);
			supplierContext.validate(queryRequestMessage);
			supplierContext.fire_QueryBooks_request_received();

			if (supplierContext.isGlobalTransactionActive())
				enrollTransaction("bookReservation", supplierContext);

			switch (getAction(queryRequestMessage)) {
			case CANCEL:
				supplierProcess.handle_QueryBooks_request_cancel(queryRequestMessage);
				break;
			case UNDO:
				supplierProcess.handle_QueryBooks_request_undo(queryRequestMessage);
				break;
			default:
				timeoutHandler = startTimeoutHandler(createTimeoutHandler(correlationId));
				supplierProcess.handle_QueryBooks_request(queryRequestMessage);
				timeoutHandler.reset();
				break;
			}
		
		} catch (Throwable e) {
			log.error("Error", e);
			if (timeoutHandler != null)
				timeoutHandler.reset();
			e = ExceptionUtil.getRootCause(e);
			transactionSynchronizationRegistry.setRollbackOnly();
			supplierProcess.handle_QueryBooks_request_exception(correlationId, e);
			
		} finally {
			timeout = DEFAULT_TIMEOUT;
		}
	}
	
	protected Runnable createTimeoutHandler(final String correlationId) {
		return new Runnable() {
			public void run() {
				String reason = "QueryBooks timed-out";
				supplierProcess.handle_QueryBooks_request_timeout(correlationId, reason);
			}
		};
	}
	
}
