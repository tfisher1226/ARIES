package admin.roleType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import admin.RoleType;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("roleTypeListManager")
public class RoleTypeListManager extends AbstractDomainListManager<RoleType, RoleTypeListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "roleTypeList";
	}
	
	@Override
	public String getTitle() {
		return "RoleType List";
	}
	
	@Override
	public Object getRecordKey(RoleType roleType) {
		return roleType.name();
	}
	
	@Override
	public String getRecordName(RoleType roleType) {
		return roleType.name();
	}
	
	@Override
	protected Class<RoleType> getRecordClass() {
		return RoleType.class;
	}
	
	@Override
	protected RoleType getRecord(RoleTypeListObject rowObject) {
		return rowObject.getRoleType();
	}
	
	@Override
	public RoleType getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(RoleType roleType) {
		super.setSelectedRecord(roleType);
	}
	
	public boolean isSelected(RoleType roleType) {
		RoleType selection = selectionContext.getSelection("roleType");
		boolean selected = selection != null && selection.equals(roleType);
		return selected;
	}
	
	public boolean isChecked(RoleType roleType) {
		Collection<RoleType> selection = selectionContext.getSelection("roleTypeSelection");
		boolean checked = selection != null && selection.contains(roleType);
		return checked;
	}
	
	@Override
	protected RoleTypeListObject createRowObject(RoleType roleType) {
		RoleTypeListObject listObject = new RoleTypeListObject(roleType);
		listObject.setSelected(isSelected(roleType));
		listObject.setChecked(isChecked(roleType));
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
	protected Collection<RoleType> createRecordList() {
		try {
			Collection<RoleType> roleTypeList = Arrays.asList(RoleType.values());
			return roleTypeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
