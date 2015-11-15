package bookshop2.supplier.outgoing.booksUnavailable;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.aries.bean.Proxy;
import org.aries.jms.util.MessageUtil;
import org.aries.tx.service.jms.JMSProxy;
import org.aries.util.ExceptionUtil;

import bookshop2.BooksUnavailableMessage;


public class BooksUnavailableReplyProxyForJMS extends JMSProxy implements Proxy<BooksUnavailableReply> {
	
	private BooksUnavailableReplyInterceptor booksUnavailableInterceptor;
	
	
	public BooksUnavailableReplyProxyForJMS(String serviceId) {
		super(serviceId);
		createDelegate();
	}
	

	protected void createDelegate() {
		booksUnavailableInterceptor = new BooksUnavailableReplyInterceptor();
		booksUnavailableInterceptor.setProxy(this);
	}

	@Override
	public BooksUnavailableReply getDelegate() {
		return booksUnavailableInterceptor;
	}

	@Override
	public void send(Serializable message) throws NamingException, JMSException {
		try {
			BooksUnavailableMessage booksUnavailableMessage = MessageUtil.getPart(message, "booksUnavailableMessage");
			String replyToDestination = getReplyToDestination(booksUnavailableMessage, "BooksUnavailable");
			send(replyToDestination, booksUnavailableMessage);
			log.info("#### [supplier]: BooksUnavailable response sent");
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

}
