package admin.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.data.map.AbstractMapper;

import admin.Registration;
import admin.entity.RegistrationEntity;


@Stateless
@Local(RegistrationMapper.class)
public class RegistrationMapper extends AbstractMapper<Registration, RegistrationEntity> {
	
	@Inject
	protected UserMapper userMapper;
	
	
	public UserMapper getUserMapper() {
		return userMapper;
	}
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@PostConstruct
	public void initialize() {
		setModelClass(Registration.class);
		setEntityClass(RegistrationEntity.class);
	}
	
	public Registration toModel(RegistrationEntity registrationEntity) {
		if (registrationEntity == null)
			return null;
		Registration registrationModel = createModel();
		registrationModel.setId(registrationEntity.getId());
		registrationModel.setEnabled(registrationEntity.isEnabled());
		registrationModel.setUser(userMapper.toModel(registrationEntity.getUser()));
		registrationModel.setLoginCount(registrationEntity.getLoginCount());
		return registrationModel;
	}
	
	public List<Registration> toModelList(Collection<RegistrationEntity> registrationEntityList) {
		if (registrationEntityList == null)
			return null;
		List<Registration> registrationModelList = new ArrayList<Registration>();
		for (RegistrationEntity registrationEntity : registrationEntityList) {
			Registration registrationModel = toModel(registrationEntity);
			registrationModelList.add(registrationModel);
		}
		return registrationModelList;
	}
	
	public RegistrationEntity toEntity(Registration registrationModel) {
		if (registrationModel == null)
			return null;
		RegistrationEntity registrationEntity = createEntity();
		toEntity(registrationEntity, registrationModel);
		return registrationEntity;
	}
	
	public void toEntity(RegistrationEntity registrationEntity, Registration registrationModel) {
		if (registrationEntity != null && registrationModel != null) {
			registrationEntity.setId(registrationModel.getId());
			registrationEntity.setEnabled(registrationModel.isEnabled());
			registrationEntity.setUser(userMapper.toEntity(registrationModel.getUser()));
			registrationEntity.setLoginCount(registrationModel.getLoginCount());
		}
	}
	
	public List<RegistrationEntity> toEntityList(Collection<Registration> registrationModelList) {
		if (registrationModelList == null)
			return null;
		List<RegistrationEntity> registrationEntityList = new ArrayList<RegistrationEntity>();
		for (Registration registrationModel : registrationModelList) {
			RegistrationEntity registrationEntity = toEntity(registrationModel);
			registrationEntityList.add(registrationEntity);
		}
		return registrationEntityList;
	}
	
}
