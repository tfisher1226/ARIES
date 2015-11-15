package admin.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.data.map.AbstractMapper;
import org.aries.util.collection.ListUtil;

import admin.Permission;
import admin.Role;
import admin.entity.PermissionEntity;
import admin.entity.RoleEntity;


@Stateless
@Local(RoleMapper.class)
public class RoleMapper extends AbstractMapper<Role, RoleEntity> {
	
	@Inject
	protected PermissionMapper permissionMapper;
	
	
	public PermissionMapper getPermissionMapper() {
		return permissionMapper;
	}
	
	public void setPermissionMapper(PermissionMapper permissionMapper) {
		this.permissionMapper = permissionMapper;
	}

	@PostConstruct
	public void initialize() {
		setModelClass(Role.class);
		setEntityClass(RoleEntity.class);
		permissionMapper.setModelClass(Permission.class);
		permissionMapper.setEntityClass(PermissionEntity.class);
	}
	
	public Role toModel(RoleEntity roleEntity) {
		if (roleEntity == null)
			return null;
		Role roleModel = toModelFlat(roleEntity);
		Set<RoleEntity> groupsEntityList = (Set<RoleEntity>) roleEntity.getGroups();
		if (groupsEntityList != null) {
			List<Role> groupsModelList = toModelList(groupsEntityList);
			ListUtil.replaceElements((Set<Role>) roleModel.getGroups(), groupsModelList);
		}
		return roleModel;
	}
	
	public Role toModelFlat(RoleEntity roleEntity) {
		if (roleEntity == null)
			return null;
		Role roleModel = createModel();
		roleModel.setId(roleEntity.getId());
		roleModel.setName(roleEntity.getName());
		roleModel.setRoleType(roleEntity.getRoleType());
		roleModel.setPermissions(new ArrayList<Permission>(permissionMapper.toModelList(roleEntity.getPermissions())));
		roleModel.setConditional(roleEntity.isConditional());
		roleModel.setEnabled(roleEntity.isEnabled());
		return roleModel;
	}
	
	public List<Role> toModelList(Collection<RoleEntity> roleEntityList) {
		if (roleEntityList == null)
			return null;
		List<Role> roleModelList = new ArrayList<Role>();
		for (RoleEntity roleEntity : roleEntityList) {
			Role roleModel = toModel(roleEntity);
			roleModelList.add(roleModel);
		}
		return roleModelList;
	}
	
//	public List<Role> toModelList(Collection<RoleEntity> roleEntityList) {
//		if (roleEntityList == null)
//			return null;
//		List<Role> roleModelList = new ArrayList<Role>();
//		for (E roleEntity : roleEntityList) {
//			M roleModel = toModel(roleEntity);
//			roleModelList.add(roleModel);
//		}
//		return roleModelList;
//	}
	
	
//	public E toEntity(M roleModel) {
//		if (roleModel == null)
//			return null;
//		E roleEntity = createEntity();
//		toEntity(roleEntity, roleModel);
//		return roleEntity;
//	}
	
	public RoleEntity toEntity(Role roleModel) {
		if (roleModel == null)
			return null;
		RoleEntity roleEntity = toEntityFlat(roleModel);
		Set<Role> groupsModelList = (Set<Role>) roleModel.getGroups();
		if (groupsModelList != null) {
			List<RoleEntity> groupsEntityList = toEntityList(groupsModelList);
			ListUtil.replaceElements((Set<RoleEntity>) roleEntity.getGroups(), groupsEntityList);
		}
		return roleEntity;
	}

	public RoleEntity toEntityFlat(Role roleModel) {
		if (roleModel == null)
			return null;
		RoleEntity roleEntity = createEntity();
		roleEntity.setId(roleModel.getId());
		roleEntity.setName(roleModel.getName());
		roleEntity.setRoleType(roleModel.getRoleType());
		//@SuppressWarnings("unchecked") Set<Role> groupsModelSet = (Set<Role>) roleModel.getGroups();
		//roleEntity.setGroups(new HashSet<RoleEntity>(this.toEntityList(groupsModelSet)));
		roleEntity.setPermissions(new ArrayList<PermissionEntity>(permissionMapper.toEntityList(roleModel.getPermissions())));
		roleEntity.setConditional(roleModel.isConditional());
		roleEntity.setEnabled(roleModel.isEnabled());
		return roleEntity;
	}

	public List<RoleEntity> toEntityList(Collection<Role> roleModelList) {
		return toEntityList(null, roleModelList);
	}
	
	public List<RoleEntity> toEntityList(RoleEntity parentEntity, Collection<Role> roleModelList) {
		if (roleModelList == null)
			return null;
		List<RoleEntity> roleEntityList = new ArrayList<RoleEntity>();
		for (Role roleModel : roleModelList) {
			RoleEntity roleEntity = toEntity(roleModel);
			roleEntityList.add(roleEntity);
		}
		return roleEntityList;
	}
	
}
