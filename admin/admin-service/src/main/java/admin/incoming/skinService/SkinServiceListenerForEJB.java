package admin.incoming.skinService;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.io.Serializable;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.aries.message.Message;
import org.aries.tx.service.ejb.AbstractEJBListener;
import org.aries.tx.service.ejb.EJBListener;


@Startup
@Stateful
@Local(EJBListener.class)
@ConcurrencyManagement(BEAN)
public class SkinServiceListenerForEJB extends AbstractEJBListener {
	
	@Inject
	private SkinServiceInterceptor skinServiceInterceptor;
	

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
	public Serializable invoke(Serializable payload, String correlationId, long timeout) {
		if (!isInitialized())
			return null;
		
		try {
			if (payload instanceof String)
				return processAsText((String) payload);
			if (payload instanceof Message)
				return processAsBinary((Message) payload);
			throw new Exception("Unexpected payload type: "+payload.getClass());
		
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
