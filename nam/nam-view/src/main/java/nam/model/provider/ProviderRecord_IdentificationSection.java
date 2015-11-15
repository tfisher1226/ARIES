package nam.model.provider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Provider;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("providerIdentificationSection")
public class ProviderRecord_IdentificationSection extends AbstractWizardPage<Provider> implements Serializable {
	
	private Provider provider;

	
	public ProviderRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setProvider(provider);
	}

	@Override
	public void validate() {
		if (provider == null) {
			validator.missing("Provider");
		} else {
			if (StringUtils.isEmpty(provider.getName()))
				validator.missing("Name");
		}
	}

}
