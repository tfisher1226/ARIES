package nam.model.element;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Element;
import nam.model.util.ElementUtil;

import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("elementHelper")
public class ElementHelper extends AbstractElementHelper<Element> implements Serializable {

	@Override
	public boolean isEmpty(Element element) {
		return ElementUtil.isEmpty(element);
	}
	
	@Override
	public String toString(Element element) {
		return ElementUtil.toString(element);
	}
	
	@Override
	public String toString(Collection<Element> elementList) {
		return ElementUtil.toString(elementList);
	}
	
	@Override
	public boolean validate(Element element) {
		return ElementUtil.validate(element);
	}

	@Override
	public boolean validate(Collection<Element> elementList) {
		return ElementUtil.validate(elementList);
	}
	
//	public DataModel<Serializable> getAttributesAndReferencesAndGroups(Element element) {
//		if (element == null)
//			return null;
//		return getAttributesAndReferencesAndGroups(element.getAttributesAndReferencesAndGroups());
//	}
//
//	public DataModel<Serializable> getAttributesAndReferencesAndGroups(Collection<Serializable> attributesAndReferencesAndGroupsList) {
//		//SerializableListManager serializableListManager = BeanContext.getFromSession("serializableListManager");
//		DataModel<Serializable> dataModel = serializableListManager.getDataModel(attributesAndReferencesAndGroupsList);
//		return dataModel;
//	}
	
//	public DataModel<RoleListObject> getRoles(Element element) {
//		if (element == null)
//			return null;
//		return getRoles(element.getRoles());
//	}
	
//	public DataModel<RoleListObject> getRoles(Collection<Role> rolesList) {
//		RoleListManager roleListManager = BeanContext.getFromSession("roleListManager");
//		DataModel<RoleListObject> dataModel = roleListManager.getDataModel(rolesList);
//		return dataModel;
//	}
	
	
}
