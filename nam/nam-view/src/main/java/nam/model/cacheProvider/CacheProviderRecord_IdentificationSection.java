package nam.model.cacheProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;


@SessionScoped
@Named("cacheProviderIdentificationSection")
public class CacheProviderRecord_IdentificationSection extends AbstractWizardPage<CacheProvider> implements Serializable {
	
	private CacheProvider cacheProvider;
	
	
	public CacheProviderRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}
	
	public void setCacheProvider(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}
	
	@Override
	public void initialize(CacheProvider cacheProvider) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setCacheProvider(cacheProvider);
	}
	
	@Override
	public void validate() {
		if (cacheProvider == null) {
			validator.missing("CacheProvider");
		} else {
		}
	}
	
}
