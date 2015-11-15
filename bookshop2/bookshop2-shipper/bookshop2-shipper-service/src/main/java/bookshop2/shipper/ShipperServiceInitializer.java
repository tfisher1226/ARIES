package bookshop2.shipper;

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
public class ShipperServiceInitializer extends Bootstrapper implements Initializer {
	
	@Override
	public String getApplication() {
		return "shipper";
	}

	@Override
	public String getDomain() {
		return "bookshop2.shipper";
	}

	@Override
	public String getModule() {
		return "bookshop2-shipper-service";
	}
	
	@PostConstruct
	public void execute() throws Exception {
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-shipper-service-checks.xml");
		loadSchema("/model/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		loadSchema("/model/admin/admin_model.xsd", admin.ObjectFactory.class);
		loadSchema("/model/bookshop/bookshop_information.xsd", bookshop2.ObjectFactory.class);
		super.initializeAsServiceModule();
		setInitialized(true);
	}
	
}
