package admin.client.roleService;

import java.util.List;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;

import admin.Role;
import admin.RoleType;


@SuppressWarnings("serial")
public class RoleServiceInterceptor extends MessageInterceptor<RoleService> implements RoleService {
	
	@Override
	public List<Role> getRoleList() {
		try {
			log.info("#### [admin]: getRoleList() sending...");
			Message request = createMessage("getRoleList");
			Message response = getProxy().invoke(request);
			List<Role> roleList = response.getPart("roleList");
			return roleList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Role getRoleById(Long id) {
		try {
			log.info("#### [admin]: getRoleById() sending...");
			Message request = createMessage("getRoleById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
			Role role = response.getPart("role");
			return role;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Role getRoleByName(String name) {
		try {
			log.info("#### [admin]: getRoleByName() sending...");
			Message request = createMessage("getRoleByName");
			request.addPart("name", name);
			Message response = getProxy().invoke(request);
			Role role = response.getPart("role");
			return role;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Role> getRoleListByRoleType(RoleType roleType) {
		try {
			log.info("#### [admin]: getRoleListByRoleType() sending...");
			Message request = createMessage("getRoleListByRoleType");
			request.addPart("roleType", roleType);
			Message response = getProxy().invoke(request);
			List<Role> roleList = response.getPart("roleList");
			return roleList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addRole(Role role) {
		try {
			log.info("#### [admin]: addRole() sending...");
			Message request = createMessage("addRole");
			request.addPart("role", role);
			Message response = getProxy().invoke(request);
			Long id = response.getPart("id");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveRole(Role role) {
		try {
			log.info("#### [admin]: saveRole() sending...");
			Message request = createMessage("saveRole");
			request.addPart("role", role);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteRole(Role role) {
		try {
			log.info("#### [admin]: deleteRole() sending...");
			Message request = createMessage("deleteRole");
			request.addPart("role", role);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
