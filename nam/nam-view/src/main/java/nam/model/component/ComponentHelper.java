package nam.model.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;
import org.aries.ui.model.ListTableModel;

import nam.model.Component;
import nam.model.Operation;
import nam.model.operation.OperationListManager;
import nam.model.operation.OperationListObject;
import nam.model.util.ComponentUtil;


@SessionScoped
@Named("componentHelper")
public class ComponentHelper extends AbstractElementHelper<Component> implements Serializable {
	
	@Override
	public boolean isEmpty(Component component) {
		return ComponentUtil.isEmpty(component);
	}
	
	@Override
	public String toString(Component component) {
		return ComponentUtil.toString(component);
	}
	
	@Override
	public String toString(Collection<Component> componentList) {
		return ComponentUtil.toString(componentList);
	}
	
	@Override
	public boolean validate(Component component) {
		return ComponentUtil.validate(component);
	}
	
	@Override
	public boolean validate(Collection<Component> componentList) {
		return ComponentUtil.validate(componentList);
	}
	
	public DataModel<String> getAnnotations(Component component) {
		if (component == null)
			return null;
		return getAnnotations(component.getAnnotations());
	}
	
	public DataModel<String> getAnnotations(Collection<String> annotationsList) {
		List<String> values = new ArrayList<String>(annotationsList);
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
	public DataModel<ComponentListObject> getComponents(Component component) {
		if (component == null)
			return null;
		return getComponents(component.getComponents());
	}
	
	public DataModel<ComponentListObject> getComponents(Collection<Component> componentsList) {
		ComponentListManager componentListManager = BeanContext.getFromSession("componentListManager");
		DataModel<ComponentListObject> dataModel = componentListManager.getDataModel(componentsList);
		return dataModel;
	}
	
	public DataModel<String> getFields(Component component) {
		if (component == null)
			return null;
		return getFields(component.getFields());
	}
	
	public DataModel<String> getFields(Collection<String> fieldsList) {
		List<String> values = new ArrayList<String>(fieldsList);
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
	public DataModel<OperationListObject> getOperations(Component component) {
		if (component == null)
			return null;
		return getOperations(component.getOperations());
	}
	
	public DataModel<OperationListObject> getOperations(Collection<Operation> operationsList) {
		OperationListManager operationListManager = BeanContext.getFromSession("operationListManager");
		DataModel<OperationListObject> dataModel = operationListManager.getDataModel(operationsList);
		return dataModel;
	}
	
}
