package org.aries.service.jaxws;

import java.security.Principal;


public interface SecurityContext {

	public Principal getUserPrincipal();

	public boolean isUserInRole(String role);

}
