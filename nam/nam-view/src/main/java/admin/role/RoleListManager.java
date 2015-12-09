package admin.role;

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

import admin.Role;
import admin.util.RoleUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("roleListManager")
public class RoleListManager extends AbstractDomainListManager<Role, RoleListObject> implements Serializable {
	
	@Inject
	private RoleDataManager roleDataManager;
	
	@Inject
	private RoleEventManager roleEventManager;
	
	@Inject
	private RoleInfoManager roleInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "roleList";
	}
	
	@Override
	public String getTitle() {
		return "Role List";
	}

	public Object getRecordId(Role role) {
		return role.getId();
	}
	
	@Override
	public Object getRecordKey(Role role) {
		return RoleUtil.getKey(role);
	}
	
	@Override
	public String getRecordName(Role role) {
		return RoleUtil.getLabel(role);
	}
	
	@Override
	protected Class<Role> getRecordClass() {
		return Role.class;
	}
	
	@Override
	protected Role getRecord(RoleListObject rowObject) {
		return rowObject.getRole();
	}
	
	@Override
	public Role getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? RoleUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Role role) {
		super.setSelectedRecord(role);
		fireSelectedEvent(role);
	}
	
	protected void fireSelectedEvent(Role role) {
		roleEventManager.fireSelectedEvent(role);
	}
	
	public boolean isSelected(Role role) {
		Role selection = selectionContext.getSelection("role");
		boolean selected = selection != null && selection.equals(role);
		return selected;
	}
	
	public boolean isChecked(Role role) {
		Collection<Role> selection = selectionContext.getSelection("roleSelection");
		boolean checked = selection != null && selection.contains(role);
		return checked;
	}
	
	@Override
	protected RoleListObject createRowObject(Role role) {
		RoleListObject listObject = new RoleListObject(role);
		listObject.setSelected(isSelected(role));
		listObject.setChecked(isChecked(role));
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
	protected Collection<Role> createRecordList() {
		try {
			Collection<Role> roleList = roleDataManager.getRoleList();
			if (roleList != null)
			return roleList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewRole() {
		return viewRole(selectedRecordKey);
	}
	
	public String viewRole(Object recordKey) {
		Role role = recordByKeyMap.get(recordKey);
		return viewRole(role);
	}
	
	public String viewRole(Role role) {
		String url = roleInfoManager.viewRole(role);
		return url;
	}
	
	public String editRole() {
		return editRole(selectedRecordKey);
	}
	
	public String editRole(Object recordKey) {
		Role role = recordByKeyMap.get(recordKey);
		return editRole(role);
	}
	
	public String editRole(Role role) {
		String url = roleInfoManager.editRole(role);
		return url;
	}
	
	public void removeRole() {
		removeRole(selectedRecordKey);
	}
	
	public void removeRole(Object recordKey) {
		Role role = recordByKeyMap.get(recordKey);
		removeRole(role);
	}
	
	public void removeRole(Role role) {
		try {
			if (roleDataManager.removeRole(role))
			clearSelection();
			refresh();

		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelRole(@Observes @Cancelled Role role) {
		try {
			//Object key = RoleUtil.getKey(role);
			//recordByKeyMap.put(key, role);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("role");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateRole(Collection<Role> roleList) {
		return RoleUtil.validate(roleList);
	}
	
	public void exportRoleList(@Observes @Export String tableId) {
		//String tableId = "pageForm:roleListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}

}
