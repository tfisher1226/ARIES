package nam.projectService;

import org.aries.bean.Proxy;
import org.aries.tx.service.rmi.RMIProxy;


public class ProjectServiceProxyForRMI extends RMIProxy implements Proxy<ProjectService> {
	
	private ProjectServiceInterceptor projectServiceInterceptor;
	
	
	public ProjectServiceProxyForRMI(String serviceId, String host, int port) {
		super(serviceId, host, port);
		createDelegate();
	}
	
	
	protected void createDelegate() {
		projectServiceInterceptor = new ProjectServiceInterceptor();
		projectServiceInterceptor.setProxy(this);
	}
	
	@Override
	public ProjectService getDelegate() {
		return projectServiceInterceptor;
	}
	
}
