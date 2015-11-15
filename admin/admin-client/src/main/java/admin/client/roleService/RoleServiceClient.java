package admin.client.roleService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;

import admin.Role;
import admin.RoleType;


public class RoleServiceClient extends AbstractDelegate implements RoleService {
	
	private static final Log log = LogFactory.getLog(RoleServiceClient.class);
	

	@Override
	public String getDomain() {
		return "admin";
	}
	
	@Override
	public String getServiceId() {
		return RoleService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public RoleService getProxy() throws Exception {
		return getProxy(RoleService.ID);
	}

	@Override
	public List<Role> getRoleList() {
		try {
			List<Role> roleList = getProxy().getRoleList();
			return roleList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Role getRoleById(Long id) {
		try {
			Role role = getProxy().getRoleById(id);
			return role;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Role getRoleByName(String name) {
		try {
			Role role = getProxy().getRoleByName(name);
			return role;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Role> getRoleListByRoleType(RoleType roleType) {
		try {
			List<Role> roleList = getProxy().getRoleListByRoleType(roleType);
			return roleList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addRole(Role role) {
		try {
		Long id = getProxy().addRole(role);
			return id;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveRole(Role role) {
		try {
			getProxy().saveRole(role);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteRole(Role role) {
		try {
			getProxy().deleteRole(role);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
