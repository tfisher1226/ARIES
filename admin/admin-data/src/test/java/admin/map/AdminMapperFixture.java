package admin.map;

import org.aries.common.util.CommonMapperFixture;

import admin.map.PermissionMapper;
import admin.map.PreferencesMapper;
import admin.map.RoleMapper;
import admin.map.SkinMapper;
import admin.map.UserMapper;


public class AdminMapperFixture {

	private static PermissionMapper permissionMapper;

	private static PreferencesMapper preferencesMapper;

	private static RegistrationMapper registrationMapper;
	
	private static RoleMapper roleMapper;

	private static SkinMapper skinMapper;

	private static UserMapper userMapper;


	public static PermissionMapper getPermissionMapper() {
		if (permissionMapper == null) {
			permissionMapper = new PermissionMapper();
		}
		return permissionMapper;
	}

	public static PreferencesMapper getPreferencesMapper() {
		if (preferencesMapper == null) {
			preferencesMapper = new PreferencesMapper();
		}
		return preferencesMapper;
	}

	public static RegistrationMapper getRegistrationMapper() {
		if (registrationMapper == null) {
			registrationMapper = new RegistrationMapper();
		}
		return registrationMapper;
	}
	
	public static RoleMapper getRoleMapper() {
		if (roleMapper == null) {
			roleMapper = new RoleMapper();
		}
		return roleMapper;
	}

	public static SkinMapper getSkinMapper() {
		if (skinMapper == null) {
			skinMapper = new SkinMapper();
		}
		return skinMapper;
	}

	public static UserMapper getUserMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
			//userMapper.emailAccountMapper = CommonMapperFixture.getEmailAccountMapper();
			userMapper.emailAddressMapper = CommonMapperFixture.getEmailAddressMapper();
			userMapper.streetAddressMapper = CommonMapperFixture.getStreetAddressMapper();
			userMapper.phoneNumberMapper = CommonMapperFixture.getPhoneNumberMapper();
			userMapper.roleMapper = AdminMapperFixture.getRoleMapper();
			userMapper.preferencesMapper = AdminMapperFixture.getPreferencesMapper();
		}
		return userMapper;
	}

}
