package bookshop2.supplier;

import org.aries.bean.ProxyLocator;
import org.aries.runtime.BeanContext;


public class ClientFactory {

	public <T> T getClient(String clientId) {
		String proxyKey = clientId;
		ProxyLocator proxyLocator = BeanContext.get("org.aries.proxyLocator");
		T client = proxyLocator.get(proxyKey);
		//if (client == null)
		//	System.out.println();
		//((Client) client).setCorrelationId(getCorrelationId());
		return client;
	}
	
}
