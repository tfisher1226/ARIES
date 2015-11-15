package bookshop2.buyer;

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
public class BuyerServiceInitializer extends Bootstrapper implements Initializer {

	@Override
	public String getApplication() {
		return "buyer";
	}

	@Override
	public String getDomain() {
		return "bookshop2.buyer";
	}

	@Override
	public String getModule() {
		return "bookshop2-buyer-service";
	}

	@PostConstruct
	public void execute() throws Exception {
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.initializeAsServiceModule();
		setInitialized(true);
	}
	
}
