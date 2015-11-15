package admin.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.data.map.AbstractMapper;

import admin.Permission;
import admin.entity.PermissionEntity;


@Stateless
@Local(PermissionMapper.class)
public class PermissionMapper extends AbstractMapper<Permission, PermissionEntity> {
	
	@PostConstruct
	public void initialize() {
		setModelClass(Permission.class);
		setEntityClass(PermissionEntity.class);
	}
	
	public Permission toModel(PermissionEntity permissionEntity) {
		if (permissionEntity == null)
			return null;
		Permission permissionModel = createModel();
		permissionModel.setId(permissionEntity.getId());
		permissionModel.setTarget(permissionEntity.getTarget());
		permissionModel.setOrganization(permissionEntity.getOrganization());
		permissionModel.setActions(permissionEntity.getActions());
		permissionModel.setEnabled(permissionEntity.isEnabled());
		return permissionModel;
	}
	
	public List<Permission> toModelList(Collection<PermissionEntity> permissionEntityList) {
		if (permissionEntityList == null)
			return null;
		List<Permission> permissionModelList = new ArrayList<Permission>();
		for (PermissionEntity permissionEntity : permissionEntityList) {
			Permission permissionModel = toModel(permissionEntity);
			permissionModelList.add(permissionModel);
		}
		return permissionModelList;
	}
	
	public PermissionEntity toEntity(Permission permissionModel) {
		if (permissionModel == null)
			return null;
		PermissionEntity permissionEntity = createEntity();
		toEntity(permissionEntity, permissionModel);
		return permissionEntity;
	}
	
	public void toEntity(PermissionEntity permissionEntity, Permission permissionModel) {
		if (permissionEntity != null && permissionModel != null) {
			permissionEntity.setId(permissionModel.getId());
			permissionEntity.setTarget(permissionModel.getTarget());
			permissionEntity.setOrganization(permissionModel.getOrganization());
			permissionEntity.setActions(permissionModel.getActions());
			permissionEntity.setEnabled(permissionModel.isEnabled());
		}
	}
	
	public List<PermissionEntity> toEntityList(Collection<Permission> permissionModelList) {
		if (permissionModelList == null)
			return null;
		List<PermissionEntity> permissionEntityList = new ArrayList<PermissionEntity>();
		for (Permission permissionModel : permissionModelList) {
			PermissionEntity permissionEntity = toEntity(permissionModel);
			permissionEntityList.add(permissionEntity);
		}
		return permissionEntityList;
	}
	
}
