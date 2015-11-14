package redhat.jee_migration_example;

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
public class EventLoggerClientInitializer extends Bootstrapper implements Initializer {
	
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
		return "jee.migration.example.client";
	}
	
	@PostConstruct
	public void execute() throws Exception {
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("jee-migration-example-client-checks.xml");
		loadSchema("/model/redhat/jee-migration-example/elements.xsd", redhat.jee_migration_example.ObjectFactory.class);
		super.initializeAsClientModule();
		setInitialized(true);
	}
	
}
