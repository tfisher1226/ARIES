package nam.model.dependencyType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.DependencyType;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("dependencyTypeListManager")
public class DependencyTypeListManager extends AbstractDomainListManager<DependencyType, DependencyTypeListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "dependencyTypeList";
	}
	
	@Override
	public String getTitle() {
		return "DependencyType List";
	}
	
	@Override
	public Object getRecordKey(DependencyType dependencyType) {
		return dependencyType.name();
	}
	
	@Override
	public String getRecordName(DependencyType dependencyType) {
		return dependencyType.name();
	}
	
	@Override
	protected Class<DependencyType> getRecordClass() {
		return DependencyType.class;
	}
	
	@Override
	protected DependencyType getRecord(DependencyTypeListObject rowObject) {
		return rowObject.getDependencyType();
	}
	
	@Override
	public DependencyType getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(DependencyType dependencyType) {
		super.setSelectedRecord(dependencyType);
	}
	
	public boolean isSelected(DependencyType dependencyType) {
		DependencyType selection = selectionContext.getSelection("dependencyType");
		boolean selected = selection != null && selection.equals(dependencyType);
		return selected;
	}
	
	@Override
	protected DependencyTypeListObject createRowObject(DependencyType dependencyType) {
		DependencyTypeListObject listObject = new DependencyTypeListObject(dependencyType);
		listObject.setSelected(isSelected(dependencyType));
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
	protected Collection<DependencyType> createRecordList() {
		try {
			Collection<DependencyType> dependencyTypeList = Arrays.asList(DependencyType.values());
			return dependencyTypeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
