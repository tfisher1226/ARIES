package org.aries.service.jaxws;

import java.security.Principal;


public class SecurityContextImpl implements SecurityContext {

	
	@Override
	public Principal getUserPrincipal() {
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		return false;
	}

}

