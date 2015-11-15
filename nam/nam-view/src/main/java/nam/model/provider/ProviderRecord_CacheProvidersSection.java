package nam.model.provider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Provider;
import nam.model.util.ProviderUtil;


@SessionScoped
@Named("providerCacheProvidersSection")
public class ProviderRecord_CacheProvidersSection extends AbstractWizardPage<Provider> implements Serializable {

	private Provider provider;

	
	public ProviderRecord_CacheProvidersSection() {
		setName("CacheProvider");
		setUrl("cacheProvider");
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
