package nam.workspaceService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class WorkspaceServiceProxyForRMI extends RMIProxy implements Proxy<WorkspaceService> {
	
	private WorkspaceServiceInterceptor workspaceServiceInterceptor;
	
	
	public WorkspaceServiceProxyForRMI(String serviceId, String host, int port) {
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
