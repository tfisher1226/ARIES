package nam.model.persistenceProvider;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.PersistenceProvider;
import nam.model.util.PersistenceProviderUtil;


@SessionScoped
@Named("persistenceProviderHelper")
public class PersistenceProviderHelper extends AbstractElementHelper<PersistenceProvider> implements Serializable {
	
	@Override
	public boolean isEmpty(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.isEmpty(persistenceProvider);
	}
	
	@Override
	public String toString(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.toString(persistenceProvider);
	}
	
	@Override
	public String toString(Collection<PersistenceProvider> persistenceProviderList) {
		return PersistenceProviderUtil.toString(persistenceProviderList);
	}
	
	@Override
	public boolean validate(PersistenceProvider persistenceProvider) {
		return PersistenceProviderUtil.validate(persistenceProvider);
	}
	
	@Override
	public boolean validate(Collection<PersistenceProvider> persistenceProviderList) {
		return PersistenceProviderUtil.validate(persistenceProviderList);
	}
	
}
