package nam.model.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Domain;
import nam.model.util.DomainUtil;


@SessionScoped
@Named("domainHelper")
public class DomainHelper extends AbstractElementHelper<Domain> implements Serializable {
	
	@Override
	public boolean isEmpty(Domain domain) {
		return DomainUtil.isEmpty(domain);
	}
	
	@Override
	public String toString(Domain domain) {
		return DomainUtil.toString(domain);
	}
	
	@Override
	public String toString(Collection<Domain> domainList) {
		return DomainUtil.toString(domainList);
	}
	
	@Override
	public boolean validate(Domain domain) {
		return DomainUtil.validate(domain);
	}
	
	@Override
	public boolean validate(Collection<Domain> domainList) {
		return DomainUtil.validate(domainList);
	}
	
	public DataModel<Serializable> getMembers(Domain domain) {
		if (domain == null)
			return null;
		return getMembers(domain.getMembers());
	}
	
	public DataModel<Serializable> getMembers(Collection<Serializable> membersList) {
		//SerializableListManager serializableListManager = BeanContext.getFromSession("serializableListManager");
		//DataModel<Serializable> dataModel = serializableListManager.getDataModel(informationsAndPersistencesAndServicesList);
		//return dataModel;
		return null;
	}
	
}
