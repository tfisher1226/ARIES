package admin.ui.permission;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractTabListManager;

import admin.Permission;
import admin.util.PermissionUtil;


@SessionScoped
@Named("permissionListManager")
public class PermissionListManager extends AbstractTabListManager<Permission, PermissionListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "PermissionList";
	}

	@Override
	public String getTitle() {
		return "Permission List";
	}

	@Override
	public Object getRecordId(Permission permission) {
		return permission.getId();
	}

	@Override
	public String getRecordName(Permission permission) {
		return PermissionUtil.toString(permission);
	}

	@Override
	protected Class<Permission> getRecordClass() {
		return Permission.class;
	}

	@Override
	protected Permission getRecord(PermissionListObject rowObject) {
		return rowObject.getPermission();
	}
	
	@Override
	protected PermissionListObject createRowObject(Permission permission) {
		return new PermissionListObject(permission);
	}

	protected PermissionHelper getPermissionHelper() {
		return BeanContext.getFromSession("permissionHelper");
	}
	
	protected PermissionInfoManager getPermissionInfoManager() {
		return BeanContext.getFromSession("permissionInfoManager");
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	public void editPermission() {
		editPermission(selectedRecordId);
	}
	
	public void editPermission(String recordId) {
		editPermission(Long.parseLong(recordId));
	}
	
	public void editPermission(Long recordId) {
		Permission permission = recordByIdMap.get(recordId);
		editPermission(permission);
	}
	
	public void editPermission(Permission permission) {
		PermissionInfoManager permissionInfoManager = BeanContext.getFromSession("permissionInfoManager");
		permissionInfoManager.editPermission(permission);
	}
	
	//SEAM @Observer("org.aries.removePermission")
	public void removePermission() {
		removePermission(selectedRecordId);
	}
	
	public void removePermission(String recordId) {
		removePermission(Long.parseLong(recordId));
	}
	
	public void removePermission(Long recordId) {
		Permission permission = recordByIdMap.get(recordId);
		removePermission(permission);
	}
	
	public void removePermission(Permission permission) {
			try {
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.cancelPermission")
	public void cancelPermission() {
		if (selectedRecord == null)
			return;
		try {
			BeanContext.removeFromSession("permission");
			Long id = selectedRecord.getId();
			initialize(recordByIdMap.values());
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validatePermission(Collection<Permission> permissionList) {
		return PermissionUtil.validate(permissionList);
	}
	
//	//SEAM @Observer("org.aries.exportPermissionList")
//	public void exportPermissionList() {
//		String tableId = "pageForm:permissionListTable";
//		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
//		exportManager.exportToXLS(tableId);
//	}
	
}
