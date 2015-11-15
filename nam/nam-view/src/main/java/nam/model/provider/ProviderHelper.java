package nam.model.provider;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Provider;
import nam.model.util.ProviderUtil;


@SessionScoped
@Named("providerHelper")
public class ProviderHelper extends AbstractElementHelper<Provider> implements Serializable {
	
	@Override
	public boolean isEmpty(Provider provider) {
		return ProviderUtil.isEmpty(provider);
	}
	
	@Override
	public String toString(Provider provider) {
		return ProviderUtil.toString(provider);
	}
	
	@Override
	public String toString(Collection<Provider> providerList) {
		return ProviderUtil.toString(providerList);
	}
	
	@Override
	public boolean validate(Provider provider) {
		return ProviderUtil.validate(provider);
	}
	
	@Override
	public boolean validate(Collection<Provider> providerList) {
		return ProviderUtil.validate(providerList);
	}
	
}
