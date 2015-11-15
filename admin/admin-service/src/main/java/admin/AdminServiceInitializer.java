package admin;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.aries.runtime.Initializer;
import org.aries.tx.module.Bootstrapper;
import org.aries.validate.util.CheckpointManager;


@Startup
@Singleton
@LocalBean
public class AdminServiceInitializer extends Bootstrapper implements Initializer {
	
	@Override
	public String getApplication() {
		return "admin";
	}
	
	@Override
	public String getDomain() {
		return "admin";
	}
	
	@Override
	public String getModule() {
		return "admin-service";
	}
	
	@PostConstruct
	public void execute() throws Exception {
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("admin-service-checks.xml");
		loadSchema("/model/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		loadSchema("/model/admin/admin_model.xsd", admin.ObjectFactory.class);
		super.initializeAsServiceModule();
		setInitialized(true);
	}
	
}
