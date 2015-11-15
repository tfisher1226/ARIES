package admin.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.aries.data.map.AbstractMapper;

import admin.Preferences;
import admin.User;
import admin.entity.PreferencesEntity;
import admin.entity.UserEntity;


@Stateless
@Local(PreferencesMapper.class)
public class PreferencesMapper extends AbstractMapper<Preferences, PreferencesEntity> {
	
	@PostConstruct
	public void initialize() {
		setModelClass(Preferences.class);
		setEntityClass(PreferencesEntity.class);
	}
	
	public Preferences toModel(User user, PreferencesEntity preferencesEntity) {
		if (preferencesEntity == null)
			return null;
		Preferences preferencesModel = createModel();
		preferencesModel.setId(preferencesEntity.getId());
		preferencesModel.setUser(user);
		preferencesModel.setThemeId(preferencesEntity.getThemeId());
		preferencesModel.setWorkState(preferencesEntity.getWorkState());
		preferencesModel.setOpenNodes(preferencesEntity.getOpenNodes());
		preferencesModel.setSelectedNode(preferencesEntity.getSelectedNode());
		preferencesModel.setEnableTooltips(preferencesEntity.isEnableTooltips());
		return preferencesModel;
	}
	
	public List<Preferences> toModelList(User userModel, Collection<PreferencesEntity> preferencesEntityList) {
		if (preferencesEntityList == null)
			return null;
		List<Preferences> preferencesModelList = new ArrayList<Preferences>();
		for (PreferencesEntity preferencesEntity : preferencesEntityList) {
			Preferences preferencesModel = toModel(userModel, preferencesEntity);
			preferencesModelList.add(preferencesModel);
		}
		return preferencesModelList;
	}
	
	public PreferencesEntity toEntity(UserEntity userEntity, Preferences preferencesModel) {
		if (preferencesModel == null)
			return null;
		PreferencesEntity preferencesEntity = createEntity();
		toEntity(userEntity, preferencesEntity, preferencesModel);
		return preferencesEntity;
	}
	
	public void toEntity(UserEntity userEntity, PreferencesEntity preferencesEntity, Preferences preferencesModel) {
		if (preferencesEntity != null && preferencesModel != null) {
			preferencesEntity.setId(preferencesModel.getId());
			preferencesEntity.setUser(userEntity);
			preferencesEntity.setThemeId(preferencesModel.getThemeId());
			preferencesEntity.setWorkState(preferencesModel.getWorkState());
			preferencesEntity.setOpenNodes(preferencesModel.getOpenNodes());
			preferencesEntity.setSelectedNode(preferencesModel.getSelectedNode());
			preferencesEntity.setEnableTooltips(preferencesModel.isEnableTooltips());
		}
	}
	
	public List<PreferencesEntity> toEntityList(UserEntity userEntity, Collection<Preferences> preferencesModelList) {
		if (preferencesModelList == null)
			return null;
		List<PreferencesEntity> preferencesEntityList = new ArrayList<PreferencesEntity>();
		for (Preferences preferencesModel : preferencesModelList) {
			PreferencesEntity preferencesEntity = toEntity(userEntity, preferencesModel);
			preferencesEntityList.add(preferencesEntity);
		}
		return preferencesEntityList;
	}
	
}
