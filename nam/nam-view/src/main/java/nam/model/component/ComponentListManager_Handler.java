package nam.model.component;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Component;
import nam.model.util.ComponentUtil;
import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.manager.ExportManager;


@SessionScoped
@Named("handlerComponentListManager")
public class ComponentListManager_Handler extends AbstractDomainListManager<Component, ComponentListObject> implements Serializable {
	
	@Inject
	private ComponentDataManager componentDataManager;
	
	@Inject
	private ComponentEventManager componentEventManager;
	
	@Inject
	private ComponentInfoManager componentInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "componentList";
	}
	
	@Override
	public String getTitle() {
		return "Component List";
	}
	
	@Override
	public Object getRecordKey(Component component) {
		return ComponentUtil.getKey(component);
	}
	
	@Override
	public String getRecordName(Component component) {
		return ComponentUtil.getLabel(component);
	}
	
	@Override
	protected Class<Component> getRecordClass() {
		return Component.class;
	}
	
	@Override
	protected Component getRecord(ComponentListObject rowObject) {
		return rowObject.getComponent();
	}
	
	@Override
	public Component getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ComponentUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Component component) {
		super.setSelectedRecord(component);
		fireSelectedEvent(component);
	}
	
	protected void fireSelectedEvent(Component component) {
		componentEventManager.fireSelectedEvent(component);
	}
	
	public boolean isSelected(Component component) {
		Component selection = selectionContext.getSelection("component");
		boolean selected = selection != null && selection.equals(component);
		return selected;
	}
	
	@Override
	protected ComponentListObject createRowObject(Component component) {
		ComponentListObject listObject = new ComponentListObject(component);
		listObject.setSelected(isSelected(component));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Component> createRecordList() {
		try {
			Collection<Component> componentList = componentDataManager.getComponentList("handler");
			if (componentList != null)
				return componentList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewComponent() {
		return viewComponent(selectedRecordKey);
	}
	
	public String viewComponent(Object recordKey) {
		Component component = recordByKeyMap.get(recordKey);
		return viewComponent(component);
	}
	
	public String viewComponent(Component component) {
		String url = componentInfoManager.viewComponent(component);
		return url;
	}
	
	public String editComponent() {
		return editComponent(selectedRecordKey);
	}
	
	public String editComponent(Object recordKey) {
		Component component = recordByKeyMap.get(recordKey);
		return editComponent(component);
	}
	
	public String editComponent(Component component) {
		String url = componentInfoManager.editComponent(component);
		return url;
	}
	
	public void removeComponent() {
		removeComponent(selectedRecordKey);
	}
	
	public void removeComponent(Object recordKey) {
		Component component = recordByKeyMap.get(recordKey);
		removeComponent(component);
	}
	
	public void removeComponent(Component component) {
		try {
			if (componentDataManager.removeComponent(component))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelComponent(@Observes @Cancelled Component component) {
		try {
			//Object key = ComponentUtil.getKey(component);
			//recordByKeyMap.put(key, component);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("component");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateComponent(Collection<Component> componentList) {
		return ComponentUtil.validate(componentList);
	}
	
	public void exportComponentList(@Observes @Export String tableId) {
		//String tableId = "pageForm:componentListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
