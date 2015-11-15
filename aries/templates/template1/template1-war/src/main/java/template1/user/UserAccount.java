package template1.user;

import java.util.Set;

import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;


//@Entity
public class UserAccount {

	private String username;
	
	private String password;

	
	@UserPrincipal
	public String getUsername() {
		return username;
	}

	@UserPassword(hash="MD5")
	public String getPassword() {
		return password;
	}
	
	//@ManyToMany
	@UserRoles
	Set<UserRole> getRoles() {
		return null;
	}
	
}

