package admin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aries.Assert;
import org.aries.common.util.CommonFixture;
import org.aries.util.BaseFixture;

import admin.Action;
import admin.Permission;
import admin.PermissionCriteria;
import admin.Preferences;
import admin.PreferencesCriteria;
import admin.Registration;
import admin.Role;
import admin.RoleCriteria;
import admin.RoleType;
import admin.Skin;
import admin.SkinCriteria;
import admin.User;
import admin.UserCriteria;


public class AdminFixture extends BaseFixture {
	
	private static long userCount = 0L;
	
	private static long userCriteriaCount = 0L;
	
	private static long roleCount = 0L;
	
	private static long permissionCount = 0L;
	
	private static long preferencesCount = 0L;
	
	private static long registrationCount = 0L;
	
	private static long skinCount = 0L;
	
	
	public static User createEmptyUser() {
		User user = new User();
		return user;
	}
	
	public static User createUser() {
		User user = createUser(userCount++);
		return user;
	}
	
	public static User createUser(long index) {
		User user = createEmptyUser();
		user.setUserName("dummyUserName" + index);
		user.setPassword("dummyPassword" + index);
		user.setPassword2("dummyPassword2" + index);
		user.setEnabled(true);
		user.setCreationDate(new Date(1000L + index));
		user.setLastUpdate(new Date(1000L + index));
		//user.setName(CommonFixture.create_PersonName(index));
		user.setPersonName(CommonFixture.create_PersonName(index));
		user.setEmailAddress(CommonFixture.create_EmailAddress(index));
		user.setStreetAddress(CommonFixture.create_StreetAddress(index));
		user.setHomePhone(CommonFixture.create_PhoneNumber(index));
		user.setCellPhone(CommonFixture.create_PhoneNumber(index));
		//user.getRoles().addAll(createEmptyRole_List());
		user.getRoles().addAll(createRole_List(2));
		user.getPermissions().addAll(createPermission_List(2));
		user.setPreferences(createPreferences(user, index));
		user.setId(10L * index);
		return user;
	}
	
	public static List<User> createEmptyUser_List() {
		return new ArrayList<User>();
	}
	
	public static List<User> createUser_List() {
		return createUser_List(2);
	}
	
	public static List<User> createUser_List(int size) {
		return createUser_List(userCount++, size);
	}
	
	public static List<User> createUser_List(long index, int size) {
		List<User> userList = createEmptyUser_List();
		long limit = index + size;
		for (; index < limit; index++)
			userList.add(createUser(index));
		return userList;
	}
	
	public static Set<User> createEmptyUser_Set() {
		return new HashSet<User>();
	}
	
	public static Set<User> createUser_Set() {
		return createUser_Set(2);
	}
	
	public static Set<User> createUser_Set(int size) {
		return createUser_Set(userCount++, size);
	}
	
	public static Set<User> createUser_Set(long index, int size) {
		Set<User> userSet = createEmptyUser_Set();
		long limit = index + size;
		for (; index < limit; index++)
			userSet.add(createUser(index));
		return userSet;
	}
	
