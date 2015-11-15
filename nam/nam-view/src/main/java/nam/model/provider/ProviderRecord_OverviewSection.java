package nam.model.provider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Provider;
import nam.model.util.ProviderUtil;


@SessionScoped
@Named("providerOverviewSection")
public class ProviderRecord_OverviewSection extends AbstractWizardPage<Provider> implements Serializable {

	private Provider provider;

	
	public ProviderRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@Override
	public void initialize(Provider provider) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setProvider(provider);
	}
	
	@Override
	public void validate() {
		if (provider == null) {
			validator.missing("Provider");
		} else {
		}
	}
	
}
