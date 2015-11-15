package nam.model.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Component;
import nam.model.util.ComponentUtil;


@SessionScoped
@Named("componentSelectManager")
public class ComponentSelectManager extends AbstractSelectManager<Component, ComponentListObject> implements Serializable {
	
	@Inject
	private ComponentDataManager componentDataManager;

	@Inject
	private ComponentHelper componentHelper;
	
	
	@Override
	public String getClientId() {
		return "componentSelect";
	}
	
	@Override
	public String getTitle() {
		return "Component Selection";
	}
	
	@Override
	protected Class<Component> getRecordClass() {
		return Component.class;
	}
	
	@Override
	public boolean isEmpty(Component component) {
		return componentHelper.isEmpty(component);
	}
	
	@Override
	public String toString(Component component) {
		return componentHelper.toString(component);
	}
	
	protected ComponentHelper getComponentHelper() {
		return BeanContext.getFromSession("componentHelper");
	}
	
	protected ComponentListManager getComponentListManager() {
		return BeanContext.getFromSession("componentListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshComponentList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Component> recordList) {
		ComponentListManager componentListManager = getComponentListManager();
		DataModel<ComponentListObject> dataModel = componentListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshComponentList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Component> refreshRecords() {
		try {
			Collection<Component> records = componentDataManager.getComponentList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Component> componentList) {
		Collections.sort(componentList, new Comparator<Component>() {
			public int compare(Component component1, Component component2) {
				String text1 = ComponentUtil.toString(component1);
				String text2 = ComponentUtil.toString(component2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
