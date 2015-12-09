package admin.permission;

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

import admin.Permission;
import admin.util.PermissionUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("permissionListManager")
public class PermissionListManager extends AbstractDomainListManager<Permission, PermissionListObject> implements Serializable {
	
	@Inject
	private PermissionDataManager permissionDataManager;
	
	@Inject
	private PermissionEventManager permissionEventManager;
	
	@Inject
	private PermissionInfoManager permissionInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "permissionList";
	}

	@Override
	public String getTitle() {
		return "Permission List";
	}

	public Object getRecordId(Permission permission) {
		return permission.getId();
	}
	
	@Override
	public Object getRecordKey(Permission permission) {
		return PermissionUtil.getKey(permission);
	}
	
	@Override
	public String getRecordName(Permission permission) {
		return PermissionUtil.getLabel(permission);
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
	public Permission getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? PermissionUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Permission permission) {
		super.setSelectedRecord(permission);
		fireSelectedEvent(permission);
	}

	protected void fireSelectedEvent(Permission permission) {
		permissionEventManager.fireSelectedEvent(permission);
	}
	
	public boolean isSelected(Permission permission) {
		Permission selection = selectionContext.getSelection("permission");
		boolean selected = selection != null && selection.equals(permission);
		return selected;
	}
	
	public boolean isChecked(Permission permission) {
		Collection<Permission> selection = selectionContext.getSelection("permissionSelection");
		boolean checked = selection != null && selection.contains(permission);
		return checked;
	}
	
	@Override
	protected PermissionListObject createRowObject(Permission permission) {
		PermissionListObject listObject = new PermissionListObject(permission);
		listObject.setSelected(isSelected(permission));
		listObject.setChecked(isChecked(permission));
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
	protected Collection<Permission> createRecordList() {
		try {
			Collection<Permission> permissionList = permissionDataManager.getPermissionList();
			if (permissionList != null)
				return permissionList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewPermission() {
		return viewPermission(selectedRecordKey);
	}
	
	public String viewPermission(Object recordKey) {
		Permission permission = recordByKeyMap.get(recordKey);
		return viewPermission(permission);
	}
	
	public String viewPermission(Permission permission) {
		String url = permissionInfoManager.viewPermission(permission);
		return url;
	}
	
	public String editPermission() {
		return editPermission(selectedRecordKey);
	}
	
	public String editPermission(Object recordKey) {
		Permission permission = recordByKeyMap.get(recordKey);
		return editPermission(permission);
	}
	
	public String editPermission(Permission permission) {
		String url = permissionInfoManager.editPermission(permission);
		return url;
	}
	
	public void removePermission() {
		removePermission(selectedRecordKey);
	}
	
	public void removePermission(Object recordKey) {
		Permission permission = recordByKeyMap.get(recordKey);
		removePermission(permission);
	}
	
	public void removePermission(Permission permission) {
			try {
			if (permissionDataManager.removePermission(permission))
				clearSelection();
				refresh();
			
			} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelPermission(@Observes @Cancelled Permission permission) {
		try {
			//Object key = PermissionUtil.getKey(permission);
			//recordByKeyMap.put(key, permission);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("permission");
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validatePermission(Collection<Permission> permissionList) {
		return PermissionUtil.validate(permissionList);
	}
	
	public void exportPermissionList(@Observes @Export String tableId) {
		//String tableId = "pageForm:permissionListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
