package admin.ui.permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Permission;
import admin.util.PermissionUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("permissionSelectManager")
@Scope(ScopeType.SESSION)
public class PermissionSelectManager extends AbstractSelectManager<Permission, PermissionListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "PermissionSelect";
	}
	
	@Override
	public String getTitle() {
		return "Permission Selection";
	}
	
	@Override
	protected Class<Permission> getRecordClass() {
		return Permission.class;
	}
	
	@Override
	public boolean isEmpty(Permission permission) {
		return getPermissionHelper().isEmpty(permission);
	}
	
	@Override
	public String toString(Permission permission) {
		return getPermissionHelper().toString(permission);
	}
	
	protected PermissionHelper getPermissionHelper() {
		return BeanContext.getFromSession("permissionHelper");
	}
	
	protected PermissionListManager getPermissionListManager() {
		return BeanContext.getFromSession("mainPermissionListManager");
	}
	
	@Override
	//@Observer("org.aries.ui.reset")
	public void initialize() {
		initializeContext(); 
		refreshPermissionList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Permission> recordList) {
		PermissionListManager permissionListManager = getPermissionListManager();
		DataModel<PermissionListObject> dataModel = permissionListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	@Observer("org.aries.refreshPermissionList")
	public void refreshPermissionList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<Permission> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Permission> permissionList = BeanContext.getFromConversation(instanceId);
		return permissionList;
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
	public void sortRecords(List<Permission> permissionList) {
		Collections.sort(recordList, new Comparator<Permission>() {
			public int compare(Permission permission1, Permission permission2) {
				String text1 = PermissionUtil.toString(permission1);
				String text2 = PermissionUtil.toString(permission2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
