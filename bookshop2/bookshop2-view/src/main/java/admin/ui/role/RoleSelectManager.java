package admin.ui.role;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import admin.Role;
import admin.client.roleService.RoleService;
import admin.util.RoleUtil;


@SessionScoped
@Named("roleSelectManager")
public class RoleSelectManager extends AbstractSelectManager<Role, RoleListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "RoleSelect";
	}
	
	@Override
	public String getTitle() {
		return "Role Selection";
	}
	
	@Override
	protected Class<Role> getRecordClass() {
		return Role.class;
	}
	
	@Override
	public boolean isEmpty(Role role) {
		return getRoleHelper().isEmpty(role);
	}
	
	@Override
	public String toString(Role role) {
		return getRoleHelper().toString(role);
	}
	
	protected RoleHelper getRoleHelper() {
		return BeanContext.getFromSession("roleHelper");
	}
	
	protected RoleService getRoleService() {
		return BeanContext.getFromSession(RoleService.ID);
	}
	
	protected RoleListManager getRoleListManager() {
		return BeanContext.getFromSession("roleListManager");
	}
	
	@Override
	//SEAM @Observer("org.aries.ui.reset")
	public void initialize() {
		initializeContext(); 
		refreshRoleList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Role> recordList) {
		RoleListManager roleListManager = getRoleListManager();
		DataModel<RoleListObject> dataModel = roleListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	//SEAM @Observer("org.aries.refreshRoleList")
	public void refreshRoleList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<Role> refreshRecords() {
		try {
			RoleService roleService = getRoleService();
			List<Role> records = roleService.getRoleList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
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
	public void sortRecords(List<Role> roleList) {
		sortRecordsByName(roleList);
	}
	
	public void sortRecordsByName(List<Role> roleList) {
		RoleUtil.sortRecordsByName(roleList);
	}
	
	public void sortRecordsByRoleType(List<Role> roleList) {
		RoleUtil.sortRecordsByRoleType(roleList);
	}
	
}
