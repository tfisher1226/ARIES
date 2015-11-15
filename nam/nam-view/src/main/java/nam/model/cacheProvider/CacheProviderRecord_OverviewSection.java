package nam.model.cacheProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;


@SessionScoped
@Named("cacheProviderOverviewSection")
public class CacheProviderRecord_OverviewSection extends AbstractWizardPage<CacheProvider> implements Serializable {
	
	private CacheProvider cacheProvider;
	
	
	public CacheProviderRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
