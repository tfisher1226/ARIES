package redhat.jee_migration_example.outgoing.item;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ItemReplyProxyForRMI extends RMIProxy implements Proxy<ItemReply> {
	
	private ItemReplyInterceptor itemReplyInterceptor;
	
	
	public ItemReplyProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		itemReplyInterceptor = new ItemReplyInterceptor();
		itemReplyInterceptor.setProxy(this);
	}
	
	@Override
	public ItemReply getDelegate() {
		return itemReplyInterceptor;
	}
	
}
