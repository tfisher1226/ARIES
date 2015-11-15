package nam.model.persistenceProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;


@SessionScoped
@Named("persistenceProviderIdentificationSection")
public class PersistenceProviderRecord_IdentificationSection extends AbstractWizardPage<PersistenceProvider> implements Serializable {
	
	private PersistenceProvider persistenceProvider;
	
	
	public PersistenceProviderRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public PersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}
	
	public void setPersistenceProvider(PersistenceProvider persistenceProvider) {
		this.persistenceProvider = persistenceProvider;
	}
	
	@Override
	public void initialize(PersistenceProvider persistenceProvider) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setPersistenceProvider(persistenceProvider);
	}
	
	@Override
	public void validate() {
		if (persistenceProvider == null) {
			validator.missing("PersistenceProvider");
		} else {
		}
	}
	
}
