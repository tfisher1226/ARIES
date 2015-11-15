package bookshop2.supplier.client.queryBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.QueryRequestMessage;


public class QueryBooksClient extends AbstractDelegate implements QueryBooks {
	
	private static final Log log = LogFactory.getLog(QueryBooksClient.class);
	
	
	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return QueryBooks.ID;
	}
	
	@SuppressWarnings("unchecked")
	public QueryBooks getProxy() throws Exception {
		return getProxy(QueryBooks.ID);
	}
	
	@Override
	public void queryBooks(QueryRequestMessage queryRequestMessage) {
		try {
			getProxy().queryBooks(queryRequestMessage);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
