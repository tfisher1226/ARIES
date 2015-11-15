package aries.ui.security;

import java.io.Serializable;

import javax.inject.Inject;


//@SessionScoped
//@Named("identity")
public class Identity implements Serializable {

	private boolean loggedIn;

	private boolean rememberMe = true;

	
	@Inject
	private Credentials credentials;
	
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
}
