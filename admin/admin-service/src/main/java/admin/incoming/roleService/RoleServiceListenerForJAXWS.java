package admin.incoming.roleService;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import admin.Role;
import admin.RoleType;


@Remote(RoleService.class)
@Stateless(name = "RoleService")
@WebService(name = "roleService", serviceName = "roleServiceService", portName = "roleService", targetNamespace = "http://admin")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class RoleServiceListenerForJAXWS implements RoleService {
	
	private static final Log log = LogFactory.getLog(RoleServiceListenerForJAXWS.class);
	
	@Inject
	private RoleServiceHandler roleServiceHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	@WebMethod
	@WebResult(name = "roleList")
	@TransactionAttribute(REQUIRED)
	public List<Role> getRoleList() {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			List<Role> roleList = roleServiceHandler.getRoleList();
			Assert.notNull(roleList, "RoleList must exist");
			return roleList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "role")
	@TransactionAttribute(REQUIRED)
	public Role getRoleById(Long id) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Role role = roleServiceHandler.getRoleById(id);
			Assert.notNull(role, "Role must exist");
			return role;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "role")
	@TransactionAttribute(REQUIRED)
	public Role getRoleByName(String name) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Role role = roleServiceHandler.getRoleByName(name);
			Assert.notNull(role, "Role must exist");
			return role;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "roleList")
	@TransactionAttribute(REQUIRED)
	public List<Role> getRoleListByRoleType(RoleType roleType) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			List<Role> roleList = roleServiceHandler.getRoleListByRoleType(roleType);
			Assert.notNull(roleList, "RoleList must exist");
			return roleList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "id")
	@TransactionAttribute(REQUIRED)
	public Long addRole(Role role) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Long id = roleServiceHandler.addRole(role);
			Assert.notNull(id, "Id must exist");
			return id;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void saveRole(Role role) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			roleServiceHandler.saveRole(role);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void deleteRole(Role role) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			roleServiceHandler.deleteRole(role);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
