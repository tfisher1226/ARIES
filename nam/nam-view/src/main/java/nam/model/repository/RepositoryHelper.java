package nam.model.repository;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Property;
import nam.model.Repository;
import nam.model.property.PropertyListManager;
import nam.model.property.PropertyListObject;
import nam.model.util.RepositoryUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("repositoryHelper")
public class RepositoryHelper extends AbstractElementHelper<Repository> implements Serializable {
	
	@Override
	public boolean isEmpty(Repository repository) {
		return RepositoryUtil.isEmpty(repository);
	}
	
	@Override
	public String toString(Repository repository) {
		return RepositoryUtil.toString(repository);
	}
	
	@Override
	public String toString(Collection<Repository> repositoryList) {
		return RepositoryUtil.toString(repositoryList);
	}
	
	@Override
	public boolean validate(Repository repository) {
		return RepositoryUtil.validate(repository);
	}
	
	@Override
	public boolean validate(Collection<Repository> repositoryList) {
		return RepositoryUtil.validate(repositoryList);
	}
	
	public DataModel<Serializable> getMembers(Repository repository) {
		if (repository == null)
			return null;
		return getMembers(repository.getMembers());
	}
	
	public DataModel<Serializable> getMembers(Collection<Serializable> membersList) {
		//SerializableListManager serializableListManager = BeanContext.getFromSession("serializableListManager");
		DataModel<Serializable> dataModel = null; //serializableListManager.getDataModel(membersList);
		return dataModel;
	}
	
	public DataModel<PropertyListObject> getProperties(Repository repository) {
		if (repository == null)
			return null;
		return getProperties(repository.getProperties());
	}
	
	public DataModel<PropertyListObject> getProperties(Collection<Property> propertiesList) {
		PropertyListManager propertyListManager = BeanContext.getFromSession("propertyListManager");
		DataModel<PropertyListObject> dataModel = propertyListManager.getDataModel(propertiesList);
		return dataModel;
	}
	
}
