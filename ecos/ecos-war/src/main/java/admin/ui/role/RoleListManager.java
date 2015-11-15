package admin.ui.role;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Role;
import admin.client.roleService.RoleService;
import admin.util.RoleUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("roleListManager")
@Scope(ScopeType.SESSION)
@org.jboss.seam.annotations.Role(name = "mainRoleListManager", scope = ScopeType.SESSION)
public class RoleListManager extends AbstractTabListManager<Role, RoleListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "RoleList";
	}
	
	@Override
	public String getTitle() {
		return "Role List";
	}

	@Override
	public Object getRecordId(Role role) {
		return role.getId();
	}

	@Override
	public String getRecordName(Role role) {
		return RoleUtil.toString(role);
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
	protected RoleListObject createRowObject(Role role) {
		return new RoleListObject(role);
	}
	
	protected RoleHelper getRoleHelper() {
		return BeanContext.getFromSession("roleHelper");
	}
	
	protected RoleService getRoleService() {
		return BeanContext.getFromSession(RoleService.ID);
	}
	
	protected RoleInfoManager getRoleInfoManager() {
		return BeanContext.getFromSession("roleInfoManager");
	}
	
	@Override
	//@Observer("org.aries.ui.reset")
	public void reset() {
		refresh();
	}
	
	@Override	
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	@Observer("org.aries.refreshRoleList")
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected List<Role> createRecordList() {
		try {
			List<Role> roleList = getRoleService().getRoleList();
			return roleList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void editRole() {
		editRole(selectedRecordId);
	}
	
	public void editRole(String recordId) {
		editRole(Long.parseLong(recordId));
	}
	
	public void editRole(Long recordId) {
		Role role = recordByIdMap.get(recordId);
		editRole(role);
	}
	
	public void editRole(Role role) {
		RoleInfoManager roleInfoManager = BeanContext.getFromSession("roleInfoManager");
		roleInfoManager.editRole(role);
	}
	
	@Observer("org.aries.removeRole")
	public void removeRole() {
		removeRole(selectedRecordId);
	}
	
	public void removeRole(String recordId) {
		removeRole(Long.parseLong(recordId));
	}
	
	public void removeRole(Long recordId) {
		Role role = recordByIdMap.get(recordId);
		removeRole(role);
	}
	
	public void removeRole(Role role) {
		try {
			getRoleService().deleteRole(selectedRecord);
			clearSelection();
			refresh();

		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Observer("org.aries.cancelRole")
	public void cancelRole() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("role");
			Long id = selectedRecord.getId();
			Role role = getRoleService().getRoleById(id);
			recordByIdMap.put(id, role);
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateRole(Collection<Role> roleList) {
		return RoleUtil.validate(roleList);
	}
	
//	@Observer("org.aries.exportRoleList")
//	public void exportRoleList() {
//		String tableId = "pageForm:roleListTable";
//		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
//		exportManager.exportToXLS(tableId);
//	}

}
