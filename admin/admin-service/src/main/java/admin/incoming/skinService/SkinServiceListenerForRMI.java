package admin.incoming.skinService;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.aries.message.Message;
import org.aries.tx.service.rmi.AbstractRMIListener;


//@Startup
//@Singleton
//@Local(RMIListener.class)
//@ConcurrencyManagement(BEAN)
public class SkinServiceListenerForRMI extends AbstractRMIListener implements Remote {
	
	//@Inject
	private SkinServiceInterceptor skinServiceInterceptor;
	
	
	public SkinServiceListenerForRMI() throws RemoteException {
		skinServiceInterceptor = new SkinServiceInterceptor();
	}
	
	
	@Override
	//@PostConstruct
	public void initialize() throws Exception {
		registerAsRMIService();
	}
	
	@Override
	public String getModuleId() {
		return "admin-service";
	}
	
	@Override
	public String getServiceId() {
		return SkinService.ID;
	}
	
	@Override
	public Object getDelegate() {
		return skinServiceInterceptor;
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
