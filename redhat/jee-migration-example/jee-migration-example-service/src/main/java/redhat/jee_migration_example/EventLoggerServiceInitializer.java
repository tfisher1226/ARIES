package redhat.jee_migration_example;

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
public class EventLoggerServiceInitializer extends Bootstrapper implements Initializer {
	
	@Override
	public String getApplication() {
		return "eventLogger";
	}
	
	@Override
	public String getDomain() {
		return "redhat";
	}
	
	@Override
	public String getModule() {
		return "jee-migration-example-service";
	}
	
	@PostConstruct
	public void execute() throws Exception {
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-service-checks.xml");
		loadSchema("/model/redhat/jee-migration-example/elements.xsd", redhat.jee_migration_example.ObjectFactory.class);
		super.initializeAsServiceModule();
		setInitialized(true);
	}
	
}