	public static void assertUserExists(Collection<User> userList, User user) {
		Assert.notNull(userList, "User list must be specified");
		Assert.notNull(user, "User record must be specified");
		Iterator<User> iterator = userList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			User user1 = iterator.next();
			if (user1.equals(user)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - User should exist: "+user);
	}
	
	public static void assertUserCorrect(User user) {
		long index = user.getId() / 10L;
		assertObjectCorrect("UserName", user.getUserName(), index);
		assertObjectCorrect("Password", user.getPassword(), index);
		assertObjectCorrect("Password2", user.getPassword2(), index);
		assertObjectCorrect("Enabled", user.getEnabled(), index);
		assertObjectCorrect("CreationDate", user.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", user.getLastUpdate(), index);
		//CommonFixture.assertPersonNameCorrect(user.getName());
		CommonFixture.assertPersonNameCorrect(user.getPersonName());
		CommonFixture.assertEmailAddressCorrect(user.getEmailAddress());
		CommonFixture.assertStreetAddressCorrect(user.getStreetAddress());
		CommonFixture.assertPhoneNumberCorrect(user.getHomePhone());
		CommonFixture.assertPhoneNumberCorrect(user.getCellPhone());
		assertRoleCorrect(user.getRoles());
		assertPermissionCorrect(user.getPermissions());
		assertPreferencesCorrect(user.getPreferences());
	}
	
	public static void assertUserCorrect(Collection<User> userList) {
		Assert.isTrue(userList.size() == 2, "User count not correct");
		Iterator<User> iterator = userList.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			assertUserCorrect(user);
		}
	}
	
	public static void assertSameUser(User user1, User user2) {
		assertSameUser(user1, user2, "");
	}
	
	public static void assertSameUser(User user1, User user2, String message) {
		assertObjectExists("User1", user1);
		assertObjectExists("User2", user2);
		assertObjectEquals("UserName", user1.getUserName(), user2.getUserName(), message);
		assertObjectEquals("Password", user1.getPassword(), user2.getPassword(), message);
		assertObjectEquals("Password2", user1.getPassword2(), user2.getPassword2(), message);
		assertObjectEquals("Enabled", user1.getEnabled(), user2.getEnabled(), message);
		assertObjectEquals("CreationDate", user1.getCreationDate(), user2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", user1.getLastUpdate(), user2.getLastUpdate(), message);
		//CommonFixture.assertSamePersonName(user1.getName(), user2.getName(), message);
		CommonFixture.assertSamePersonName(user1.getPersonName(), user2.getPersonName(), message);
		CommonFixture.assertSameEmailAddress(user1.getEmailAddress(), user2.getEmailAddress(), message);
		CommonFixture.assertSameStreetAddress(user1.getStreetAddress(), user2.getStreetAddress(), message);
		CommonFixture.assertSamePhoneNumber(user1.getHomePhone(), user2.getHomePhone(), message);
		CommonFixture.assertSamePhoneNumber(user1.getCellPhone(), user2.getCellPhone(), message);
		assertSameRole(user1.getRoles(), user2.getRoles(), message);
		assertSamePermission(user1.getPermissions(), user2.getPermissions(), message);
		assertSamePreferences(user1.getPreferences(), user2.getPreferences(), message);
	}
	
	public static void assertSameUser(Collection<User> userList1, Collection<User> userList2) {
		assertSameUser(userList1, userList2, "");
	}
	
	public static void assertSameUser(Collection<User> userList1, Collection<User> userList2, String message) {
		Assert.notNull(userList1, "User list1 must be specified");
		Assert.notNull(userList2, "User list2 must be specified");
		Assert.equals(userList1.size(), userList2.size(), "User count not equal");
		Collection<User> sortedRecords1 = UserUtil.sortRecords(userList1);
		Collection<User> sortedRecords2 = UserUtil.sortRecords(userList2);
		Iterator<User> list1Iterator = sortedRecords1.iterator();
		Iterator<User> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			User user1 = list1Iterator.next();
			User user2 = list2Iterator.next();
			assertSameUser(user1, user2, message);
		}
	}
	
	public static UserCriteria createEmptyUserCriteria() {
		UserCriteria userCriteria = new UserCriteria();
		return userCriteria;
	}
	
	public static UserCriteria createUserCriteria() {
		UserCriteria userCriteria = createUserCriteria(userCriteriaCount++);
		return userCriteria;
	}
	
	public static UserCriteria createUserCriteria(long index) {
		UserCriteria userCriteria = createEmptyUserCriteria();
		userCriteria.setEnabled(true);
		userCriteria.setUserName("dummyUserName" + index);
		userCriteria.setPersonName(CommonFixture.create_PersonName(index));
		userCriteria.setEmailAddress(CommonFixture.create_EmailAddress(index));
		userCriteria.setStreetAddress(CommonFixture.create_StreetAddress(index));
		userCriteria.setRoles(createRole(index));
		return userCriteria;
	}
	
	public static List<UserCriteria> createEmptyUserCriteria_List() {
		return new ArrayList<UserCriteria>();
	}
	
	public static List<UserCriteria> createUserCriteria_List() {
		return createUserCriteria_List(2);
	}
	
	public static List<UserCriteria> createUserCriteria_List(int size) {
		return createUserCriteria_List(userCriteriaCount++, size);
	}
	
	public static List<UserCriteria> createUserCriteria_List(long index, int size) {
		List<UserCriteria> userCriteriaList = createEmptyUserCriteria_List();
		long limit = index + size;
		for (; index < limit; index++)
			userCriteriaList.add(createUserCriteria(index));
		return userCriteriaList;
	}
	
	public static Set<UserCriteria> createEmptyUserCriteria_Set() {
		return new HashSet<UserCriteria>();
	}
	
	public static Set<UserCriteria> createUserCriteria_Set() {
		return createUserCriteria_Set(2);
	}
	
	public static Set<UserCriteria> createUserCriteria_Set(int size) {
		return createUserCriteria_Set(userCriteriaCount++, size);
	}
	
	public static Set<UserCriteria> createUserCriteria_Set(long index, int size) {
		Set<UserCriteria> userCriteriaSet = createEmptyUserCriteria_Set();
		long limit = index + size;
		for (; index < limit; index++)
			userCriteriaSet.add(createUserCriteria(index));
		return userCriteriaSet;
	}
	
	public static void assertUserCriteriaExists(Collection<UserCriteria> userCriteriaList, UserCriteria userCriteria) {
		Assert.notNull(userCriteriaList, "UserCriteria list must be specified");
		Assert.notNull(userCriteria, "UserCriteria record must be specified");
		Iterator<UserCriteria> iterator = userCriteriaList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			UserCriteria userCriteria1 = iterator.next();
			if (userCriteria1.equals(userCriteria)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - UserCriteria should exist: "+userCriteria);
	}
	
	public static void assertUserCriteriaCorrect(UserCriteria userCriteria) {
		assertObjectCorrect("Enabled", userCriteria.getEnabled());
		assertObjectCorrect("UserName", userCriteria.getUserName());
		CommonFixture.assertPersonNameCorrect(userCriteria.getPersonName());
		CommonFixture.assertEmailAddressCorrect(userCriteria.getEmailAddress());
		CommonFixture.assertStreetAddressCorrect(userCriteria.getStreetAddress());
		assertRoleCorrect(userCriteria.getRoles());
	}
	
	public static void assertUserCriteriaCorrect(Collection<UserCriteria> userCriteriaList) {
		Assert.isTrue(userCriteriaList.size() == 2, "UserCriteria count not correct");
		Iterator<UserCriteria> iterator = userCriteriaList.iterator();
		while (iterator.hasNext()) {
			UserCriteria userCriteria = iterator.next();
			assertUserCriteriaCorrect(userCriteria);
		}
	}
	
	public static Role createEmptyRole() {
		Role role = new Role();
		return role;
	}
	
	public static Role createRole() {
		Role role = createRole(roleCount++);
		return role;
	}
	
	public static Role createRole(long index) {
		Role role = createEmptyRole();
		role.setName("dummyName" + index);
		role.setRoleType(RoleType.USER);
		role.setConditional(true);
		role.setEnabled(true);
		role.getGroups().addAll(createEmptyRole_List());
		role.getPermissions().addAll(createPermission_List(2));
		role.setId(10L * index);
		return role;
	}
	
	public static List<Role> createEmptyRole_List() {
		return new ArrayList<Role>();
	}
	
	public static List<Role> createRole_List() {
		return createRole_List(2);
	}
	
	public static List<Role> createRole_List(int size) {
		return createRole_List(roleCount++, size);
	}
	
	public static List<Role> createRole_List(long index, int size) {
		List<Role> roleList = createEmptyRole_List();
		long limit = index + size;
		for (; index < limit; index++)
			roleList.add(createRole(index));
		return roleList;
	}
	
	public static Set<Role> createEmptyRole_Set() {
		return new HashSet<Role>();
	}
	
	public static Set<Role> createRole_Set() {
		return createRole_Set(2);
	}
	
	public static Set<Role> createRole_Set(int size) {
		return createRole_Set(roleCount++, size);
	}
	
	public static Set<Role> createRole_Set(long index, int size) {
		Set<Role> roleSet = createEmptyRole_Set();
		long limit = index + size;
		for (; index < limit; index++)
			roleSet.add(createRole(index));
		return roleSet;
	}
	
	public static void assertRoleExists(Collection<Role> roleList, Role role) {
		Assert.notNull(roleList, "Role list must be specified");
		Assert.notNull(role, "Role record must be specified");
		Iterator<Role> iterator = roleList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Role role1 = iterator.next();
			if (role1.equals(role)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Role should exist: "+role);
	}
	
	public static void assertRoleCorrect(Role role) {
		long index = role.getId() / 10L;
		assertObjectCorrect("Name", role.getName(), index);
		assertObjectCorrect("RoleType", role.getRoleType(), index);
		assertObjectCorrect("Conditional", role.getConditional(), index);
		assertObjectCorrect("Enabled", role.getEnabled(), index);
		assertRoleCorrect(role.getGroups());
		assertPermissionCorrect(role.getPermissions());
	}
	
	public static void assertRoleCorrect(Collection<Role> roleList) {
		Assert.isTrue(roleList.size() == 2, "Role count not correct");
		Iterator<Role> iterator = roleList.iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			assertRoleCorrect(role);
		}
	}
	
	public static void assertSameRole(Role role1, Role role2) {
		assertSameRole(role1, role2, "");
	}
	
	public static void assertSameRole(Role role1, Role role2, String message) {
		assertObjectExists("Role1", role1);
		assertObjectExists("Role2", role2);
		assertObjectEquals("Name", role1.getName(), role2.getName(), message);
		assertObjectEquals("RoleType", role1.getRoleType(), role2.getRoleType(), message);
		assertObjectEquals("Conditional", role1.getConditional(), role2.getConditional(), message);
		assertObjectEquals("Enabled", role1.getEnabled(), role2.getEnabled(), message);
		assertSameRole(role1.getGroups(), role2.getGroups(), message);
		assertSamePermission(role1.getPermissions(), role2.getPermissions(), message);
	}
	
	public static void assertSameRole(Collection<Role> roleList1, Collection<Role> roleList2) {
		assertSameRole(roleList1, roleList2, "");
	}
	
	public static void assertSameRole(Collection<Role> roleList1, Collection<Role> roleList2, String message) {
		Assert.notNull(roleList1, "Role list1 must be specified");
		Assert.notNull(roleList2, "Role list2 must be specified");
		Assert.equals(roleList1.size(), roleList2.size(), "Role count not equal");
		Collection<Role> sortedRecords1 = RoleUtil.sortRecords(roleList1);
		Collection<Role> sortedRecords2 = RoleUtil.sortRecords(roleList2);
		Iterator<Role> list1Iterator = sortedRecords1.iterator();
		Iterator<Role> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Role role1 = list1Iterator.next();
			Role role2 = list2Iterator.next();
			assertSameRole(role1, role2, message);
		}
	}
	
	public static Permission createEmptyPermission() {
		Permission permission = new Permission();
		return permission;
	}
	
	public static Permission createPermission() {
		Permission permission = createPermission(permissionCount++);
		return permission;
	}
	
	public static Permission createPermission(long index) {
		Permission permission = createEmptyPermission();
		permission.setTarget("dummyTarget" + index);
		permission.setOrganization("dummyOrganization" + index);
		permission.getActions().add(Action.ALL);
		permission.setEnabled(true);
		permission.setId(10L * index);
		return permission;
	}
	
	public static List<Permission> createEmptyPermission_List() {
		return new ArrayList<Permission>();
	}
	
	public static List<Permission> createPermission_List() {
		return createPermission_List(2);
	}
	
	public static List<Permission> createPermission_List(int size) {
		return createPermission_List(permissionCount++, size);
	}
	
	public static List<Permission> createPermission_List(long index, int size) {
		List<Permission> permissionList = createEmptyPermission_List();
		long limit = index + size;
		for (; index < limit; index++)
			permissionList.add(createPermission(index));
		return permissionList;
	}
	
	public static Set<Permission> createEmptyPermission_Set() {
		return new HashSet<Permission>();
	}
	
	public static Set<Permission> createPermission_Set() {
		return createPermission_Set(2);
	}
	
	public static Set<Permission> createPermission_Set(int size) {
		return createPermission_Set(permissionCount++, size);
	}
	
	public static Set<Permission> createPermission_Set(long index, int size) {
		Set<Permission> permissionSet = createEmptyPermission_Set();
		long limit = index + size;
		for (; index < limit; index++)
			permissionSet.add(createPermission(index));
		return permissionSet;
	}
	
	public static void assertPermissionExists(Collection<Permission> permissionList, Permission permission) {
		Assert.notNull(permissionList, "Permission list must be specified");
		Assert.notNull(permission, "Permission record must be specified");
		Iterator<Permission> iterator = permissionList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Permission permission1 = iterator.next();
			if (permission1.equals(permission)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Permission should exist: "+permission);
	}
	
	public static void assertPermissionCorrect(Permission permission) {
		long index = permission.getId() / 10L;
		assertObjectCorrect("Target", permission.getTarget(), index);
		assertObjectCorrect("Organization", permission.getOrganization(), index);
		assertObjectCorrect("Actions", permission.getActions(), index);
		assertObjectCorrect("Enabled", permission.getEnabled(), index);
	}
	
	public static void assertPermissionCorrect(Collection<Permission> permissionList) {
		Assert.isTrue(permissionList.size() == 2, "Permission count not correct");
		Iterator<Permission> iterator = permissionList.iterator();
		while (iterator.hasNext()) {
			Permission permission = iterator.next();
			assertPermissionCorrect(permission);
		}
	}
	
	public static void assertSamePermission(Permission permission1, Permission permission2) {
		assertSamePermission(permission1, permission2, "");
	}
	
	public static void assertSamePermission(Permission permission1, Permission permission2, String message) {
		assertObjectExists("Permission1", permission1);
		assertObjectExists("Permission2", permission2);
		assertObjectEquals("Target", permission1.getTarget(), permission2.getTarget(), message);
		assertObjectEquals("Organization", permission1.getOrganization(), permission2.getOrganization(), message);
		assertObjectEquals("Actions", permission1.getActions(), permission2.getActions(), message);
		assertObjectEquals("Enabled", permission1.getEnabled(), permission2.getEnabled(), message);
	}
	
	public static void assertSamePermission(Collection<Permission> permissionList1, Collection<Permission> permissionList2) {
		assertSamePermission(permissionList1, permissionList2, "");
	}
	
	public static void assertSamePermission(Collection<Permission> permissionList1, Collection<Permission> permissionList2, String message) {
		Assert.notNull(permissionList1, "Permission list1 must be specified");
		Assert.notNull(permissionList2, "Permission list2 must be specified");
		Assert.equals(permissionList1.size(), permissionList2.size(), "Permission count not equal");
		Collection<Permission> sortedRecords1 = PermissionUtil.sortRecords(permissionList1);
		Collection<Permission> sortedRecords2 = PermissionUtil.sortRecords(permissionList2);
		Iterator<Permission> list1Iterator = sortedRecords1.iterator();
		Iterator<Permission> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Permission permission1 = list1Iterator.next();
			Permission permission2 = list2Iterator.next();
			assertSamePermission(permission1, permission2, message);
		}
	}
	
	public static Preferences createEmptyPreferences() {
		Preferences preferences = new Preferences();
		return preferences;
	}
	
	public static Preferences createPreferences(User user) {
		Preferences preferences = createPreferences(user, preferencesCount++);
		return preferences;
	}
	
	public static Preferences createPreferences(User user, long index) {
		Preferences preferences = createEmptyPreferences();
		preferences.setThemeId("dummyThemeId" + index);
		preferences.getWorkState().put("dummyWorkState" + index, "dummyWorkStateValue");
		preferences.getOpenNodes().put("dummyOpenNodes" + index, false);
		preferences.setSelectedNode(1L + (long) index);
		preferences.setEnableTooltips(false);
		preferences.setUser(user);
		preferences.setId(10L * index);
		return preferences;
	}
	
	public static List<Preferences> createEmptyPreferences_List() {
		return new ArrayList<Preferences>();
	}
	
	public static List<Preferences> createPreferences_List(User user) {
		return createPreferences_List(user, 2);
	}
	
	public static List<Preferences> createPreferences_List(User user, int size) {
		return createPreferences_List(user, preferencesCount++, size);
	}
	
	public static List<Preferences> createPreferences_List(User user, long index, int size) {
		List<Preferences> preferencesList = createEmptyPreferences_List();
		long limit = index + size;
		for (; index < limit; index++)
			preferencesList.add(createPreferences(user, index));
		return preferencesList;
	}
	
	public static Set<Preferences> createEmptyPreferences_Set() {
		return new HashSet<Preferences>();
	}
	
	public static Set<Preferences> createPreferences_Set(User user) {
		return createPreferences_Set(user, 2);
	}
	
	public static Set<Preferences> createPreferences_Set(User user, int size) {
		return createPreferences_Set(user, preferencesCount++, size);
	}
	
	public static Set<Preferences> createPreferences_Set(User user, long index, int size) {
		Set<Preferences> preferencesSet = createEmptyPreferences_Set();
		long limit = index + size;
		for (; index < limit; index++)
			preferencesSet.add(createPreferences(user, index));
		return preferencesSet;
	}
	
	public static void assertPreferencesExists(Collection<Preferences> preferencesList, Preferences preferences) {
		Assert.notNull(preferencesList, "Preferences list must be specified");
		Assert.notNull(preferences, "Preferences record must be specified");
		Iterator<Preferences> iterator = preferencesList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Preferences preferences1 = iterator.next();
			if (preferences1.equals(preferences)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Preferences should exist: "+preferences);
	}
	
	public static void assertPreferencesCorrect(Preferences preferences) {
		long index = preferences.getId() / 10L;
		assertObjectCorrect("ThemeId", preferences.getThemeId(), index);
		assertObjectCorrect("WorkState", preferences.getWorkState(), index);
		assertObjectCorrect("OpenNodes", preferences.getOpenNodes(), index);
		assertObjectCorrect("SelectedNode", preferences.getSelectedNode(), index);
		assertObjectCorrect("EnableTooltips", preferences.getEnableTooltips(), index);
		assertUserCorrect(preferences.getUser());
	}
	
	public static void assertPreferencesCorrect(Collection<Preferences> preferencesList) {
		Assert.isTrue(preferencesList.size() == 2, "Preferences count not correct");
		Iterator<Preferences> iterator = preferencesList.iterator();
		while (iterator.hasNext()) {
			Preferences preferences = iterator.next();
			assertPreferencesCorrect(preferences);
		}
	}
	
	public static void assertSamePreferences(Preferences preferences1, Preferences preferences2) {
		assertSamePreferences(preferences1, preferences2, "");
	}
	
	public static void assertSamePreferences(Preferences preferences1, Preferences preferences2, String message) {
		assertObjectExists("Preferences1", preferences1);
		assertObjectExists("Preferences2", preferences2);
		assertObjectEquals("ThemeId", preferences1.getThemeId(), preferences2.getThemeId(), message);
		assertObjectEquals("OpenNodes", preferences1.getOpenNodes(), preferences2.getOpenNodes(), message);
		assertObjectEquals("SelectedNode", preferences1.getSelectedNode(), preferences2.getSelectedNode(), message);
		assertObjectEquals("EnableTooltips", preferences1.getEnableTooltips(), preferences2.getEnableTooltips(), message);
		assertSameUser(preferences1.getUser(), preferences2.getUser(), message);
	}
	
	public static void assertSamePreferences(Collection<Preferences> preferencesList1, Collection<Preferences> preferencesList2) {
		assertSamePreferences(preferencesList1, preferencesList2, "");
	}
	
	public static void assertSamePreferences(Collection<Preferences> preferencesList1, Collection<Preferences> preferencesList2, String message) {
		Assert.notNull(preferencesList1, "Preferences list1 must be specified");
		Assert.notNull(preferencesList2, "Preferences list2 must be specified");
		Assert.equals(preferencesList1.size(), preferencesList2.size(), "Preferences count not equal");
		Collection<Preferences> sortedRecords1 = PreferencesUtil.sortRecords(preferencesList1);
		Collection<Preferences> sortedRecords2 = PreferencesUtil.sortRecords(preferencesList2);
		Iterator<Preferences> list1Iterator = sortedRecords1.iterator();
		Iterator<Preferences> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Preferences preferences1 = list1Iterator.next();
			Preferences preferences2 = list2Iterator.next();
			assertSamePreferences(preferences1, preferences2, message);
		}
	}
	
	public static Registration createEmptyRegistration() {
		Registration registration = new Registration();
		return registration;
	}
	
	public static Registration createRegistration() {
		Registration registration = createRegistration(registrationCount++);
		return registration;
	}
	
	public static Registration createRegistration(long index) {
		Registration registration = createEmptyRegistration();
		registration.setEnabled(true);
		registration.setLoginCount(1L + (long) index);
		registration.setUser(createUser(index));
		registration.setId(10L * index);
		return registration;
	}
	
	public static List<Registration> createEmptyRegistration_List() {
		return new ArrayList<Registration>();
	}
	
	public static List<Registration> createRegistration_List() {
		return createRegistration_List(2);
	}
	
	public static List<Registration> createRegistration_List(int size) {
		return createRegistration_List(registrationCount++, size);
	}
	
	public static List<Registration> createRegistration_List(long index, int size) {
		List<Registration> registrationList = createEmptyRegistration_List();
		long limit = index + size;
		for (; index < limit; index++)
			registrationList.add(createRegistration(index));
		return registrationList;
	}
	
	public static Set<Registration> createEmptyRegistration_Set() {
		return new HashSet<Registration>();
	}
	
	public static Set<Registration> createRegistration_Set() {
		return createRegistration_Set(2);
	}
	
	public static Set<Registration> createRegistration_Set(int size) {
		return createRegistration_Set(registrationCount++, size);
	}
	
	public static Set<Registration> createRegistration_Set(long index, int size) {
		Set<Registration> registrationSet = createEmptyRegistration_Set();
		long limit = index + size;
		for (; index < limit; index++)
			registrationSet.add(createRegistration(index));
		return registrationSet;
	}
	
	public static void assertRegistrationExists(Collection<Registration> registrationList, Registration registration) {
		Assert.notNull(registrationList, "Registration list must be specified");
		Assert.notNull(registration, "Registration record must be specified");
		Iterator<Registration> iterator = registrationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Registration registration1 = iterator.next();
			if (registration1.equals(registration)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Registration should exist: "+registration);
	}
	
	public static void assertRegistrationCorrect(Registration registration) {
		long index = registration.getId() / 10L;
		assertObjectCorrect("Enabled", registration.getEnabled(), index);
		assertObjectCorrect("LoginCount", registration.getLoginCount(), index);
		assertUserCorrect(registration.getUser());
	}
	
	public static void assertRegistrationCorrect(Collection<Registration> registrationList) {
		Assert.isTrue(registrationList.size() == 2, "Registration count not correct");
		Iterator<Registration> iterator = registrationList.iterator();
		while (iterator.hasNext()) {
			Registration registration = iterator.next();
			assertRegistrationCorrect(registration);
		}
	}
	
	public static void assertSameRegistration(Registration registration1, Registration registration2) {
		assertSameRegistration(registration1, registration2, "");
	}
	
	public static void assertSameRegistration(Registration registration1, Registration registration2, String message) {
		assertObjectExists("Registration1", registration1);
		assertObjectExists("Registration2", registration2);
		assertObjectEquals("Enabled", registration1.getEnabled(), registration2.getEnabled(), message);
		assertObjectEquals("LoginCount", registration1.getLoginCount(), registration2.getLoginCount(), message);
		assertSameUser(registration1.getUser(), registration2.getUser(), message);
	}
	
	public static void assertSameRegistration(Collection<Registration> registrationList1, Collection<Registration> registrationList2) {
		assertSameRegistration(registrationList1, registrationList2, "");
	}
	
	public static void assertSameRegistration(Collection<Registration> registrationList1, Collection<Registration> registrationList2, String message) {
		Assert.notNull(registrationList1, "Registration list1 must be specified");
		Assert.notNull(registrationList2, "Registration list2 must be specified");
		Assert.equals(registrationList1.size(), registrationList2.size(), "Registration count not equal");
		Collection<Registration> sortedRecords1 = RegistrationUtil.sortRecords(registrationList1);
		Collection<Registration> sortedRecords2 = RegistrationUtil.sortRecords(registrationList2);
		Iterator<Registration> list1Iterator = sortedRecords1.iterator();
		Iterator<Registration> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Registration registration1 = list1Iterator.next();
			Registration registration2 = list2Iterator.next();
			assertSameRegistration(registration1, registration2, message);
		}
	}
	
	public static Skin createEmptySkin() {
		Skin skin = new Skin();
		return skin;
	}
	
	public static Skin createSkin() {
		Skin skin = createSkin(skinCount++);
		return skin;
	}
	
	public static Skin createSkin(long index) {
		Skin skin = createEmptySkin();
		skin.setName("dummyName" + index);
		skin.setWidth("dummyWidth" + index);
		skin.setHeight("dummyHeight" + index);
		skin.setMargin("dummyMargin" + index);
		skin.setMarginTop("dummyMarginTop" + index);
		skin.setMarginLeft("dummyMarginLeft" + index);
		skin.setMarginRight("dummyMarginRight" + index);
		skin.setMarginBottom("dummyMarginBottom" + index);
		skin.setPadding("dummyPadding" + index);
		skin.setPaddingTop("dummyPaddingTop" + index);
		skin.setPaddingLeft("dummyPaddingLeft" + index);
		skin.setPaddingRight("dummyPaddingRight" + index);
		skin.setPaddingBottom("dummyPaddingBottom" + index);
		skin.setBorderStyle("dummyBorderStyle" + index);
		skin.setBorderWidth("dummyBorderWidth" + index);
		skin.setBorderColor("dummyBorderColor" + index);
		skin.setCornerRadius("dummyCornerRadius" + index);
		skin.setBackgroundColor("dummyBackgroundColor" + index);
		skin.setBackgroundImage("dummyBackgroundImage" + index);
		skin.setGradientColor("dummyGradientColor" + index);
		skin.setGradientImage("dummyGradientImage" + index);
		skin.setFontSize("dummyFontSize" + index);
		skin.setFontFamily("dummyFontFamily" + index);
		skin.setFontWeight("dummyFontWeight" + index);
		skin.setFontStyle("dummyFontStyle" + index);
		skin.setFontVariant("dummyFontVariant" + index);
		skin.setTextColor("dummyTextColor" + index);
		skin.setTextDecoration("dummyTextDecoration" + index);
		skin.setLinkColor("dummyLinkColor" + index);
		skin.setLinkHoverColor("dummyLinkHoverColor" + index);
		skin.setLinkVisitedColor("dummyLinkVisitedColor" + index);
		skin.setButtonFontFamily("dummyButtonFontFamily" + index);
		skin.setButtonFontSize("dummyButtonFontSize" + index);
		skin.setButtonFontStyle("dummyButtonFontStyle" + index);
		skin.setButtonFontVariant("dummyButtonFontVariant" + index);
		skin.setButtonFontWeight("dummyButtonFontWeight" + index);
		skin.setButtonTextColor("dummyButtonTextColor" + index);
		skin.setButtonTextDecoration("dummyButtonTextDecoration" + index);
		skin.setButtonBackgroundColor("dummyButtonBackgroundColor" + index);
		skin.setButtonBackgroundImage("dummyButtonBackgroundImage" + index);
		skin.setButtonGradientColor("dummyButtonGradientColor" + index);
		skin.setButtonBorderStyle("dummyButtonBorderStyle" + index);
		skin.setButtonBorderWidth("dummyButtonBorderWidth" + index);
		skin.setButtonBorderColor("dummyButtonBorderColor" + index);
		skin.setButtonControlColor("dummyButtonControlColor" + index);
		skin.setButtonCornerRadius("dummyButtonCornerRadius" + index);
		skin.setHeaderBackgroundColor("dummyHeaderBackgroundColor" + index);
		skin.setHeaderBackgroundImage("dummyHeaderBackgroundImage" + index);
		skin.setHeaderGradientColor("dummyHeaderGradientColor" + index);
		skin.setHeaderBorderStyle("dummyHeaderBorderStyle" + index);
		skin.setHeaderBorderWidth("dummyHeaderBorderWidth" + index);
		skin.setHeaderBorderColor("dummyHeaderBorderColor" + index);
		skin.setHeaderFontFamily("dummyHeaderFontFamily" + index);
		skin.setHeaderFontSize("dummyHeaderFontSize" + index);
		skin.setHeaderFontStyle("dummyHeaderFontStyle" + index);
		skin.setHeaderFontVariant("dummyHeaderFontVariant" + index);
		skin.setHeaderFontWeight("dummyHeaderFontWeight" + index);
		skin.setHeaderTextColor("dummyHeaderTextColor" + index);
		skin.setHeaderTextDecoration("dummyHeaderTextDecoration" + index);
		skin.setToolbarBackgroundColor("dummyToolbarBackgroundColor" + index);
		skin.setToolbarBackgroundImage("dummyToolbarBackgroundImage" + index);
		skin.setToolbarGradientColor("dummyToolbarGradientColor" + index);
		skin.setToolbarBorderColor("dummyToolbarBorderColor" + index);
		skin.setToolbarBorderStyle("dummyToolbarBorderStyle" + index);
		skin.setToolbarBorderWidth("dummyToolbarBorderWidth" + index);
		skin.setToolbarFontFamily("dummyToolbarFontFamily" + index);
		skin.setToolbarFontSize("dummyToolbarFontSize" + index);
		skin.setToolbarFontStyle("dummyToolbarFontStyle" + index);
		skin.setToolbarFontVariant("dummyToolbarFontVariant" + index);
		skin.setToolbarFontWeight("dummyToolbarFontWeight" + index);
		skin.setToolbarTextColor("dummyToolbarTextColor" + index);
		skin.setToolbarTextDecoration("dummyToolbarTextDecoration" + index);
		skin.setTabBackgroundColor("dummyTabBackgroundColor" + index);
		skin.setTabBackgroundImage("dummyTabBackgroundImage" + index);
		skin.setTabGradientColor("dummyTabGradientColor" + index);
		skin.setTabBorderColor("dummyTabBorderColor" + index);
		skin.setTabCornerRadius("dummyTabCornerRadius" + index);
		skin.setTabFontFamily("dummyTabFontFamily" + index);
		skin.setTabFontSize("dummyTabFontSize" + index);
		skin.setTabFontStyle("dummyTabFontStyle" + index);
		skin.setTabFontWeight("dummyTabFontWeight" + index);
		skin.setTabTextColor("dummyTabTextColor" + index);
		skin.setTabTextDecoration("dummyTabTextDecoration" + index);
		skin.setTabActiveBackgroundColor("dummyTabActiveBackgroundColor" + index);
		skin.setTabActiveBackgroundImage("dummyTabActiveBackgroundImage" + index);
		skin.setTabActiveGradientColor("dummyTabActiveGradientColor" + index);
		skin.setTabActiveBorderColor("dummyTabActiveBorderColor" + index);
		skin.setTabActiveFontFamily("dummyTabActiveFontFamily" + index);
		skin.setTabActiveFontSize("dummyTabActiveFontSize" + index);
		skin.setTabActiveFontStyle("dummyTabActiveFontStyle" + index);
		skin.setTabActiveFontWeight("dummyTabActiveFontWeight" + index);
		skin.setTabActiveTextColor("dummyTabActiveTextColor" + index);
		skin.setTabActiveTextDecoration("dummyTabActiveTextDecoration" + index);
		skin.setTabInactiveBackgroundColor("dummyTabInactiveBackgroundColor" + index);
		skin.setTabInactiveBackgroundImage("dummyTabInactiveBackgroundImage" + index);
		skin.setTabInactiveGradientColor("dummyTabInactiveGradientColor" + index);
		skin.setTabInactiveBorderColor("dummyTabInactiveBorderColor" + index);
		skin.setTabInactiveFontFamily("dummyTabInactiveFontFamily" + index);
		skin.setTabInactiveFontSize("dummyTabInactiveFontSize" + index);
		skin.setTabInactiveFontStyle("dummyTabInactiveFontStyle" + index);
		skin.setTabInactiveFontWeight("dummyTabInactiveFontWeight" + index);
		skin.setTabInactiveTextColor("dummyTabInactiveTextColor" + index);
		skin.setTabInactiveTextDecoration("dummyTabInactiveTextDecoration" + index);
		skin.setTabDisabledBackgroundColor("dummyTabDisabledBackgroundColor" + index);
		skin.setTabDisabledBackgroundImage("dummyTabDisabledBackgroundImage" + index);
		skin.setTabDisabledGradientColor("dummyTabDisabledGradientColor" + index);
		skin.setTabDisabledBorderColor("dummyTabDisabledBorderColor" + index);
		skin.setTabDisabledFontFamily("dummyTabDisabledFontFamily" + index);
		skin.setTabDisabledFontSize("dummyTabDisabledFontSize" + index);
		skin.setTabDisabledFontStyle("dummyTabDisabledFontStyle" + index);
		skin.setTabDisabledFontWeight("dummyTabDisabledFontWeight" + index);
		skin.setTabDisabledTextColor("dummyTabDisabledTextColor" + index);
		skin.setTabDisabledTextDecoration("dummyTabDisabledTextDecoration" + index);
		skin.setTableBackgroundColor("dummyTableBackgroundColor" + index);
		skin.setTableBorderColor("dummyTableBorderColor" + index);
		skin.setTableBorderWidth("dummyTableBorderWidth" + index);
		skin.setTableCellBackgroundColor("dummyTableCellBackgroundColor" + index);
		skin.setTableCellBackgroundImage("dummyTableCellBackgroundImage" + index);
		skin.setTableCellGradientColor("dummyTableCellGradientColor" + index);
		skin.setTableCellBorderColor("dummyTableCellBorderColor" + index);
		skin.setTableCellFontFamily("dummyTableCellFontFamily" + index);
		skin.setTableCellFontSize("dummyTableCellFontSize" + index);
		skin.setTableCellFontStyle("dummyTableCellFontStyle" + index);
		skin.setTableCellFontVariant("dummyTableCellFontVariant" + index);
		skin.setTableCellFontWeight("dummyTableCellFontWeight" + index);
		skin.setTableCellTextColor("dummyTableCellTextColor" + index);
		skin.setTableCellTextDecoration("dummyTableCellTextDecoration" + index);
		skin.setTableHeaderBackgroundColor("dummyTableHeaderBackgroundColor" + index);
		skin.setTableHeaderBackgroundImage("dummyTableHeaderBackgroundImage" + index);
		skin.setTableHeaderGradientColor("dummyTableHeaderGradientColor" + index);
		skin.setTableHeaderBorderColor("dummyTableHeaderBorderColor" + index);
		skin.setTableHeaderFontFamily("dummyTableHeaderFontFamily" + index);
		skin.setTableHeaderFontSize("dummyTableHeaderFontSize" + index);
		skin.setTableHeaderFontStyle("dummyTableHeaderFontStyle" + index);
		skin.setTableHeaderFontVariant("dummyTableHeaderFontVariant" + index);
		skin.setTableHeaderFontWeight("dummyTableHeaderFontWeight" + index);
		skin.setTableHeaderTextColor("dummyTableHeaderTextColor" + index);
		skin.setTableHeaderTextDecoration("dummyTableHeaderTextDecoration" + index);
		skin.setTableFooterBackgroundColor("dummyTableFooterBackgroundColor" + index);
		skin.setTableFooterBackgroundImage("dummyTableFooterBackgroundImage" + index);
		skin.setTableFooterGradientColor("dummyTableFooterGradientColor" + index);
		skin.setTableFooterBorderColor("dummyTableFooterBorderColor" + index);
		skin.setTableFooterFontFamily("dummyTableFooterFontFamily" + index);
		skin.setTableFooterFontSize("dummyTableFooterFontSize" + index);
		skin.setTableFooterFontStyle("dummyTableFooterFontStyle" + index);
		skin.setTableFooterFontVariant("dummyTableFooterFontVariant" + index);
		skin.setTableFooterFontWeight("dummyTableFooterFontWeight" + index);
		skin.setTableFooterTextColor("dummyTableFooterTextColor" + index);
		skin.setTableFooterTextDecoration("dummyTableFooterTextDecoration" + index);
		skin.setTableSubHeaderBackgroundColor("dummyTableSubHeaderBackgroundColor" + index);
		skin.setTableSubFooterBackgroundColor("dummyTableSubFooterBackgroundColor" + index);
		skin.setTooltipBackgroundColor("dummyTooltipBackgroundColor" + index);
		skin.setTooltipBackgroundImage("dummyTooltipBackgroundImage" + index);
		skin.setTooltipGradientColor("dummyTooltipGradientColor" + index);
		skin.setTooltipBorderColor("dummyTooltipBorderColor" + index);
		skin.setTooltipBorderStyle("dummyTooltipBorderStyle" + index);
		skin.setTooltipBorderWidth("dummyTooltipBorderWidth" + index);
		skin.setTooltipFontFamily("dummyTooltipFontFamily" + index);
		skin.setTooltipFontSize("dummyTooltipFontSize" + index);
		skin.setTooltipFontStyle("dummyTooltipFontStyle" + index);
		skin.setTooltipFontVariant("dummyTooltipFontVariant" + index);
		skin.setTooltipFontWeight("dummyTooltipFontWeight" + index);
		skin.setTooltipTextColor("dummyTooltipTextColor" + index);
		skin.setTooltipTextDecoration("dummyTooltipTextDecoration" + index);
		skin.setHighlightBackgroundColor("dummyHighlightBackgroundColor" + index);
		skin.setHighlightBackgroundImage("dummyHighlightBackgroundImage" + index);
		skin.setHighlightBorderStyle("dummyHighlightBorderStyle" + index);
		skin.setHighlightBorderWidth("dummyHighlightBorderWidth" + index);
		skin.setHighlightBorderColor("dummyHighlightBorderColor" + index);
		skin.setHighlightFontFamily("dummyHighlightFontFamily" + index);
		skin.setHighlightFontSize("dummyHighlightFontSize" + index);
		skin.setHighlightFontStyle("dummyHighlightFontStyle" + index);
		skin.setHighlightFontWeight("dummyHighlightFontWeight" + index);
		skin.setHighlightTextColor("dummyHighlightTextColor" + index);
		skin.setHighlightTextDecoration("dummyHighlightTextDecoration" + index);
		skin.setHighlightControlColor("dummyHighlightControlColor" + index);
		skin.setFocusBackgroundColor("dummyFocusBackgroundColor" + index);
		skin.setFocusBackgroundImage("dummyFocusBackgroundImage" + index);
		skin.setFocusGradientColor("dummyFocusGradientColor" + index);
		skin.setFocusBorderStyle("dummyFocusBorderStyle" + index);
		skin.setFocusBorderWidth("dummyFocusBorderWidth" + index);
		skin.setFocusBorderColor("dummyFocusBorderColor" + index);
		skin.setFocusFontFamily("dummyFocusFontFamily" + index);
		skin.setFocusFontSize("dummyFocusFontSize" + index);
		skin.setFocusFontStyle("dummyFocusFontStyle" + index);
		skin.setFocusFontWeight("dummyFocusFontWeight" + index);
		skin.setFocusTextColor("dummyFocusTextColor" + index);
		skin.setFocusTextDecoration("dummyFocusTextDecoration" + index);
		skin.setFocusControlColor("dummyFocusControlColor" + index);
		skin.setDisabledBackgroundColor("dummyDisabledBackgroundColor" + index);
		skin.setDisabledBackgroundImage("dummyDisabledBackgroundImage" + index);
		skin.setDisabledBorderStyle("dummyDisabledBorderStyle" + index);
		skin.setDisabledBorderWidth("dummyDisabledBorderWidth" + index);
		skin.setDisabledBorderColor("dummyDisabledBorderColor" + index);
		skin.setDisabledFontFamily("dummyDisabledFontFamily" + index);
		skin.setDisabledFontSize("dummyDisabledFontSize" + index);
		skin.setDisabledFontStyle("dummyDisabledFontStyle" + index);
		skin.setDisabledFontWeight("dummyDisabledFontWeight" + index);
		skin.setDisabledTextColor("dummyDisabledTextColor" + index);
		skin.setDisabledTextDecoration("dummyDisabledTextDecoration" + index);
		skin.setDisabledControlColor("dummyDisabledControlColor" + index);
		skin.setThemeBorderColor("dummyThemeBorderColor" + index);
		skin.setThemeBackgroundColor("dummyThemeBackgroundColor" + index);
		skin.setId(10L * index);
		return skin;
	}
	
	public static List<Skin> createEmptySkin_List() {
		return new ArrayList<Skin>();
	}
	
	public static List<Skin> createSkin_List() {
		return createSkin_List(2);
	}
	
	public static List<Skin> createSkin_List(int size) {
		return createSkin_List(skinCount++, size);
	}
	
	public static List<Skin> createSkin_List(long index, int size) {
		List<Skin> skinList = createEmptySkin_List();
		long limit = index + size;
		for (; index < limit; index++)
			skinList.add(createSkin(index));
		return skinList;
	}
	
	public static Set<Skin> createEmptySkin_Set() {
		return new HashSet<Skin>();
	}
	
	public static Set<Skin> createSkin_Set() {
		return createSkin_Set(2);
	}
	
	public static Set<Skin> createSkin_Set(int size) {
		return createSkin_Set(skinCount++, size);
	}
	
	public static Set<Skin> createSkin_Set(long index, int size) {
		Set<Skin> skinSet = createEmptySkin_Set();
		long limit = index + size;
		for (; index < limit; index++)
			skinSet.add(createSkin(index));
		return skinSet;
	}
	
	public static void assertSkinExists(Collection<Skin> skinList, Skin skin) {
		Assert.notNull(skinList, "Skin list must be specified");
		Assert.notNull(skin, "Skin record must be specified");
		Iterator<Skin> iterator = skinList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Skin skin1 = iterator.next();
			if (skin1.equals(skin)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Skin should exist: "+skin);
	}
	
	public static void assertSkinCorrect(Skin skin) {
		long index = skin.getId() / 10L;
		assertObjectCorrect("Name", skin.getName(), index);
		assertObjectCorrect("Width", skin.getWidth(), index);
		assertObjectCorrect("Height", skin.getHeight(), index);
		assertObjectCorrect("Margin", skin.getMargin(), index);
		assertObjectCorrect("MarginTop", skin.getMarginTop(), index);
		assertObjectCorrect("MarginLeft", skin.getMarginLeft(), index);
		assertObjectCorrect("MarginRight", skin.getMarginRight(), index);
		assertObjectCorrect("MarginBottom", skin.getMarginBottom(), index);
		assertObjectCorrect("Padding", skin.getPadding(), index);
		assertObjectCorrect("PaddingTop", skin.getPaddingTop(), index);
		assertObjectCorrect("PaddingLeft", skin.getPaddingLeft(), index);
		assertObjectCorrect("PaddingRight", skin.getPaddingRight(), index);
		assertObjectCorrect("PaddingBottom", skin.getPaddingBottom(), index);
		assertObjectCorrect("BorderStyle", skin.getBorderStyle(), index);
		assertObjectCorrect("BorderWidth", skin.getBorderWidth(), index);
		assertObjectCorrect("BorderColor", skin.getBorderColor(), index);
		assertObjectCorrect("CornerRadius", skin.getCornerRadius(), index);
		assertObjectCorrect("BackgroundColor", skin.getBackgroundColor(), index);
		assertObjectCorrect("BackgroundImage", skin.getBackgroundImage(), index);
		assertObjectCorrect("GradientColor", skin.getGradientColor(), index);
		assertObjectCorrect("GradientImage", skin.getGradientImage(), index);
		assertObjectCorrect("FontSize", skin.getFontSize(), index);
		assertObjectCorrect("FontFamily", skin.getFontFamily(), index);
		assertObjectCorrect("FontWeight", skin.getFontWeight(), index);
		assertObjectCorrect("FontStyle", skin.getFontStyle(), index);
		assertObjectCorrect("FontVariant", skin.getFontVariant(), index);
		assertObjectCorrect("TextColor", skin.getTextColor(), index);
		assertObjectCorrect("TextDecoration", skin.getTextDecoration(), index);
		assertObjectCorrect("LinkColor", skin.getLinkColor(), index);
		assertObjectCorrect("LinkHoverColor", skin.getLinkHoverColor(), index);
		assertObjectCorrect("LinkVisitedColor", skin.getLinkVisitedColor(), index);
		assertObjectCorrect("ButtonFontFamily", skin.getButtonFontFamily(), index);
		assertObjectCorrect("ButtonFontSize", skin.getButtonFontSize(), index);
		assertObjectCorrect("ButtonFontStyle", skin.getButtonFontStyle(), index);
		assertObjectCorrect("ButtonFontVariant", skin.getButtonFontVariant(), index);
		assertObjectCorrect("ButtonFontWeight", skin.getButtonFontWeight(), index);
		assertObjectCorrect("ButtonTextColor", skin.getButtonTextColor(), index);
		assertObjectCorrect("ButtonTextDecoration", skin.getButtonTextDecoration(), index);
		assertObjectCorrect("ButtonBackgroundColor", skin.getButtonBackgroundColor(), index);
		assertObjectCorrect("ButtonBackgroundImage", skin.getButtonBackgroundImage(), index);
		assertObjectCorrect("ButtonGradientColor", skin.getButtonGradientColor(), index);
		assertObjectCorrect("ButtonBorderStyle", skin.getButtonBorderStyle(), index);
		assertObjectCorrect("ButtonBorderWidth", skin.getButtonBorderWidth(), index);
		assertObjectCorrect("ButtonBorderColor", skin.getButtonBorderColor(), index);
		assertObjectCorrect("ButtonControlColor", skin.getButtonControlColor(), index);
		assertObjectCorrect("ButtonCornerRadius", skin.getButtonCornerRadius(), index);
		assertObjectCorrect("HeaderBackgroundColor", skin.getHeaderBackgroundColor(), index);
		assertObjectCorrect("HeaderBackgroundImage", skin.getHeaderBackgroundImage(), index);
		assertObjectCorrect("HeaderGradientColor", skin.getHeaderGradientColor(), index);
		assertObjectCorrect("HeaderBorderStyle", skin.getHeaderBorderStyle(), index);
		assertObjectCorrect("HeaderBorderWidth", skin.getHeaderBorderWidth(), index);
		assertObjectCorrect("HeaderBorderColor", skin.getHeaderBorderColor(), index);
		assertObjectCorrect("HeaderFontFamily", skin.getHeaderFontFamily(), index);
		assertObjectCorrect("HeaderFontSize", skin.getHeaderFontSize(), index);
		assertObjectCorrect("HeaderFontStyle", skin.getHeaderFontStyle(), index);
		assertObjectCorrect("HeaderFontVariant", skin.getHeaderFontVariant(), index);
		assertObjectCorrect("HeaderFontWeight", skin.getHeaderFontWeight(), index);
		assertObjectCorrect("HeaderTextColor", skin.getHeaderTextColor(), index);
		assertObjectCorrect("HeaderTextDecoration", skin.getHeaderTextDecoration(), index);
		assertObjectCorrect("ToolbarBackgroundColor", skin.getToolbarBackgroundColor(), index);
		assertObjectCorrect("ToolbarBackgroundImage", skin.getToolbarBackgroundImage(), index);
		assertObjectCorrect("ToolbarGradientColor", skin.getToolbarGradientColor(), index);
		assertObjectCorrect("ToolbarBorderColor", skin.getToolbarBorderColor(), index);
		assertObjectCorrect("ToolbarBorderStyle", skin.getToolbarBorderStyle(), index);
		assertObjectCorrect("ToolbarBorderWidth", skin.getToolbarBorderWidth(), index);
		assertObjectCorrect("ToolbarFontFamily", skin.getToolbarFontFamily(), index);
		assertObjectCorrect("ToolbarFontSize", skin.getToolbarFontSize(), index);
		assertObjectCorrect("ToolbarFontStyle", skin.getToolbarFontStyle(), index);
		assertObjectCorrect("ToolbarFontVariant", skin.getToolbarFontVariant(), index);
		assertObjectCorrect("ToolbarFontWeight", skin.getToolbarFontWeight(), index);
		assertObjectCorrect("ToolbarTextColor", skin.getToolbarTextColor(), index);
		assertObjectCorrect("ToolbarTextDecoration", skin.getToolbarTextDecoration(), index);
		assertObjectCorrect("TabBackgroundColor", skin.getTabBackgroundColor(), index);
		assertObjectCorrect("TabBackgroundImage", skin.getTabBackgroundImage(), index);
		assertObjectCorrect("TabGradientColor", skin.getTabGradientColor(), index);
		assertObjectCorrect("TabBorderColor", skin.getTabBorderColor(), index);
		assertObjectCorrect("TabCornerRadius", skin.getTabCornerRadius(), index);
		assertObjectCorrect("TabFontFamily", skin.getTabFontFamily(), index);
		assertObjectCorrect("TabFontSize", skin.getTabFontSize(), index);
		assertObjectCorrect("TabFontStyle", skin.getTabFontStyle(), index);
		assertObjectCorrect("TabFontWeight", skin.getTabFontWeight(), index);
		assertObjectCorrect("TabTextColor", skin.getTabTextColor(), index);
		assertObjectCorrect("TabTextDecoration", skin.getTabTextDecoration(), index);
		assertObjectCorrect("TabActiveBackgroundColor", skin.getTabActiveBackgroundColor(), index);
		assertObjectCorrect("TabActiveBackgroundImage", skin.getTabActiveBackgroundImage(), index);
		assertObjectCorrect("TabActiveGradientColor", skin.getTabActiveGradientColor(), index);
		assertObjectCorrect("TabActiveBorderColor", skin.getTabActiveBorderColor(), index);
		assertObjectCorrect("TabActiveFontFamily", skin.getTabActiveFontFamily(), index);
		assertObjectCorrect("TabActiveFontSize", skin.getTabActiveFontSize(), index);
		assertObjectCorrect("TabActiveFontStyle", skin.getTabActiveFontStyle(), index);
		assertObjectCorrect("TabActiveFontWeight", skin.getTabActiveFontWeight(), index);
		assertObjectCorrect("TabActiveTextColor", skin.getTabActiveTextColor(), index);
		assertObjectCorrect("TabActiveTextDecoration", skin.getTabActiveTextDecoration(), index);
		assertObjectCorrect("TabInactiveBackgroundColor", skin.getTabInactiveBackgroundColor(), index);
		assertObjectCorrect("TabInactiveBackgroundImage", skin.getTabInactiveBackgroundImage(), index);
		assertObjectCorrect("TabInactiveGradientColor", skin.getTabInactiveGradientColor(), index);
		assertObjectCorrect("TabInactiveBorderColor", skin.getTabInactiveBorderColor(), index);
		assertObjectCorrect("TabInactiveFontFamily", skin.getTabInactiveFontFamily(), index);
		assertObjectCorrect("TabInactiveFontSize", skin.getTabInactiveFontSize(), index);
		assertObjectCorrect("TabInactiveFontStyle", skin.getTabInactiveFontStyle(), index);
		assertObjectCorrect("TabInactiveFontWeight", skin.getTabInactiveFontWeight(), index);
		assertObjectCorrect("TabInactiveTextColor", skin.getTabInactiveTextColor(), index);
		assertObjectCorrect("TabInactiveTextDecoration", skin.getTabInactiveTextDecoration(), index);
		assertObjectCorrect("TabDisabledBackgroundColor", skin.getTabDisabledBackgroundColor(), index);
		assertObjectCorrect("TabDisabledBackgroundImage", skin.getTabDisabledBackgroundImage(), index);
		assertObjectCorrect("TabDisabledGradientColor", skin.getTabDisabledGradientColor(), index);
		assertObjectCorrect("TabDisabledBorderColor", skin.getTabDisabledBorderColor(), index);
		assertObjectCorrect("TabDisabledFontFamily", skin.getTabDisabledFontFamily(), index);
		assertObjectCorrect("TabDisabledFontSize", skin.getTabDisabledFontSize(), index);
		assertObjectCorrect("TabDisabledFontStyle", skin.getTabDisabledFontStyle(), index);
		assertObjectCorrect("TabDisabledFontWeight", skin.getTabDisabledFontWeight(), index);
		assertObjectCorrect("TabDisabledTextColor", skin.getTabDisabledTextColor(), index);
		assertObjectCorrect("TabDisabledTextDecoration", skin.getTabDisabledTextDecoration(), index);
		assertObjectCorrect("TableBackgroundColor", skin.getTableBackgroundColor(), index);
		assertObjectCorrect("TableBorderColor", skin.getTableBorderColor(), index);
		assertObjectCorrect("TableBorderWidth", skin.getTableBorderWidth(), index);
		assertObjectCorrect("TableCellBackgroundColor", skin.getTableCellBackgroundColor(), index);
		assertObjectCorrect("TableCellBackgroundImage", skin.getTableCellBackgroundImage(), index);
		assertObjectCorrect("TableCellGradientColor", skin.getTableCellGradientColor(), index);
		assertObjectCorrect("TableCellBorderColor", skin.getTableCellBorderColor(), index);
		assertObjectCorrect("TableCellFontFamily", skin.getTableCellFontFamily(), index);
		assertObjectCorrect("TableCellFontSize", skin.getTableCellFontSize(), index);
		assertObjectCorrect("TableCellFontStyle", skin.getTableCellFontStyle(), index);
		assertObjectCorrect("TableCellFontVariant", skin.getTableCellFontVariant(), index);
		assertObjectCorrect("TableCellFontWeight", skin.getTableCellFontWeight(), index);
		assertObjectCorrect("TableCellTextColor", skin.getTableCellTextColor(), index);
		assertObjectCorrect("TableCellTextDecoration", skin.getTableCellTextDecoration(), index);
		assertObjectCorrect("TableHeaderBackgroundColor", skin.getTableHeaderBackgroundColor(), index);
		assertObjectCorrect("TableHeaderBackgroundImage", skin.getTableHeaderBackgroundImage(), index);
		assertObjectCorrect("TableHeaderGradientColor", skin.getTableHeaderGradientColor(), index);
		assertObjectCorrect("TableHeaderBorderColor", skin.getTableHeaderBorderColor(), index);
		assertObjectCorrect("TableHeaderFontFamily", skin.getTableHeaderFontFamily(), index);
		assertObjectCorrect("TableHeaderFontSize", skin.getTableHeaderFontSize(), index);
		assertObjectCorrect("TableHeaderFontStyle", skin.getTableHeaderFontStyle(), index);
		assertObjectCorrect("TableHeaderFontVariant", skin.getTableHeaderFontVariant(), index);
		assertObjectCorrect("TableHeaderFontWeight", skin.getTableHeaderFontWeight(), index);
		assertObjectCorrect("TableHeaderTextColor", skin.getTableHeaderTextColor(), index);
		assertObjectCorrect("TableHeaderTextDecoration", skin.getTableHeaderTextDecoration(), index);
		assertObjectCorrect("TableFooterBackgroundColor", skin.getTableFooterBackgroundColor(), index);
		assertObjectCorrect("TableFooterBackgroundImage", skin.getTableFooterBackgroundImage(), index);
		assertObjectCorrect("TableFooterGradientColor", skin.getTableFooterGradientColor(), index);
		assertObjectCorrect("TableFooterBorderColor", skin.getTableFooterBorderColor(), index);
		assertObjectCorrect("TableFooterFontFamily", skin.getTableFooterFontFamily(), index);
		assertObjectCorrect("TableFooterFontSize", skin.getTableFooterFontSize(), index);
		assertObjectCorrect("TableFooterFontStyle", skin.getTableFooterFontStyle(), index);
		assertObjectCorrect("TableFooterFontVariant", skin.getTableFooterFontVariant(), index);
		assertObjectCorrect("TableFooterFontWeight", skin.getTableFooterFontWeight(), index);
		assertObjectCorrect("TableFooterTextColor", skin.getTableFooterTextColor(), index);
		assertObjectCorrect("TableFooterTextDecoration", skin.getTableFooterTextDecoration(), index);
		assertObjectCorrect("TableSubHeaderBackgroundColor", skin.getTableSubHeaderBackgroundColor(), index);
		assertObjectCorrect("TableSubFooterBackgroundColor", skin.getTableSubFooterBackgroundColor(), index);
		assertObjectCorrect("TooltipBackgroundColor", skin.getTooltipBackgroundColor(), index);
		assertObjectCorrect("TooltipBackgroundImage", skin.getTooltipBackgroundImage(), index);
		assertObjectCorrect("TooltipGradientColor", skin.getTooltipGradientColor(), index);
		assertObjectCorrect("TooltipBorderColor", skin.getTooltipBorderColor(), index);
		assertObjectCorrect("TooltipBorderStyle", skin.getTooltipBorderStyle(), index);
		assertObjectCorrect("TooltipBorderWidth", skin.getTooltipBorderWidth(), index);
		assertObjectCorrect("TooltipFontFamily", skin.getTooltipFontFamily(), index);
		assertObjectCorrect("TooltipFontSize", skin.getTooltipFontSize(), index);
		assertObjectCorrect("TooltipFontStyle", skin.getTooltipFontStyle(), index);
		assertObjectCorrect("TooltipFontVariant", skin.getTooltipFontVariant(), index);
		assertObjectCorrect("TooltipFontWeight", skin.getTooltipFontWeight(), index);
		assertObjectCorrect("TooltipTextColor", skin.getTooltipTextColor(), index);
		assertObjectCorrect("TooltipTextDecoration", skin.getTooltipTextDecoration(), index);
		assertObjectCorrect("HighlightBackgroundColor", skin.getHighlightBackgroundColor(), index);
		assertObjectCorrect("HighlightBackgroundImage", skin.getHighlightBackgroundImage(), index);
		assertObjectCorrect("HighlightBorderStyle", skin.getHighlightBorderStyle(), index);
		assertObjectCorrect("HighlightBorderWidth", skin.getHighlightBorderWidth(), index);
		assertObjectCorrect("HighlightBorderColor", skin.getHighlightBorderColor(), index);
		assertObjectCorrect("HighlightFontFamily", skin.getHighlightFontFamily(), index);
		assertObjectCorrect("HighlightFontSize", skin.getHighlightFontSize(), index);
		assertObjectCorrect("HighlightFontStyle", skin.getHighlightFontStyle(), index);
		assertObjectCorrect("HighlightFontWeight", skin.getHighlightFontWeight(), index);
		assertObjectCorrect("HighlightTextColor", skin.getHighlightTextColor(), index);
		assertObjectCorrect("HighlightTextDecoration", skin.getHighlightTextDecoration(), index);
		assertObjectCorrect("HighlightControlColor", skin.getHighlightControlColor(), index);
		assertObjectCorrect("FocusBackgroundColor", skin.getFocusBackgroundColor(), index);
		assertObjectCorrect("FocusBackgroundImage", skin.getFocusBackgroundImage(), index);
		assertObjectCorrect("FocusGradientColor", skin.getFocusGradientColor(), index);
		assertObjectCorrect("FocusBorderStyle", skin.getFocusBorderStyle(), index);
		assertObjectCorrect("FocusBorderWidth", skin.getFocusBorderWidth(), index);
		assertObjectCorrect("FocusBorderColor", skin.getFocusBorderColor(), index);
		assertObjectCorrect("FocusFontFamily", skin.getFocusFontFamily(), index);
		assertObjectCorrect("FocusFontSize", skin.getFocusFontSize(), index);
		assertObjectCorrect("FocusFontStyle", skin.getFocusFontStyle(), index);
		assertObjectCorrect("FocusFontWeight", skin.getFocusFontWeight(), index);
		assertObjectCorrect("FocusTextColor", skin.getFocusTextColor(), index);
		assertObjectCorrect("FocusTextDecoration", skin.getFocusTextDecoration(), index);
		assertObjectCorrect("FocusControlColor", skin.getFocusControlColor(), index);
		assertObjectCorrect("DisabledBackgroundColor", skin.getDisabledBackgroundColor(), index);
		assertObjectCorrect("DisabledBackgroundImage", skin.getDisabledBackgroundImage(), index);
		assertObjectCorrect("DisabledBorderStyle", skin.getDisabledBorderStyle(), index);
		assertObjectCorrect("DisabledBorderWidth", skin.getDisabledBorderWidth(), index);
		assertObjectCorrect("DisabledBorderColor", skin.getDisabledBorderColor(), index);
		assertObjectCorrect("DisabledFontFamily", skin.getDisabledFontFamily(), index);
		assertObjectCorrect("DisabledFontSize", skin.getDisabledFontSize(), index);
		assertObjectCorrect("DisabledFontStyle", skin.getDisabledFontStyle(), index);
		assertObjectCorrect("DisabledFontWeight", skin.getDisabledFontWeight(), index);
		assertObjectCorrect("DisabledTextColor", skin.getDisabledTextColor(), index);
		assertObjectCorrect("DisabledTextDecoration", skin.getDisabledTextDecoration(), index);
		assertObjectCorrect("DisabledControlColor", skin.getDisabledControlColor(), index);
		assertObjectCorrect("ThemeBorderColor", skin.getThemeBorderColor(), index);
		assertObjectCorrect("ThemeBackgroundColor", skin.getThemeBackgroundColor(), index);
	}
		
	public static void assertSkinCorrect(Collection<Skin> skinList) {
		Assert.isTrue(skinList.size() == 2, "Skin count not correct");
		Iterator<Skin> iterator = skinList.iterator();
		while (iterator.hasNext()) {
			Skin skin = iterator.next();
			assertSkinCorrect(skin);
		}
	}
	
	public static void assertSameSkin(Skin skin1, Skin skin2) {
		assertSameSkin(skin1, skin2, "");
	}
	
	public static void assertSameSkin(Skin skin1, Skin skin2, String message) {
		assertObjectExists("Skin1", skin1);
		assertObjectExists("Skin2", skin2);
		assertObjectEquals("Name", skin1.getName(), skin2.getName(), message);
		assertObjectEquals("Width", skin1.getWidth(), skin2.getWidth(), message);
		assertObjectEquals("Height", skin1.getHeight(), skin2.getHeight(), message);
		assertObjectEquals("Margin", skin1.getMargin(), skin2.getMargin(), message);
		assertObjectEquals("MarginTop", skin1.getMarginTop(), skin2.getMarginTop(), message);
		assertObjectEquals("MarginLeft", skin1.getMarginLeft(), skin2.getMarginLeft(), message);
		assertObjectEquals("MarginRight", skin1.getMarginRight(), skin2.getMarginRight(), message);
		assertObjectEquals("MarginBottom", skin1.getMarginBottom(), skin2.getMarginBottom(), message);
		assertObjectEquals("Padding", skin1.getPadding(), skin2.getPadding(), message);
		assertObjectEquals("PaddingTop", skin1.getPaddingTop(), skin2.getPaddingTop(), message);
		assertObjectEquals("PaddingLeft", skin1.getPaddingLeft(), skin2.getPaddingLeft(), message);
		assertObjectEquals("PaddingRight", skin1.getPaddingRight(), skin2.getPaddingRight(), message);
		assertObjectEquals("PaddingBottom", skin1.getPaddingBottom(), skin2.getPaddingBottom(), message);
		assertObjectEquals("BorderStyle", skin1.getBorderStyle(), skin2.getBorderStyle(), message);
		assertObjectEquals("BorderWidth", skin1.getBorderWidth(), skin2.getBorderWidth(), message);
		assertObjectEquals("BorderColor", skin1.getBorderColor(), skin2.getBorderColor(), message);
		assertObjectEquals("CornerRadius", skin1.getCornerRadius(), skin2.getCornerRadius(), message);
		assertObjectEquals("BackgroundColor", skin1.getBackgroundColor(), skin2.getBackgroundColor(), message);
		assertObjectEquals("BackgroundImage", skin1.getBackgroundImage(), skin2.getBackgroundImage(), message);
		assertObjectEquals("GradientColor", skin1.getGradientColor(), skin2.getGradientColor(), message);
		assertObjectEquals("GradientImage", skin1.getGradientImage(), skin2.getGradientImage(), message);
		assertObjectEquals("FontSize", skin1.getFontSize(), skin2.getFontSize(), message);
		assertObjectEquals("FontFamily", skin1.getFontFamily(), skin2.getFontFamily(), message);
		assertObjectEquals("FontWeight", skin1.getFontWeight(), skin2.getFontWeight(), message);
		assertObjectEquals("FontStyle", skin1.getFontStyle(), skin2.getFontStyle(), message);
		assertObjectEquals("FontVariant", skin1.getFontVariant(), skin2.getFontVariant(), message);
		assertObjectEquals("TextColor", skin1.getTextColor(), skin2.getTextColor(), message);
		assertObjectEquals("TextDecoration", skin1.getTextDecoration(), skin2.getTextDecoration(), message);
		assertObjectEquals("LinkColor", skin1.getLinkColor(), skin2.getLinkColor(), message);
		assertObjectEquals("LinkHoverColor", skin1.getLinkHoverColor(), skin2.getLinkHoverColor(), message);
		assertObjectEquals("LinkVisitedColor", skin1.getLinkVisitedColor(), skin2.getLinkVisitedColor(), message);
		assertObjectEquals("ButtonFontFamily", skin1.getButtonFontFamily(), skin2.getButtonFontFamily(), message);
		assertObjectEquals("ButtonFontSize", skin1.getButtonFontSize(), skin2.getButtonFontSize(), message);
		assertObjectEquals("ButtonFontStyle", skin1.getButtonFontStyle(), skin2.getButtonFontStyle(), message);
		assertObjectEquals("ButtonFontVariant", skin1.getButtonFontVariant(), skin2.getButtonFontVariant(), message);
		assertObjectEquals("ButtonFontWeight", skin1.getButtonFontWeight(), skin2.getButtonFontWeight(), message);
		assertObjectEquals("ButtonTextColor", skin1.getButtonTextColor(), skin2.getButtonTextColor(), message);
		assertObjectEquals("ButtonTextDecoration", skin1.getButtonTextDecoration(), skin2.getButtonTextDecoration(), message);
		assertObjectEquals("ButtonBackgroundColor", skin1.getButtonBackgroundColor(), skin2.getButtonBackgroundColor(), message);
		assertObjectEquals("ButtonBackgroundImage", skin1.getButtonBackgroundImage(), skin2.getButtonBackgroundImage(), message);
		assertObjectEquals("ButtonGradientColor", skin1.getButtonGradientColor(), skin2.getButtonGradientColor(), message);
		assertObjectEquals("ButtonBorderStyle", skin1.getButtonBorderStyle(), skin2.getButtonBorderStyle(), message);
		assertObjectEquals("ButtonBorderWidth", skin1.getButtonBorderWidth(), skin2.getButtonBorderWidth(), message);
		assertObjectEquals("ButtonBorderColor", skin1.getButtonBorderColor(), skin2.getButtonBorderColor(), message);
		assertObjectEquals("ButtonControlColor", skin1.getButtonControlColor(), skin2.getButtonControlColor(), message);
		assertObjectEquals("ButtonCornerRadius", skin1.getButtonCornerRadius(), skin2.getButtonCornerRadius(), message);
		assertObjectEquals("HeaderBackgroundColor", skin1.getHeaderBackgroundColor(), skin2.getHeaderBackgroundColor(), message);
		assertObjectEquals("HeaderBackgroundImage", skin1.getHeaderBackgroundImage(), skin2.getHeaderBackgroundImage(), message);
		assertObjectEquals("HeaderGradientColor", skin1.getHeaderGradientColor(), skin2.getHeaderGradientColor(), message);
		assertObjectEquals("HeaderBorderStyle", skin1.getHeaderBorderStyle(), skin2.getHeaderBorderStyle(), message);
		assertObjectEquals("HeaderBorderWidth", skin1.getHeaderBorderWidth(), skin2.getHeaderBorderWidth(), message);
		assertObjectEquals("HeaderBorderColor", skin1.getHeaderBorderColor(), skin2.getHeaderBorderColor(), message);
		assertObjectEquals("HeaderFontFamily", skin1.getHeaderFontFamily(), skin2.getHeaderFontFamily(), message);
		assertObjectEquals("HeaderFontSize", skin1.getHeaderFontSize(), skin2.getHeaderFontSize(), message);
		assertObjectEquals("HeaderFontStyle", skin1.getHeaderFontStyle(), skin2.getHeaderFontStyle(), message);
		assertObjectEquals("HeaderFontVariant", skin1.getHeaderFontVariant(), skin2.getHeaderFontVariant(), message);
		assertObjectEquals("HeaderFontWeight", skin1.getHeaderFontWeight(), skin2.getHeaderFontWeight(), message);
		assertObjectEquals("HeaderTextColor", skin1.getHeaderTextColor(), skin2.getHeaderTextColor(), message);
		assertObjectEquals("HeaderTextDecoration", skin1.getHeaderTextDecoration(), skin2.getHeaderTextDecoration(), message);
		assertObjectEquals("ToolbarBackgroundColor", skin1.getToolbarBackgroundColor(), skin2.getToolbarBackgroundColor(), message);
		assertObjectEquals("ToolbarBackgroundImage", skin1.getToolbarBackgroundImage(), skin2.getToolbarBackgroundImage(), message);
		assertObjectEquals("ToolbarGradientColor", skin1.getToolbarGradientColor(), skin2.getToolbarGradientColor(), message);
		assertObjectEquals("ToolbarBorderColor", skin1.getToolbarBorderColor(), skin2.getToolbarBorderColor(), message);
		assertObjectEquals("ToolbarBorderStyle", skin1.getToolbarBorderStyle(), skin2.getToolbarBorderStyle(), message);
		assertObjectEquals("ToolbarBorderWidth", skin1.getToolbarBorderWidth(), skin2.getToolbarBorderWidth(), message);
		assertObjectEquals("ToolbarFontFamily", skin1.getToolbarFontFamily(), skin2.getToolbarFontFamily(), message);
		assertObjectEquals("ToolbarFontSize", skin1.getToolbarFontSize(), skin2.getToolbarFontSize(), message);
		assertObjectEquals("ToolbarFontStyle", skin1.getToolbarFontStyle(), skin2.getToolbarFontStyle(), message);
		assertObjectEquals("ToolbarFontVariant", skin1.getToolbarFontVariant(), skin2.getToolbarFontVariant(), message);
		assertObjectEquals("ToolbarFontWeight", skin1.getToolbarFontWeight(), skin2.getToolbarFontWeight(), message);
		assertObjectEquals("ToolbarTextColor", skin1.getToolbarTextColor(), skin2.getToolbarTextColor(), message);
		assertObjectEquals("ToolbarTextDecoration", skin1.getToolbarTextDecoration(), skin2.getToolbarTextDecoration(), message);
		assertObjectEquals("TabBackgroundColor", skin1.getTabBackgroundColor(), skin2.getTabBackgroundColor(), message);
		assertObjectEquals("TabBackgroundImage", skin1.getTabBackgroundImage(), skin2.getTabBackgroundImage(), message);
		assertObjectEquals("TabGradientColor", skin1.getTabGradientColor(), skin2.getTabGradientColor(), message);
		assertObjectEquals("TabBorderColor", skin1.getTabBorderColor(), skin2.getTabBorderColor(), message);
		assertObjectEquals("TabCornerRadius", skin1.getTabCornerRadius(), skin2.getTabCornerRadius(), message);
		assertObjectEquals("TabFontFamily", skin1.getTabFontFamily(), skin2.getTabFontFamily(), message);
		assertObjectEquals("TabFontSize", skin1.getTabFontSize(), skin2.getTabFontSize(), message);
		assertObjectEquals("TabFontStyle", skin1.getTabFontStyle(), skin2.getTabFontStyle(), message);
		assertObjectEquals("TabFontWeight", skin1.getTabFontWeight(), skin2.getTabFontWeight(), message);
		assertObjectEquals("TabTextColor", skin1.getTabTextColor(), skin2.getTabTextColor(), message);
		assertObjectEquals("TabTextDecoration", skin1.getTabTextDecoration(), skin2.getTabTextDecoration(), message);
		assertObjectEquals("TabActiveBackgroundColor", skin1.getTabActiveBackgroundColor(), skin2.getTabActiveBackgroundColor(), message);
		assertObjectEquals("TabActiveBackgroundImage", skin1.getTabActiveBackgroundImage(), skin2.getTabActiveBackgroundImage(), message);
		assertObjectEquals("TabActiveGradientColor", skin1.getTabActiveGradientColor(), skin2.getTabActiveGradientColor(), message);
		assertObjectEquals("TabActiveBorderColor", skin1.getTabActiveBorderColor(), skin2.getTabActiveBorderColor(), message);
		assertObjectEquals("TabActiveFontFamily", skin1.getTabActiveFontFamily(), skin2.getTabActiveFontFamily(), message);
		assertObjectEquals("TabActiveFontSize", skin1.getTabActiveFontSize(), skin2.getTabActiveFontSize(), message);
		assertObjectEquals("TabActiveFontStyle", skin1.getTabActiveFontStyle(), skin2.getTabActiveFontStyle(), message);
		assertObjectEquals("TabActiveFontWeight", skin1.getTabActiveFontWeight(), skin2.getTabActiveFontWeight(), message);
		assertObjectEquals("TabActiveTextColor", skin1.getTabActiveTextColor(), skin2.getTabActiveTextColor(), message);
		assertObjectEquals("TabActiveTextDecoration", skin1.getTabActiveTextDecoration(), skin2.getTabActiveTextDecoration(), message);
		assertObjectEquals("TabInactiveBackgroundColor", skin1.getTabInactiveBackgroundColor(), skin2.getTabInactiveBackgroundColor(), message);
		assertObjectEquals("TabInactiveBackgroundImage", skin1.getTabInactiveBackgroundImage(), skin2.getTabInactiveBackgroundImage(), message);
		assertObjectEquals("TabInactiveGradientColor", skin1.getTabInactiveGradientColor(), skin2.getTabInactiveGradientColor(), message);
		assertObjectEquals("TabInactiveBorderColor", skin1.getTabInactiveBorderColor(), skin2.getTabInactiveBorderColor(), message);
		assertObjectEquals("TabInactiveFontFamily", skin1.getTabInactiveFontFamily(), skin2.getTabInactiveFontFamily(), message);
		assertObjectEquals("TabInactiveFontSize", skin1.getTabInactiveFontSize(), skin2.getTabInactiveFontSize(), message);
		assertObjectEquals("TabInactiveFontStyle", skin1.getTabInactiveFontStyle(), skin2.getTabInactiveFontStyle(), message);
		assertObjectEquals("TabInactiveFontWeight", skin1.getTabInactiveFontWeight(), skin2.getTabInactiveFontWeight(), message);
		assertObjectEquals("TabInactiveTextColor", skin1.getTabInactiveTextColor(), skin2.getTabInactiveTextColor(), message);
		assertObjectEquals("TabInactiveTextDecoration", skin1.getTabInactiveTextDecoration(), skin2.getTabInactiveTextDecoration(), message);
		assertObjectEquals("TabDisabledBackgroundColor", skin1.getTabDisabledBackgroundColor(), skin2.getTabDisabledBackgroundColor(), message);
		assertObjectEquals("TabDisabledBackgroundImage", skin1.getTabDisabledBackgroundImage(), skin2.getTabDisabledBackgroundImage(), message);
		assertObjectEquals("TabDisabledGradientColor", skin1.getTabDisabledGradientColor(), skin2.getTabDisabledGradientColor(), message);
		assertObjectEquals("TabDisabledBorderColor", skin1.getTabDisabledBorderColor(), skin2.getTabDisabledBorderColor(), message);
		assertObjectEquals("TabDisabledFontFamily", skin1.getTabDisabledFontFamily(), skin2.getTabDisabledFontFamily(), message);
		assertObjectEquals("TabDisabledFontSize", skin1.getTabDisabledFontSize(), skin2.getTabDisabledFontSize(), message);
		assertObjectEquals("TabDisabledFontStyle", skin1.getTabDisabledFontStyle(), skin2.getTabDisabledFontStyle(), message);
		assertObjectEquals("TabDisabledFontWeight", skin1.getTabDisabledFontWeight(), skin2.getTabDisabledFontWeight(), message);
		assertObjectEquals("TabDisabledTextColor", skin1.getTabDisabledTextColor(), skin2.getTabDisabledTextColor(), message);
		assertObjectEquals("TabDisabledTextDecoration", skin1.getTabDisabledTextDecoration(), skin2.getTabDisabledTextDecoration(), message);
		assertObjectEquals("TableBackgroundColor", skin1.getTableBackgroundColor(), skin2.getTableBackgroundColor(), message);
		assertObjectEquals("TableBorderColor", skin1.getTableBorderColor(), skin2.getTableBorderColor(), message);
		assertObjectEquals("TableBorderWidth", skin1.getTableBorderWidth(), skin2.getTableBorderWidth(), message);
		assertObjectEquals("TableCellBackgroundColor", skin1.getTableCellBackgroundColor(), skin2.getTableCellBackgroundColor(), message);
		assertObjectEquals("TableCellBackgroundImage", skin1.getTableCellBackgroundImage(), skin2.getTableCellBackgroundImage(), message);
		assertObjectEquals("TableCellGradientColor", skin1.getTableCellGradientColor(), skin2.getTableCellGradientColor(), message);
		assertObjectEquals("TableCellBorderColor", skin1.getTableCellBorderColor(), skin2.getTableCellBorderColor(), message);
		assertObjectEquals("TableCellFontFamily", skin1.getTableCellFontFamily(), skin2.getTableCellFontFamily(), message);
		assertObjectEquals("TableCellFontSize", skin1.getTableCellFontSize(), skin2.getTableCellFontSize(), message);
		assertObjectEquals("TableCellFontStyle", skin1.getTableCellFontStyle(), skin2.getTableCellFontStyle(), message);
		assertObjectEquals("TableCellFontVariant", skin1.getTableCellFontVariant(), skin2.getTableCellFontVariant(), message);
		assertObjectEquals("TableCellFontWeight", skin1.getTableCellFontWeight(), skin2.getTableCellFontWeight(), message);
		assertObjectEquals("TableCellTextColor", skin1.getTableCellTextColor(), skin2.getTableCellTextColor(), message);
		assertObjectEquals("TableCellTextDecoration", skin1.getTableCellTextDecoration(), skin2.getTableCellTextDecoration(), message);
		assertObjectEquals("TableHeaderBackgroundColor", skin1.getTableHeaderBackgroundColor(), skin2.getTableHeaderBackgroundColor(), message);
		assertObjectEquals("TableHeaderBackgroundImage", skin1.getTableHeaderBackgroundImage(), skin2.getTableHeaderBackgroundImage(), message);
		assertObjectEquals("TableHeaderGradientColor", skin1.getTableHeaderGradientColor(), skin2.getTableHeaderGradientColor(), message);
		assertObjectEquals("TableHeaderBorderColor", skin1.getTableHeaderBorderColor(), skin2.getTableHeaderBorderColor(), message);
		assertObjectEquals("TableHeaderFontFamily", skin1.getTableHeaderFontFamily(), skin2.getTableHeaderFontFamily(), message);
		assertObjectEquals("TableHeaderFontSize", skin1.getTableHeaderFontSize(), skin2.getTableHeaderFontSize(), message);
		assertObjectEquals("TableHeaderFontStyle", skin1.getTableHeaderFontStyle(), skin2.getTableHeaderFontStyle(), message);
		assertObjectEquals("TableHeaderFontVariant", skin1.getTableHeaderFontVariant(), skin2.getTableHeaderFontVariant(), message);
		assertObjectEquals("TableHeaderFontWeight", skin1.getTableHeaderFontWeight(), skin2.getTableHeaderFontWeight(), message);
		assertObjectEquals("TableHeaderTextColor", skin1.getTableHeaderTextColor(), skin2.getTableHeaderTextColor(), message);
		assertObjectEquals("TableHeaderTextDecoration", skin1.getTableHeaderTextDecoration(), skin2.getTableHeaderTextDecoration(), message);
		assertObjectEquals("TableFooterBackgroundColor", skin1.getTableFooterBackgroundColor(), skin2.getTableFooterBackgroundColor(), message);
		assertObjectEquals("TableFooterBackgroundImage", skin1.getTableFooterBackgroundImage(), skin2.getTableFooterBackgroundImage(), message);
		assertObjectEquals("TableFooterGradientColor", skin1.getTableFooterGradientColor(), skin2.getTableFooterGradientColor(), message);
		assertObjectEquals("TableFooterBorderColor", skin1.getTableFooterBorderColor(), skin2.getTableFooterBorderColor(), message);
		assertObjectEquals("TableFooterFontFamily", skin1.getTableFooterFontFamily(), skin2.getTableFooterFontFamily(), message);
		assertObjectEquals("TableFooterFontSize", skin1.getTableFooterFontSize(), skin2.getTableFooterFontSize(), message);
		assertObjectEquals("TableFooterFontStyle", skin1.getTableFooterFontStyle(), skin2.getTableFooterFontStyle(), message);
		assertObjectEquals("TableFooterFontVariant", skin1.getTableFooterFontVariant(), skin2.getTableFooterFontVariant(), message);
		assertObjectEquals("TableFooterFontWeight", skin1.getTableFooterFontWeight(), skin2.getTableFooterFontWeight(), message);
		assertObjectEquals("TableFooterTextColor", skin1.getTableFooterTextColor(), skin2.getTableFooterTextColor(), message);
		assertObjectEquals("TableFooterTextDecoration", skin1.getTableFooterTextDecoration(), skin2.getTableFooterTextDecoration(), message);
		assertObjectEquals("TableSubHeaderBackgroundColor", skin1.getTableSubHeaderBackgroundColor(), skin2.getTableSubHeaderBackgroundColor(), message);
		assertObjectEquals("TableSubFooterBackgroundColor", skin1.getTableSubFooterBackgroundColor(), skin2.getTableSubFooterBackgroundColor(), message);
		assertObjectEquals("TooltipBackgroundColor", skin1.getTooltipBackgroundColor(), skin2.getTooltipBackgroundColor(), message);
		assertObjectEquals("TooltipBackgroundImage", skin1.getTooltipBackgroundImage(), skin2.getTooltipBackgroundImage(), message);
		assertObjectEquals("TooltipGradientColor", skin1.getTooltipGradientColor(), skin2.getTooltipGradientColor(), message);
		assertObjectEquals("TooltipBorderColor", skin1.getTooltipBorderColor(), skin2.getTooltipBorderColor(), message);
		assertObjectEquals("TooltipBorderStyle", skin1.getTooltipBorderStyle(), skin2.getTooltipBorderStyle(), message);
		assertObjectEquals("TooltipBorderWidth", skin1.getTooltipBorderWidth(), skin2.getTooltipBorderWidth(), message);
		assertObjectEquals("TooltipFontFamily", skin1.getTooltipFontFamily(), skin2.getTooltipFontFamily(), message);
		assertObjectEquals("TooltipFontSize", skin1.getTooltipFontSize(), skin2.getTooltipFontSize(), message);
		assertObjectEquals("TooltipFontStyle", skin1.getTooltipFontStyle(), skin2.getTooltipFontStyle(), message);
		assertObjectEquals("TooltipFontVariant", skin1.getTooltipFontVariant(), skin2.getTooltipFontVariant(), message);
		assertObjectEquals("TooltipFontWeight", skin1.getTooltipFontWeight(), skin2.getTooltipFontWeight(), message);
		assertObjectEquals("TooltipTextColor", skin1.getTooltipTextColor(), skin2.getTooltipTextColor(), message);
		assertObjectEquals("TooltipTextDecoration", skin1.getTooltipTextDecoration(), skin2.getTooltipTextDecoration(), message);
		assertObjectEquals("HighlightBackgroundColor", skin1.getHighlightBackgroundColor(), skin2.getHighlightBackgroundColor(), message);
		assertObjectEquals("HighlightBackgroundImage", skin1.getHighlightBackgroundImage(), skin2.getHighlightBackgroundImage(), message);
		assertObjectEquals("HighlightBorderStyle", skin1.getHighlightBorderStyle(), skin2.getHighlightBorderStyle(), message);
		assertObjectEquals("HighlightBorderWidth", skin1.getHighlightBorderWidth(), skin2.getHighlightBorderWidth(), message);
		assertObjectEquals("HighlightBorderColor", skin1.getHighlightBorderColor(), skin2.getHighlightBorderColor(), message);
		assertObjectEquals("HighlightFontFamily", skin1.getHighlightFontFamily(), skin2.getHighlightFontFamily(), message);
		assertObjectEquals("HighlightFontSize", skin1.getHighlightFontSize(), skin2.getHighlightFontSize(), message);
		assertObjectEquals("HighlightFontStyle", skin1.getHighlightFontStyle(), skin2.getHighlightFontStyle(), message);
		assertObjectEquals("HighlightFontWeight", skin1.getHighlightFontWeight(), skin2.getHighlightFontWeight(), message);
		assertObjectEquals("HighlightTextColor", skin1.getHighlightTextColor(), skin2.getHighlightTextColor(), message);
		assertObjectEquals("HighlightTextDecoration", skin1.getHighlightTextDecoration(), skin2.getHighlightTextDecoration(), message);
		assertObjectEquals("HighlightControlColor", skin1.getHighlightControlColor(), skin2.getHighlightControlColor(), message);
		assertObjectEquals("FocusBackgroundColor", skin1.getFocusBackgroundColor(), skin2.getFocusBackgroundColor(), message);
		assertObjectEquals("FocusBackgroundImage", skin1.getFocusBackgroundImage(), skin2.getFocusBackgroundImage(), message);
		assertObjectEquals("FocusGradientColor", skin1.getFocusGradientColor(), skin2.getFocusGradientColor(), message);
		assertObjectEquals("FocusBorderStyle", skin1.getFocusBorderStyle(), skin2.getFocusBorderStyle(), message);
		assertObjectEquals("FocusBorderWidth", skin1.getFocusBorderWidth(), skin2.getFocusBorderWidth(), message);
		assertObjectEquals("FocusBorderColor", skin1.getFocusBorderColor(), skin2.getFocusBorderColor(), message);
		assertObjectEquals("FocusFontFamily", skin1.getFocusFontFamily(), skin2.getFocusFontFamily(), message);
		assertObjectEquals("FocusFontSize", skin1.getFocusFontSize(), skin2.getFocusFontSize(), message);
		assertObjectEquals("FocusFontStyle", skin1.getFocusFontStyle(), skin2.getFocusFontStyle(), message);
		assertObjectEquals("FocusFontWeight", skin1.getFocusFontWeight(), skin2.getFocusFontWeight(), message);
		assertObjectEquals("FocusTextColor", skin1.getFocusTextColor(), skin2.getFocusTextColor(), message);
		assertObjectEquals("FocusTextDecoration", skin1.getFocusTextDecoration(), skin2.getFocusTextDecoration(), message);
		assertObjectEquals("FocusControlColor", skin1.getFocusControlColor(), skin2.getFocusControlColor(), message);
		assertObjectEquals("DisabledBackgroundColor", skin1.getDisabledBackgroundColor(), skin2.getDisabledBackgroundColor(), message);
		assertObjectEquals("DisabledBackgroundImage", skin1.getDisabledBackgroundImage(), skin2.getDisabledBackgroundImage(), message);
		assertObjectEquals("DisabledBorderStyle", skin1.getDisabledBorderStyle(), skin2.getDisabledBorderStyle(), message);
		assertObjectEquals("DisabledBorderWidth", skin1.getDisabledBorderWidth(), skin2.getDisabledBorderWidth(), message);
		assertObjectEquals("DisabledBorderColor", skin1.getDisabledBorderColor(), skin2.getDisabledBorderColor(), message);
		assertObjectEquals("DisabledFontFamily", skin1.getDisabledFontFamily(), skin2.getDisabledFontFamily(), message);
		assertObjectEquals("DisabledFontSize", skin1.getDisabledFontSize(), skin2.getDisabledFontSize(), message);
		assertObjectEquals("DisabledFontStyle", skin1.getDisabledFontStyle(), skin2.getDisabledFontStyle(), message);
		assertObjectEquals("DisabledFontWeight", skin1.getDisabledFontWeight(), skin2.getDisabledFontWeight(), message);
		assertObjectEquals("DisabledTextColor", skin1.getDisabledTextColor(), skin2.getDisabledTextColor(), message);
		assertObjectEquals("DisabledTextDecoration", skin1.getDisabledTextDecoration(), skin2.getDisabledTextDecoration(), message);
		assertObjectEquals("DisabledControlColor", skin1.getDisabledControlColor(), skin2.getDisabledControlColor(), message);
		assertObjectEquals("ThemeBorderColor", skin1.getThemeBorderColor(), skin2.getThemeBorderColor(), message);
		assertObjectEquals("ThemeBackgroundColor", skin1.getThemeBackgroundColor(), skin2.getThemeBackgroundColor(), message);
	}
	
	public static void assertSameSkin(Collection<Skin> skinList1, Collection<Skin> skinList2) {
		assertSameSkin(skinList1, skinList2, "");
	}
	
	public static void assertSameSkin(Collection<Skin> skinList1, Collection<Skin> skinList2, String message) {
		Assert.notNull(skinList1, "Skin list1 must be specified");
		Assert.notNull(skinList2, "Skin list2 must be specified");
		Assert.equals(skinList1.size(), skinList2.size(), "Skin count not equal");
		Collection<Skin> sortedRecords1 = SkinUtil.sortRecords(skinList1);
		Collection<Skin> sortedRecords2 = SkinUtil.sortRecords(skinList2);
		Iterator<Skin> list1Iterator = sortedRecords1.iterator();
		Iterator<Skin> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Skin skin1 = list1Iterator.next();
			Skin skin2 = list2Iterator.next();
			assertSameSkin(skin1, skin2, message);
		}
	}
	
}
