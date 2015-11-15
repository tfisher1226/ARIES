package nam.model.cache;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Cache;
import nam.model.util.CacheUtil;


@SessionScoped
@Named("cacheOverviewSection")
public class CacheRecord_OverviewSection extends AbstractWizardPage<Cache> implements Serializable {
	
	private Cache cache;
	
	
	public CacheRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Cache getCache() {
		return cache;
	}
	
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	@Override
	public void initialize(Cache cache) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setCache(cache);
	}
	
	@Override
	public void validate() {
		if (cache == null) {
			validator.missing("Cache");
		} else {
		}
	}
	
}
