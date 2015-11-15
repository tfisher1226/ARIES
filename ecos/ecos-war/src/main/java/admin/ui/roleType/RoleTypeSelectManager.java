package admin.ui.roleType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

import admin.RoleType;


@Startup
@AutoCreate
@BypassInterceptors
@Name("roleTypeSelectManager")
@Scope(ScopeType.SESSION)
public class RoleTypeSelectManager extends AbstractSelectManager<RoleType, RoleTypeListObject> implements Serializable {
	
	@Override
	public String getClientId() {
		return "RoleTypeSelect";
	}
	
	@Override
	public String getTitle() {
		return "RoleType Selection";
	}
	
	@Override
	protected Class<RoleType> getRecordClass() {
		return RoleType.class;
	}
	
	@Override
	public String toString(RoleType roleType) {
		return roleType.name();
	}
	
	protected RoleTypeHelper getRoleTypeHelper() {
		return BeanContext.getFromSession("roleTypeHelper");
	}
	
	protected RoleTypeListManager getRoleTypeListManager() {
		return BeanContext.getFromSession("roleTypeListManager");
	}
	
	@Override
	@Observer("org.aries.ui.reset")
	public void initialize() {
		initializeContext(); 
		refreshRoleTypeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<RoleType> recordList) {
		RoleTypeListManager roleTypeListManager = getRoleTypeListManager();
		DataModel<RoleTypeListObject> dataModel = roleTypeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	@Observer("org.aries.refreshRoleTypeList")
	public void refreshRoleTypeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected List<RoleType> refreshRecords() {
		RoleType[] values = RoleType.values();
		List<RoleType> masterList = new ArrayList<RoleType>();
		for (RoleType capability : values) {
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
	public void sortRecords(List<RoleType> roleTypeList) {
		Collections.sort(roleTypeList);
	}

}
