package bookshop2.supplier.incoming.queryBooks;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import bookshop2.QueryRequestMessage;
import bookshop2.supplier.SupplierContext;


@Remote(QueryBooks.class)
@Stateless(name = "QueryBooks")
@WebService(name = "queryBooks", serviceName = "queryBooksService", portName = "queryBooks", targetNamespace = "http://bookshop2/supplier")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class QueryBooksListenerForJAXWS implements QueryBooks {
	
	private static final Log log = LogFactory.getLog(QueryBooksListenerForJAXWS.class);
	
	@Inject
	private SupplierContext supplierContext;
	
	@Inject
	private QueryBooksHandler queryBooksHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Oneway
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void queryBooks(QueryRequestMessage queryRequestMessage) {
		if (!Bootstrapper.isInitialized("bookshop2-supplier-service"))
			return;
		
		try {
			supplierContext.validate(queryRequestMessage);
			queryBooksHandler.queryBooks(queryRequestMessage);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
