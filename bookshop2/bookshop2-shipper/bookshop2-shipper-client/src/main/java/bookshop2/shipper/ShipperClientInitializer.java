package bookshop2.shipper;

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
public class ShipperClientInitializer extends Bootstrapper implements Initializer {
	
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
		return "bookshop2-shipper-client";
	}
	
	@PostConstruct
	public void execute() throws Exception {
		log.info("Initializing module: "+getModule());
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-shipper-client-checks.xml");
		loadSchema("/model/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
		loadSchema("/model/admin/admin_model.xsd", admin.ObjectFactory.class);
		loadSchema("/model/bookshop/bookshop_information.xsd", bookshop2.ObjectFactory.class);
		super.initializeAsClientModule();
		setInitialized(true);
	}
	
}
