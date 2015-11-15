package bookshop2.supplier;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.aries.runtime.Initializer;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class TxManagerServiceInitializer implements Initializer, Serializable {

	private AtomicBoolean initialized = new AtomicBoolean(false);
	
	@Override
	public boolean isInitialized() {
		return initialized.get();
	}
	
	@PostConstruct
	public void initialize() throws Exception {
		System.out.println();
	}

}
