package template1.user;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractPanelManager;
import org.aries.ui.Display;
import org.aries.ui.Globals;
import org.aries.ui.Messages;
import org.aries.ui.manager.UploadManager;
import org.aries.util.properties.PropertyManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.core.Events;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.RememberMe;

import template1.model.User;
import template1.service.UserService;


@Startup
@AutoCreate
@SuppressWarnings("serial")
@Scope(ScopeType.SESSION)
@Name("userManager")
public class UserManager extends AbstractPanelManager implements Serializable {

	@In(required = true)
	private Events events;

	@In(required = false)
	private Globals globals;

	@In(required=true)
	private Display display;

	@In(required=true)
	private Messages messages;

	@In(required=true)
	private Identity identity;

	@In(required=true)
	private Credentials credentials;
	
	@In(required=true)
	private RememberMe rememberMe;
	
	@Out(required=false)
	private User principal;

	@Out(required=false)
	private String username;

	@In(required=true)
	private UserService userService;

	@In(required = true)
	private UploadManager uploadManager;

	
	public String getDescription() {
		return "User Manager";
	}

	public User getUser() {
		return principal;
	}

	@Create
	public void start() throws Exception {
		PropertyManager.getInstance().initialize();
		display.setModule("main");
		reset();
	}
	
	@Destroy
	public void stop() throws Exception {
		//do nothing for now
	}

	public void reset() {
		//display.info(getDescription());
		initialize();
	}

	public void initialize() {
		log.debug("testing=" + isTesting());

		if (identity.isLoggedIn()) {
			log.debug("authenticated");
			//raiseLoginSuccessfulEvent();
			//return "authenticated";
		}

		if (isTesting()) {
			identity.logout();
			log.debug("not-authenticated");
			//return "testing";
		}

		/*
		if (username == null) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			username = request.getHeader("ARIES_USER");
		}
		*/

		if (username == null) {
			log.debug("not-authenticated");
			//return "not-authenticated";
		}

//		try {
//			credentials.setUsername(username);
//			credentials.setInitialized(true);
//			SimplePrincipal principal = new SimplePrincipal(username);
//			identity.acceptExternallyAuthenticatedPrincipal(principal);
//			rememberMe.setEnabled(true);
//		} catch (Exception e) {
//			log.error("Error", e);
//			return "";
//		}

		log.info("logged in user: " + username);
		principal = userService.getUserByName(username);
		log.info("logged in user: "+principal);

		if (principal != null) {
			log.debug("Super-user: " + globals.isSuperUser());
		}

		log.debug("authenticated");
		//raiseLoginSuccessfulEvent();
		//return "authenticated";
	}

	public boolean authenticate() {
		log.debug("testing: " + isTesting());

		try {
			username = credentials.getUsername();
			String password = credentials.getPassword();

			if (StringUtils.isEmpty(username)) {
				messages.error("login", "User name must be specified.");
				return false;
			}
			
			if (username.equals("admin")) {
				identity.addRole("admin");
			}
			
			principal = userService.getUserByName(username);
			if (principal != null && principal.getPassword().equals(password)) {
				log.debug("authenticated: "+username);
				raiseUserAuthenticatedEvent();
				return true;
			}
			
			messages.error("login", "Invalid user name or password.");
			log.debug("not-authenticated");
			return false;
			
		} catch (Throwable e) {
			messages.error("login", e.getMessage());
			log.error("not-authenticated: "+e);
			return false;
		}
	}


	public boolean isTesting() {
		boolean value = PropertyManager.getInstance().getBoolean("aries.testing");
		return value;
	}

	public String showRegistrationDialog() {
		return "";
	}
	
	protected void redirect(String url) {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(url);
		} catch (IOException e) {
			log.error("Error: ", e);
		}
	}

	protected void raiseUserAuthenticatedEvent() {
		events.raiseEvent("org.sgiusa.userAuthenticated");
	}
	
	protected void raiseLoginSuccessfulEvent() {
		events.raiseEvent("org.jboss.seam.security.loginSuccessful");
	}

	protected void raiseAlreadyLoggedInEvent() {
		events.raiseEvent("org.jboss.seam.security.alreadyLoggedIn");
	}

}
