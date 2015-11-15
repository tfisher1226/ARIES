package nam;

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
public class NamClientInitializer extends Bootstrapper implements Initializer {
	
	@Override
	public String getApplication() {
		return "nam";
	}
	
	@Override
	public String getDomain() {
		return "aries.org";
	}

	@Override
	public String getModule() {
		return "nam-client";
	}

	@PostConstruct
	public void execute() throws Exception {
		log.info("Initializing module: "+getModule());
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("nam-client-checks.xml");
		loadSchema("/model/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		loadSchema("/model/nam/nam-1.0.xsd", nam.model.ObjectFactory.class);
		super.initializeAsClientModule();
		setInitialized(true);
	}
	
}
