package nam.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

import nam.entity.WorkspaceEntity;
import nam.model.Workspace;

import org.aries.data.map.AbstractMapper;


@Stateless
@Local(WorkspaceMapper.class)
public class WorkspaceMapper extends AbstractMapper<Workspace, WorkspaceEntity> {
	
	@PostConstruct
	public void initialize() {
		setModelClass(Workspace.class);
		setEntityClass(WorkspaceEntity.class);
	}
	
	public Workspace toModel(WorkspaceEntity workspaceEntity) {
		if (workspaceEntity == null)
			return null;
		Workspace workspaceModel = createModel();
		workspaceModel.setId(workspaceEntity.getId());
		workspaceModel.setName(workspaceEntity.getName());
		workspaceModel.setUser(workspaceEntity.getUser());
		workspaceModel.setEnabled(workspaceEntity.isEnabled());
		workspaceModel.getProjects().addAll(workspaceEntity.getProjects());
		workspaceModel.setCreationDate(workspaceEntity.getCreationDate());
		workspaceModel.setLastUpdate(workspaceEntity.getLastUpdate());
		return workspaceModel;
	}
	
	public List<Workspace> toModelList(Collection<WorkspaceEntity> workspaceEntityList) {
		if (workspaceEntityList == null)
			return null;
		List<Workspace> workspaceModelList = new ArrayList<Workspace>();
		for (WorkspaceEntity workspaceEntity : workspaceEntityList) {
			Workspace workspaceModel = toModel(workspaceEntity);
			workspaceModelList.add(workspaceModel);
		}
		return workspaceModelList;
	}
	
	public WorkspaceEntity toEntity(Workspace workspaceModel) {
		if (workspaceModel == null)
			return null;
		WorkspaceEntity workspaceEntity = createEntity();
		toEntity(workspaceEntity, workspaceModel);
		return workspaceEntity;
	}
	
	public void toEntity(WorkspaceEntity workspaceEntity, Workspace workspaceModel) {
		if (workspaceEntity != null && workspaceModel != null) {
			workspaceEntity.setId(workspaceModel.getId());
			workspaceEntity.setName(workspaceModel.getName());
			workspaceEntity.setUser(workspaceModel.getUser());
			workspaceEntity.setEnabled(workspaceModel.getEnabled());
			workspaceEntity.setCreationDate(workspaceModel.getCreationDate());
			workspaceEntity.setLastUpdate(workspaceModel.getLastUpdate());
		}
	}
	
	public List<WorkspaceEntity> toEntityList(Collection<Workspace> workspaceModelList) {
		if (workspaceModelList == null)
			return null;
		List<WorkspaceEntity> workspaceEntityList = new ArrayList<WorkspaceEntity>();
		for (Workspace workspaceModel : workspaceModelList) {
			WorkspaceEntity workspaceEntity = toEntity(workspaceModel);
			workspaceEntityList.add(workspaceEntity);
		}
		return workspaceEntityList;
	}
	
}
