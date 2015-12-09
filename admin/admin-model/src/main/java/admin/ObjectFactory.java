package admin;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		//nothing for now
	}


	public Permission createPermission() {
		return new Permission();
	}

	public Preferences createPreferences() {
		return new Preferences();
	}

	public Registration createRegistration() {
		return new Registration();
	}
	
	public Role createRole() {
		return new Role();
	}

	public Skin createSkin() {
		return new Skin();
	}
	
	public Team createTeam() {
		return new Team();
	}
	
	public User createUser() {
		return new User();
	}
	
	public UserCriteria createUserCriteria() {
		return new UserCriteria();
	}
	
	public Users createUsers() {
		return new Users();
	}
	
}
