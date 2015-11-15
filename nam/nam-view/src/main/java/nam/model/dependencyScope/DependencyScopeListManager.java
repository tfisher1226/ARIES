package nam.model.dependencyScope;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.DependencyScope;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("dependencyScopeListManager")
public class DependencyScopeListManager extends AbstractDomainListManager<DependencyScope, DependencyScopeListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "dependencyScopeList";
	}
	
	@Override
	public String getTitle() {
		return "DependencyScope List";
	}
	
	@Override
	public Object getRecordKey(DependencyScope dependencyScope) {
		return dependencyScope.name();
	}
	
	@Override
	public String getRecordName(DependencyScope dependencyScope) {
		return dependencyScope.name();
	}
	
	@Override
	protected Class<DependencyScope> getRecordClass() {
		return DependencyScope.class;
	}
	
	@Override
	protected DependencyScope getRecord(DependencyScopeListObject rowObject) {
		return rowObject.getDependencyScope();
	}
	
	@Override
	public DependencyScope getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(DependencyScope dependencyScope) {
		super.setSelectedRecord(dependencyScope);
	}
	
	public boolean isSelected(DependencyScope dependencyScope) {
		DependencyScope selection = selectionContext.getSelection("dependencyScope");
		boolean selected = selection != null && selection.equals(dependencyScope);
		return selected;
	}
	
	@Override
	protected DependencyScopeListObject createRowObject(DependencyScope dependencyScope) {
		DependencyScopeListObject listObject = new DependencyScopeListObject(dependencyScope);
		listObject.setSelected(isSelected(dependencyScope));
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
	protected Collection<DependencyScope> createRecordList() {
		try {
			Collection<DependencyScope> dependencyScopeList = Arrays.asList(DependencyScope.values());
			return dependencyScopeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
