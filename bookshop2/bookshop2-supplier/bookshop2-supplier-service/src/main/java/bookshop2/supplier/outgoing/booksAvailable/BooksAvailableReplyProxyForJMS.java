package bookshop2.supplier.outgoing.booksAvailable;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksAvailableMessage;


public class BooksAvailableReplyProxyForJMS extends JMSProxy implements Proxy<BooksAvailableReply> {
	
	private BooksAvailableReplyInterceptor booksAvailableInterceptor;
	
	
	public BooksAvailableReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	

	protected void createDelegate() {
		booksAvailableInterceptor = new BooksAvailableReplyInterceptor();
		booksAvailableInterceptor.setProxy(this);
	}

	@Override
	public BooksAvailableReply getDelegate() {
		return booksAvailableInterceptor;
	}

	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			BooksAvailableMessage booksAvailableMessage = MessageUtil.getPart(message, "booksAvailableMessage");
			String replyToDestination = getReplyToDestination(booksAvailableMessage, "BooksAvailable");
			send(replyToDestination, booksAvailableMessage);
			log.info("#### [supplier]: BooksAvailable response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

}
