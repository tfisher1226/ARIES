package nam.projectService;

import org.aries.bean.Proxy;
import org.aries.tx.service.ejb.EJBProxy;


public class ProjectServiceProxyForEJB extends EJBProxy implements Proxy<ProjectService> {
	
	private ProjectServiceInterceptor projectServiceInterceptor;
	
	
	public ProjectServiceProxyForEJB(String serviceId, String host, int port) {
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
