package nam.workspaceService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class WorkspaceServiceProxyForEJB extends EJBProxy implements Proxy<WorkspaceService> {
	
	private WorkspaceServiceInterceptor workspaceServiceInterceptor;
	
	
	public WorkspaceServiceProxyForEJB(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		workspaceServiceInterceptor = new WorkspaceServiceInterceptor();
		workspaceServiceInterceptor.setProxy(this);
	}
	
	@Override
	public WorkspaceService getDelegate() {
		return workspaceServiceInterceptor;
	}
	
}
