package bookshop2.buyer.client.orderBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import bookshop2.OrderRequestMessage;


public class OrderBooksClient extends AbstractDelegate implements OrderBooks {

	private static final Log log = LogFactory.getLog(OrderBooksClient.class);


	@Override
	public String getDomain() {
		return "bookshop2.buyer";
	}

	@Override
	public String getServiceId() {
		return OrderBooks.ID;
	}

	@SuppressWarnings("unchecked")
	public OrderBooks getProxy() throws Exception {
		return getProxy(OrderBooks.ID);
	}

	@Override
	public void orderBooks(OrderRequestMessage orderRequestMessage) {
		try {
			getProxy().orderBooks(orderRequestMessage);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}

}
