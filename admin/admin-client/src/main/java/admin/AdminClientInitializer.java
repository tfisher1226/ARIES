package admin;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.aries.runtime.Initializer;
import org.aries.tx.module.Bootstrapper;
import org.aries.validate.util.CheckpointManager;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
public class AdminClientInitializer extends Bootstrapper implements Initializer {
	
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
		return "admin-client";
	}

	@PostConstruct
	public void execute() throws Exception {
		log.info("Initializing module: "+getModule());
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("admin-client-checks.xml");
		loadSchema("/model/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		loadSchema("/model/admin/admin_model.xsd", admin.ObjectFactory.class);
		super.initializeAsClientModule();
		setInitialized(true);
	}
	
}
