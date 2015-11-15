package admin.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.aries.common.map.EmailAddressMapper;
import org.aries.common.map.PersonNameMapper;
import org.aries.common.map.PhoneNumberMapper;
import org.aries.common.map.StreetAddressMapper;
import org.aries.data.map.AbstractMapper;

import admin.Permission;
import admin.Role;
import admin.User;
import admin.entity.PermissionEntity;
import admin.entity.RoleEntity;
import admin.entity.UserEntity;


@Stateless
@Local(UserMapper.class)
public class UserMapper extends AbstractMapper<User, UserEntity> {
	
	@Inject
	protected PersonNameMapper personNameMapper;
	
	@Inject
	protected EmailAddressMapper emailAddressMapper;
	
	@Inject
	protected StreetAddressMapper streetAddressMapper;
	
	@Inject
	protected PhoneNumberMapper phoneNumberMapper;
	
	@Inject
	protected RoleMapper roleMapper;
	
	@Inject
	protected PermissionMapper permissionMapper;
	
	@Inject
	protected PreferencesMapper preferencesMapper;
	
	
	public PersonNameMapper getPersonNameMapper() {
		return personNameMapper;
	}
	
	public void setPersonNameMapper(PersonNameMapper personNameMapper) {
		this.personNameMapper = personNameMapper;
	}
	
	public EmailAddressMapper getEmailAddressMapper() {
		return emailAddressMapper;
	}
	
	public void setEmailAddressMapper(EmailAddressMapper emailAddressMapper) {
		this.emailAddressMapper = emailAddressMapper;
	}
	
	public StreetAddressMapper getStreetAddressMapper() {
		return streetAddressMapper;
	}
	
	public void setStreetAddressMapper(StreetAddressMapper streetAddressMapper) {
		this.streetAddressMapper = streetAddressMapper;
	}
	
	public PhoneNumberMapper getPhoneNumberMapper() {
		return phoneNumberMapper;
	}
	
	public void setPhoneNumberMapper(PhoneNumberMapper phoneNumberMapper) {
		this.phoneNumberMapper = phoneNumberMapper;
	}
	
	public RoleMapper getRoleMapper() {
		return roleMapper;
	}
	
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}
	
	public PermissionMapper getPermissionMapper() {
		return permissionMapper;
	}
	
	public void setPermissionMapper(PermissionMapper permissionMapper) {
		this.permissionMapper = permissionMapper;
	}
	
	public PreferencesMapper getPreferencesMapper() {
		return preferencesMapper;
	}
	
	public void setPreferencesMapper(PreferencesMapper preferencesMapper) {
		this.preferencesMapper = preferencesMapper;
	}
	
	@PostConstruct
	public void initialize() {
		setModelClass(User.class);
		setEntityClass(UserEntity.class);
	}
	
	public User toModel(UserEntity userEntity) {
		if (userEntity == null)
			return null;
		User userModel = createModel();
		userModel.setId(userEntity.getId());
		//userModel.setName(personNameMapper.toModel(userEntity.getName()));
		userModel.setPersonName(personNameMapper.toModel(userEntity.getPersonName()));
		userModel.setUserName(userEntity.getUserName());
		userModel.setPassword(userEntity.getPassword());
		userModel.setPassword2(userEntity.getPassword2());
		userModel.setEnabled(userEntity.isEnabled());
		userModel.setEmailAddress(emailAddressMapper.toModel(userEntity.getEmailAddress()));
		userModel.setStreetAddress(streetAddressMapper.toModel(userEntity.getStreetAddress()));
		userModel.setHomePhone(phoneNumberMapper.toModel(userEntity.getHomePhone()));
		userModel.setCellPhone(phoneNumberMapper.toModel(userEntity.getCellPhone()));
		userModel.setRoles(new HashSet<Role>(roleMapper.toModelList(userEntity.getRoles())));
		userModel.setPermissions(new ArrayList<Permission>(permissionMapper.toModelList(userEntity.getPermissions())));
		userModel.setPreferences(preferencesMapper.toModel(userModel, userEntity.getPreferences()));
		userModel.setCreationDate(userEntity.getCreationDate());
		userModel.setLastUpdate(userEntity.getLastUpdate());
		return userModel;
	}
	
	public List<User> toModelList(Collection<UserEntity> userEntityList) {
		if (userEntityList == null)
			return null;
		List<User> userModelList = new ArrayList<User>();
		for (UserEntity userEntity : userEntityList) {
			User userModel = toModel(userEntity);
			userModelList.add(userModel);
		}
		return userModelList;
	}
	
	public UserEntity toEntity(User userModel) {
		if (userModel == null)
			return null;
		UserEntity userEntity = createEntity();
		toEntity(userEntity, userModel);
		return userEntity;
	}
	
	public void toEntity(UserEntity userEntity, User userModel) {
		if (userEntity != null && userModel != null) {
			userEntity.setId(userModel.getId());
			//userEntity.setName(personNameMapper.toEntity(userModel.getName()));
			userEntity.setPersonName(personNameMapper.toEntity(userModel.getPersonName()));
			userEntity.setUserName(userModel.getUserName());
			userEntity.setPassword(userModel.getPassword());
			userEntity.setPassword2(userModel.getPassword2());
			userEntity.setEnabled(userModel.isEnabled());
			userEntity.setEmailAddress(emailAddressMapper.toEntity(userModel.getEmailAddress()));
			userEntity.setStreetAddress(streetAddressMapper.toEntity(userModel.getStreetAddress()));
			userEntity.setHomePhone(phoneNumberMapper.toEntity(userModel.getHomePhone()));
			userEntity.setCellPhone(phoneNumberMapper.toEntity(userModel.getCellPhone()));
			userEntity.setRoles(new HashSet<RoleEntity>(roleMapper.toEntityList(userModel.getRoles())));
			userEntity.setPermissions(new ArrayList<PermissionEntity>(permissionMapper.toEntityList(userModel.getPermissions())));
			userEntity.setPreferences(preferencesMapper.toEntity(userEntity, userModel.getPreferences()));
			userEntity.setCreationDate(userModel.getCreationDate());
			userEntity.setLastUpdate(userModel.getLastUpdate());
		}
	}
	
	public List<UserEntity> toEntityList(Collection<User> userModelList) {
		if (userModelList == null)
			return null;
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		for (User userModel : userModelList) {
			UserEntity userEntity = toEntity(userModel);
			userEntityList.add(userEntity);
		}
		return userEntityList;
	}
	
}
