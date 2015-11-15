package nam.model.dependency;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Dependency;
import nam.model.util.DependencyUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("dependencyListManager")
public class DependencyListManager extends AbstractDomainListManager<Dependency, DependencyListObject> implements Serializable {
	
	@Inject
	private DependencyDataManager dependencyDataManager;
	
	@Inject
	private DependencyEventManager dependencyEventManager;
	
	@Inject
	private DependencyInfoManager dependencyInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "dependencyList";
	}
	
	@Override
	public String getTitle() {
		return "Dependency List";
	}
	
	@Override
	public Object getRecordKey(Dependency dependency) {
		return DependencyUtil.getKey(dependency);
	}
	
	@Override
	public String getRecordName(Dependency dependency) {
		return DependencyUtil.getLabel(dependency);
	}
	
	@Override
	protected Class<Dependency> getRecordClass() {
		return Dependency.class;
	}
	
	@Override
	protected Dependency getRecord(DependencyListObject rowObject) {
		return rowObject.getDependency();
	}
	
	@Override
	public Dependency getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? DependencyUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Dependency dependency) {
		super.setSelectedRecord(dependency);
		fireSelectedEvent(dependency);
	}
	
	protected void fireSelectedEvent(Dependency dependency) {
		dependencyEventManager.fireSelectedEvent(dependency);
	}
	
	public boolean isSelected(Dependency dependency) {
		Dependency selection = selectionContext.getSelection("dependency");
		boolean selected = selection != null && selection.equals(dependency);
		return selected;
	}
	
	@Override
	protected DependencyListObject createRowObject(Dependency dependency) {
		DependencyListObject listObject = new DependencyListObject(dependency);
		listObject.setSelected(isSelected(dependency));
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
	protected Collection<Dependency> createRecordList() {
		try {
			Collection<Dependency> dependencyList = dependencyDataManager.getDependencyList();
			if (dependencyList != null)
				return dependencyList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewDependency() {
		return viewDependency(selectedRecordKey);
	}
	
	public String viewDependency(Object recordKey) {
		Dependency dependency = recordByKeyMap.get(recordKey);
		return viewDependency(dependency);
	}
	
	public String viewDependency(Dependency dependency) {
		String url = dependencyInfoManager.viewDependency(dependency);
		return url;
	}
	
	public String editDependency() {
		return editDependency(selectedRecordKey);
	}
	
	public String editDependency(Object recordKey) {
		Dependency dependency = recordByKeyMap.get(recordKey);
		return editDependency(dependency);
	}
	
	public String editDependency(Dependency dependency) {
		String url = dependencyInfoManager.editDependency(dependency);
		return url;
	}
	
	public void removeDependency() {
		removeDependency(selectedRecordKey);
	}
	
	public void removeDependency(Object recordKey) {
		Dependency dependency = recordByKeyMap.get(recordKey);
		removeDependency(dependency);
	}
	
	public void removeDependency(Dependency dependency) {
		try {
			if (dependencyDataManager.removeDependency(dependency))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelDependency(@Observes @Cancelled Dependency dependency) {
		try {
			//Object key = DependencyUtil.getKey(dependency);
			//recordByKeyMap.put(key, dependency);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("dependency");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateDependency(Collection<Dependency> dependencyList) {
		return DependencyUtil.validate(dependencyList);
	}
	
	public void exportDependencyList(@Observes @Export String tableId) {
		//String tableId = "pageForm:dependencyListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
