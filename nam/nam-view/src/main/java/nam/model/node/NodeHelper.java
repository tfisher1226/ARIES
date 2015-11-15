package nam.model.node;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Node;
import nam.model.Property;
import nam.model.property.PropertyListManager;
import nam.model.property.PropertyListObject;
import nam.model.util.NodeUtil;


@SessionScoped
@Named("nodeHelper")
public class NodeHelper extends AbstractElementHelper<Node> implements Serializable {
	
	@Override
	public boolean isEmpty(Node node) {
		return NodeUtil.isEmpty(node);
	}
	
	@Override
	public String toString(Node node) {
		return NodeUtil.toString(node);
	}
	
	@Override
	public String toString(Collection<Node> nodeList) {
		return NodeUtil.toString(nodeList);
	}
	
	@Override
	public boolean validate(Node node) {
		return NodeUtil.validate(node);
	}
	
	@Override
	public boolean validate(Collection<Node> nodeList) {
		return NodeUtil.validate(nodeList);
	}
	
	public DataModel<PropertyListObject> getProperties(Node node) {
		if (node == null)
			return null;
		return getProperties(node.getProperties());
	}
	
	public DataModel<PropertyListObject> getProperties(Collection<Property> propertiesList) {
		PropertyListManager propertyListManager = BeanContext.getFromSession("propertyListManager");
		DataModel<PropertyListObject> dataModel = propertyListManager.getDataModel(propertiesList);
		return dataModel;
	}
	
}
