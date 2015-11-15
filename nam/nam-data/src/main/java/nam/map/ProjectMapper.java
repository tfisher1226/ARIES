package nam.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nam.entity.FileEntity;
import nam.entity.ProjectEntity;
import nam.model.File;
import nam.model.Project;

import org.aries.data.map.AbstractMapper;


@Stateless
@Local(ProjectMapper.class)
public class ProjectMapper extends AbstractMapper<Project, ProjectEntity> {
	
	@Inject
	protected FileMapper fileMapper;
	
	
	public FileMapper getFileMapper() {
		return fileMapper;
	}
	
	public void setFileMapper(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}

	@PostConstruct
	public void initialize() {
		setModelClass(Project.class);
		setEntityClass(ProjectEntity.class);
		fileMapper.setModelClass(File.class);
		fileMapper.setEntityClass(FileEntity.class);
	}
	
	public Project toModel(ProjectEntity projectEntity) {
		if (projectEntity == null)
			return null;
		Project projectModel = toModelFlat(projectEntity);
		return projectModel;
	}
	
	public Project toModelFlat(ProjectEntity projectEntity) {
		if (projectEntity == null)
			return null;
		Project projectModel = createModel();
		projectModel.setId(projectEntity.getId());
		projectModel.setName(projectEntity.getName());
		projectModel.setDomain(projectEntity.getDomain());
		projectModel.setVersion(projectEntity.getVersion());
		projectModel.setOwner(projectEntity.getOwner());
		projectModel.setEnabled(projectEntity.getEnabled());
		projectModel.setShared(projectEntity.getShared());
		return projectModel;
	}
	
	public List<Project> toModelList(Collection<ProjectEntity> projectEntityList) {
		if (projectEntityList == null)
			return null;
		List<Project> projectModelList = new ArrayList<Project>();
		for (ProjectEntity projectEntity : projectEntityList) {
			Project projectModel = toModel(projectEntity);
			projectModelList.add(projectModel);
		}
		return projectModelList;
	}
	
//	public List<Project> toModelList(Collection<ProjectEntity> projectEntityList) {
//		if (projectEntityList == null)
//			return null;
//		List<Project> projectModelList = new ArrayList<Project>();
//		for (E projectEntity : projectEntityList) {
//			M projectModel = toModel(projectEntity);
//			projectModelList.add(projectModel);
//		}
//		return projectModelList;
//	}
	
	
//	public E toEntity(M projectModel) {
//		if (projectModel == null)
//			return null;
//		E projectEntity = createEntity();
//		toEntity(projectEntity, projectModel);
//		return projectEntity;
//	}
	
	public ProjectEntity toEntity(Project projectModel) {
		if (projectModel == null)
			return null;
		ProjectEntity projectEntity = toEntityFlat(projectModel);
		return projectEntity;
	}

	public ProjectEntity toEntityFlat(Project projectModel) {
		if (projectModel == null)
			return null;
		ProjectEntity projectEntity = createEntity();
		projectEntity.setId(projectModel.getId());
		projectEntity.setName(projectModel.getName());
		projectEntity.setDomain(projectModel.getDomain());
		projectEntity.setVersion(projectModel.getVersion());
		projectEntity.setOwner(projectModel.getOwner());
		projectEntity.setEnabled(projectModel.getEnabled());
		projectEntity.setShared(projectModel.getShared());
		//TODO projectEntity.setFiles(new ArrayList<FileEntity>(fileMapper.toEntityList(projectModel.getFiles())));
		return projectEntity;
	}

	public List<ProjectEntity> toEntityList(Collection<Project> projectModelList) {
		return toEntityList(null, projectModelList);
	}
	
	public List<ProjectEntity> toEntityList(ProjectEntity parentEntity, Collection<Project> projectModelList) {
		if (projectModelList == null)
			return null;
		List<ProjectEntity> projectEntityList = new ArrayList<ProjectEntity>();
		for (Project projectModel : projectModelList) {
			ProjectEntity projectEntity = toEntity(projectModel);
			projectEntityList.add(projectEntity);
		}
		return projectEntityList;
	}
	
}
