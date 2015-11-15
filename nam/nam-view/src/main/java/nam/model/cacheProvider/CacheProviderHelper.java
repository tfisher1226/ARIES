package nam.model.cacheProvider;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.CacheProvider;
import nam.model.util.CacheProviderUtil;


@SessionScoped
@Named("cacheProviderHelper")
public class CacheProviderHelper extends AbstractElementHelper<CacheProvider> implements Serializable {
	
	@Override
	public boolean isEmpty(CacheProvider cacheProvider) {
		return CacheProviderUtil.isEmpty(cacheProvider);
	}
	
	@Override
	public String toString(CacheProvider cacheProvider) {
		return CacheProviderUtil.toString(cacheProvider);
	}
	
	@Override
	public String toString(Collection<CacheProvider> cacheProviderList) {
		return CacheProviderUtil.toString(cacheProviderList);
	}
	
	@Override
	public boolean validate(CacheProvider cacheProvider) {
		return CacheProviderUtil.validate(cacheProvider);
	}
	
	@Override
	public boolean validate(Collection<CacheProvider> cacheProviderList) {
		return CacheProviderUtil.validate(cacheProviderList);
	}
	
}
