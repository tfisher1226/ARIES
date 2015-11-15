package nam.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

import nam.entity.FileEntity;
import nam.model.File;

import org.aries.data.map.AbstractMapper;


@Stateless
@Local(FileMapper.class)
public class FileMapper extends AbstractMapper<File, FileEntity> {
	
	@PostConstruct
	public void initialize() {
		setModelClass(File.class);
		setEntityClass(FileEntity.class);
	}
	
	public File toModel(FileEntity fileEntity) {
		if (fileEntity == null)
			return null;
		File fileModel = createModel();
		fileModel.setId(fileEntity.getId());
		fileModel.setType(fileEntity.getType());
		fileModel.setName(fileEntity.getName());
		fileModel.setPath(fileEntity.getPath());
		fileModel.setOwner(fileEntity.getOwner());
		fileModel.setCreationDate(fileEntity.getCreationDate());
		fileModel.setLastUpdate(fileEntity.getLastUpdate());
		return fileModel;
	}
	
	public List<File> toModelList(Collection<FileEntity> fileEntityList) {
		if (fileEntityList == null)
			return null;
		List<File> fileModelList = new ArrayList<File>();
		for (FileEntity fileEntity : fileEntityList) {
			File fileModel = toModel(fileEntity);
			fileModelList.add(fileModel);
		}
		return fileModelList;
	}
	
	public FileEntity toEntity(File fileModel) {
		if (fileModel == null)
			return null;
		FileEntity fileEntity = createEntity();
		toEntity(fileEntity, fileModel);
		return fileEntity;
	}
	
	public void toEntity(FileEntity fileEntity, File fileModel) {
		if (fileEntity != null && fileModel != null) {
			fileEntity.setId(fileModel.getId());
			fileEntity.setType(fileModel.getType());
			fileEntity.setName(fileModel.getName());
			fileEntity.setPath(fileModel.getPath());
			fileEntity.setOwner(fileModel.getOwner());
			fileEntity.setCreationDate(fileModel.getCreationDate());
			fileEntity.setLastUpdate(fileModel.getLastUpdate());
		}
	}
	
	public List<FileEntity> toEntityList(Collection<File> fileModelList) {
		if (fileModelList == null)
			return null;
		List<FileEntity> fileEntityList = new ArrayList<FileEntity>();
		for (File fileModel : fileModelList) {
			FileEntity fileEntity = toEntity(fileModel);
			fileEntityList.add(fileEntity);
		}
		return fileEntityList;
	}
	
}
