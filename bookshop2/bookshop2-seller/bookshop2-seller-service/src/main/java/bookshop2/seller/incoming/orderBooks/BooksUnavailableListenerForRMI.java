package bookshop2.seller.incoming.orderBooks;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksUnavailableMessage;
import bookshop2.seller.SellerContext;


public class BooksUnavailableListenerForRMI extends AbstractRMIListener implements Remote {
	
	private SellerContext sellerContext;
	
	private BooksUnavailableInterceptor booksUnavailableInterceptor;
	
	
	public BooksUnavailableListenerForRMI() throws RemoteException {
		registerAsRMIService();
	}
	
	
	@Override
	public void initialize() throws Exception {
		registerAsRMIService();
	}
	
	@Override
	public String getModuleId() {
		return "bookshop2-seller-service";
	}
	
	@Override
	public String getServiceId() {
		return BooksUnavailable.ID;
	}
	
	@Override
	public SellerContext getContext() {
		if (sellerContext == null || resetRequested)
			sellerContext = BeanContext.getFromJNDI(SellerContext.class, "SellerContext");
		return sellerContext;
	}
	
	@Override
	public Object getDelegate() {
		if (booksUnavailableInterceptor == null || resetRequested)
			booksUnavailableInterceptor = BeanContext.getFromJNDI(BooksUnavailableInterceptor.class, "BooksUnavailableInterceptor");
		return booksUnavailableInterceptor;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Serializable invoke(Serializable request, String correlationId, long timeout) throws RemoteException {
			if (!isInitialized())
				return null;
		
		try {
			if (request instanceof String)
				return processAsText((String) request);
			if (request instanceof Message)
				return processAsBinary((Message) request);
			throw new Exception("Unexpected payload type: "+request.getClass());
		
		} catch (Throwable e) {
			Throwable cause = ExceptionUtil.getRootCause(e);
			throw new RemoteException(cause.getMessage(), cause);
		}
	}
	
	@Override
	protected void validate(Message message) {
		BooksUnavailableMessage booksUnavailableMessage = message.getPart("booksUnavailableMessage");
		sellerContext.validate(booksUnavailableMessage);
	}
	
}
