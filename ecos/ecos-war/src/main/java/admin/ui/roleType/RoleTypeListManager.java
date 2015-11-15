package admin.ui.roleType;

import java.io.Serializable;
import java.util.Arrays;
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

import admin.RoleType;


@Startup
@AutoCreate
@BypassInterceptors
@Name("roleTypeListManager")
@Scope(ScopeType.SESSION)
public class RoleTypeListManager extends AbstractTabListManager<RoleType, RoleTypeListObject> implements Serializable {
	
	@Override
	public String getModule() {
		return "RoleTypeList";
	}
	
	@Override
	public String getTitle() {
		return "RoleType List";
	}
	
	@Override
	public Object getRecordId(RoleType roleType) {
		return roleType.ordinal();
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
	protected RoleTypeListObject createRowObject(RoleType roleType) {
		return new RoleTypeListObject(roleType);
	}
	
	protected RoleTypeHelper getRoleTypeHelper() {
		return BeanContext.getFromSession("roleTypeHelper");
	}
	
	@Override
	@Observer("org.aries.ui.reset")
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
	}
	
	@Override
	@Observer("org.aries.refreshRoleTypeList")
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected List<RoleType> createRecordList() {
		try {
			List<RoleType> roleTypeList = Arrays.asList(RoleType.values());
			return roleTypeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}
