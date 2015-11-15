package nam.model.dependencyScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.DependencyScope;


@SessionScoped
@Named("dependencyScopeSelectManager")
public class DependencyScopeSelectManager extends AbstractSelectManager<DependencyScope, DependencyScopeListObject> implements Serializable {
	
	@Inject
	private DependencyScopeHelper dependencyScopeHelper;
	
	
	@Override
	public String getClientId() {
		return "dependencyScopeSelect";
	}
	
	@Override
	public String getTitle() {
		return "DependencyScope Selection";
	}
	
	@Override
	protected Class<DependencyScope> getRecordClass() {
		return DependencyScope.class;
	}
	
	@Override
	public String toString(DependencyScope dependencyScope) {
		return dependencyScope.name();
	}
	
	protected DependencyScopeHelper getDependencyScopeHelper() {
		return BeanContext.getFromSession("dependencyScopeHelper");
	}
	
	protected DependencyScopeListManager getDependencyScopeListManager() {
		return BeanContext.getFromSession("dependencyScopeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshDependencyScopeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<DependencyScope> recordList) {
		DependencyScopeListManager dependencyScopeListManager = getDependencyScopeListManager();
		DataModel<DependencyScopeListObject> dataModel = dependencyScopeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshDependencyScopeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<DependencyScope> refreshRecords() {
		DependencyScope[] values = DependencyScope.values();
		List<DependencyScope> masterList = new ArrayList<DependencyScope>();
		for (DependencyScope capability : values) {
			masterList.add(capability);
		}
		return masterList;
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
	public void sortRecords(List<DependencyScope> dependencyScopeList) {
		Collections.sort(dependencyScopeList);
	}
	
}
