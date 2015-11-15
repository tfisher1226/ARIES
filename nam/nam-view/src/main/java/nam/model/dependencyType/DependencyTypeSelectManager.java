package nam.model.dependencyType;

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

import nam.model.DependencyType;


@SessionScoped
@Named("dependencyTypeSelectManager")
public class DependencyTypeSelectManager extends AbstractSelectManager<DependencyType, DependencyTypeListObject> implements Serializable {
	
	@Inject
	private DependencyTypeHelper dependencyTypeHelper;
	
	
	@Override
	public String getClientId() {
		return "dependencyTypeSelect";
	}
	
	@Override
	public String getTitle() {
		return "DependencyType Selection";
	}
	
	@Override
	protected Class<DependencyType> getRecordClass() {
		return DependencyType.class;
	}
	
	@Override
	public String toString(DependencyType dependencyType) {
		return dependencyType.name();
	}
	
	protected DependencyTypeHelper getDependencyTypeHelper() {
		return BeanContext.getFromSession("dependencyTypeHelper");
	}
	
	protected DependencyTypeListManager getDependencyTypeListManager() {
		return BeanContext.getFromSession("dependencyTypeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshDependencyTypeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<DependencyType> recordList) {
		DependencyTypeListManager dependencyTypeListManager = getDependencyTypeListManager();
		DataModel<DependencyTypeListObject> dataModel = dependencyTypeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshDependencyTypeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<DependencyType> refreshRecords() {
		DependencyType[] values = DependencyType.values();
		List<DependencyType> masterList = new ArrayList<DependencyType>();
		for (DependencyType capability : values) {
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
	public void sortRecords(List<DependencyType> dependencyTypeList) {
		Collections.sort(dependencyTypeList);
	}
	
}
