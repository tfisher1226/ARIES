package nam.workspaceService;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.tx.service.rmi.AbstractRMIListener;


//@Startup  
//@Singleton
//@Local(RMIListener.class)
//@ConcurrencyManagement(BEAN)
public class WorkspaceServiceListenerForRMI extends AbstractRMIListener implements Remote {
	
	//@Inject
	//private WorkspaceServiceInterceptor workspaceServiceInterceptor;
	

	public WorkspaceServiceListenerForRMI() throws RemoteException {
		//workspaceServiceInterceptor = new WorkspaceServiceInterceptor();
		registerAsRMIService();
	}

	
	@Override
	//@PostConstruct
	public void initialize() throws Exception {
		registerAsRMIService();
	}
	
	@Override
	public String getModuleId() {
		return "nam-service";
	}
	
	@Override
	public String getServiceId() {
		return WorkspaceService.ID;
	}
	
	@Override
	public Object getDelegate() {
		//return workspaceServiceInterceptor;
		return BeanContext.getFromJNDI(WorkspaceServiceInterceptor.class, "WorkspaceServiceInterceptor");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Serializable invoke(Serializable request, String correlationId, long timeout) throws RemoteException {
		try {
			if (!isInitialized())
				return null;
			if (request instanceof String)
				return processAsText((String) request);
			if (request instanceof Message)
				return processAsBinary((Message) request);
			throw new Exception("Unexpected payload type: "+request.getClass());
		} catch (Throwable e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
}
