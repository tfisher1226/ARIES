package nam.model.namespace;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Namespace;
import nam.model.Properties;
import nam.model.Property;
import nam.model.Type;
import nam.model.property.PropertyListManager;
import nam.model.property.PropertyListObject;
import nam.model.type.TypeListManager;
import nam.model.type.TypeListObject;
import nam.model.util.NamespaceUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("namespaceHelper")
public class NamespaceHelper extends AbstractElementHelper<Namespace> implements Serializable {
	
	@Override
	public boolean isEmpty(Namespace namespace) {
		return NamespaceUtil.isEmpty(namespace);
	}
	
	@Override
	public String toString(Namespace namespace) {
		return NamespaceUtil.toString(namespace);
	}
	
	@Override
	public String toString(Collection<Namespace> namespaceList) {
		return NamespaceUtil.toString(namespaceList);
	}
	
	@Override
	public boolean validate(Namespace namespace) {
		return NamespaceUtil.validate(namespace);
	}
	
	@Override
	public boolean validate(Collection<Namespace> namespaceList) {
		return NamespaceUtil.validate(namespaceList);
	}
	
	public DataModel<NamespaceListObject> getImports(Namespace namespace) {
		if (namespace == null)
			return null;
		return getImports(namespace.getImports());
	}
	
	public DataModel<NamespaceListObject> getImports(Collection<Namespace> importsList) {
		NamespaceListManager namespaceListManager = BeanContext.getFromSession("namespaceListManager");
		DataModel<NamespaceListObject> dataModel = namespaceListManager.getDataModel(importsList);
		return dataModel;
	}
	
	public DataModel<PropertyListObject> getProperties(Namespace namespace) {
		if (namespace == null)
			return null;
		Properties properties = NamespaceUtil.getProperties(namespace);
		return getProperties(properties.getProperties());
	}
	
	public DataModel<PropertyListObject> getProperties(Collection<Property> propertiesList) {
		PropertyListManager propertyListManager = BeanContext.getFromSession("propertyListManager");
		DataModel<PropertyListObject> dataModel = propertyListManager.getDataModel(propertiesList);
		return dataModel;
	}
	
	public DataModel<TypeListObject> getTypes(Namespace namespace) {
		if (namespace == null)
			return null;
		List<Type> types = NamespaceUtil.getTypes(namespace);
		return getTypes(types);
	}
	
	public DataModel<TypeListObject> getTypes(Collection<Type> typesList) {
		TypeListManager typeListManager = BeanContext.getFromSession("typeListManager");
		DataModel<TypeListObject> dataModel = typeListManager.getDataModel(typesList);
		return dataModel;
	}
	
}
