package template1_package.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.bean.ProxyLocator;
import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.Globals;
import org.aries.ui.Messages;
import org.aries.ui.manager.UploadManager;
import org.aries.ui.util.ConversationHelper;
import org.aries.util.ExceptionUtil;
import org.aries.util.properties.PropertyManager;
import org.aries.util.ssl.KeystoreUtil;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
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

import admin.Role;
import admin.RoleType;
import admin.User;
import admin.client.userService.UserService;


@Startup
@AutoCreate
@Scope(ScopeType.SESSION)
@Name("securityManager")
@SuppressWarnings("serial")
public class SecurityManager implements Serializable {

	private static Log log = LogFactory.getLog(SecurityManager.class);

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

	private String username;

	//private String keystoreFileName;
	
	private byte[] keystoreFileData;
	
	//@Inject
	//@Client
	@In(value = UserService.ID, required = true)
	private UserService userService;

	@In(required=false, scope=ScopeType.CONVERSATION)
	private UploadManager uploadManager;

	@In(required=true)
	private ConversationHelper conversationHelper;

	
	public String getDescription() {
		return "User Manager";
	}

	public User getUser() {
		return principal;
	}

	public String getUsername() {
		return credentials.getUsername();
	}

	public void setUsername(String username) {
		//credentials.setUsername("");
		credentials.setUsername(username);
	}

	public String getPassword() {
		return "";
	}

	public void setPassword(String password) {
		//credentials.setPassword("");
		credentials.setPassword(username);
	}

	public boolean isTesting() {
		boolean value = PropertyManager.getInstance().getBoolean("aries.testing");
		return value && false;
	}

	@Create
	public void start() throws Exception {
		PropertyManager.getInstance().initialize();
		display.setModule("Login");
		reset();
	}
	
	@Destroy
	public void stop() throws Exception {
		//do nothing for now
	}

	public String reset() {
		//display.info(getDescription());
		initialize();
		return null;
	}

	//@Begin
	public String initialize() {
		display.setModule("Login");
		conversationHelper.logStatus();
		log.debug("testing=" + isTesting());
		rememberMe.setEnabled(true);

		if (identity.isLoggedIn()) {
			log.debug("authenticated");
			//raiseLoginSuccessfulEvent();
			return "authenticated";
		}

		if (isTesting()) {
			identity.logout();
			log.debug("not-authenticated");
			return "testing";
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
			display.info("Login", "Sign-in to access the site");
			return "not-authenticated";
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

		try {
			log.info("logged in user: " + username);
			//UserService userService = getClient(UserService.ID);
			principal = userService.getUserByUserName(username);
			Assert.notNull(principal, "User not found: "+username);
			log.info("logged in user: "+principal);
	
		} catch (Exception e) {
			Throwable cause = ExceptionUtil.getRootCause(e);
			messages.error("login", cause.getMessage());
		}
		
		if (principal != null) {
			log.debug("Super-user: " + globals.isSuperUser());
		}

		log.debug("authenticated");
		//raiseLoginSuccessfulEvent();
		return "authenticated";
	}

	public void login() {
		try {
			identity.login();
			List<FacesMessage> list = messages.getMessages("Login");
			if (!list.isEmpty() || credentials.isInvalid()) {
				//Session.instance().invalidate();
				//credentials.setUsername("");
				//System.out.println();
			}
		} catch (Exception e) {
			display.error("Login", "Error: "+e.getMessage());
		}
	}
	
	public boolean authenticate() {
		display.setModule("Login");
		conversationHelper.logStatus();
		log.debug("testing: " + isTesting());

		try {
			username = credentials.getUsername();
			String password = credentials.getPassword();

			if (StringUtils.isEmpty(username)) {
				display.error("Login", "User name must be specified.");
				return false;
			}
			
			if (username.equals("admin")) {
				identity.addRole("admin");
			}
			
			//validateKeyFile();
			//KeystoreUtil.initializeKeystoreProperties(keystoreFileName);

			if (StringUtils.isEmpty(password)) {
				display.error("Login", "Password must be specified.");
				log.debug("not-authenticated");
				return false;
			}

			//UserService userService = getClient(UserService.ID);
			principal = userService.getUserByUserName(username);
			if (principal == null) {
				display.error("Login", "Invalid user name.");
				log.debug("not-authenticated");
				return false;
			}

			//TODO add hash
			if (!principal.getPasswordHash().equals(password)) {
				display.error("Login", "Incorrect password.");
				log.debug("not-authenticated");
				return false;
			}

			Set<Role> roles = principal.getRoles();
			Iterator<Role> iterator = roles.iterator();
			while (iterator.hasNext()) {
				Role role = iterator.next();
				RoleType roleType = role.getRoleType();
				identity.addRole(roleType.name());
			}

			BeanContext.set("org.aries.user", principal);
			BeanContext.set("org.aries.userName", username);
			
			log.debug("authenticated: "+username);
			raiseUserAuthenticatedEvent();
			return true;

		} catch (Throwable e) {
			Throwable cause = ExceptionUtils.getRootCause(e);
			if (cause == null)
				cause = e;
			String message = cause.getMessage();
			display.error("Login", message);
			log.error("Error", cause);
			return false;
		}
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
		events.raiseEvent("org.aries.userAuthenticated");
	}
	
	protected void raiseLoginSuccessfulEvent() {
		events.raiseEvent("org.jboss.seam.security.loginSuccessful");
	}

	protected void raiseAlreadyLoggedInEvent() {
		events.raiseEvent("org.jboss.seam.security.alreadyLoggedIn");
	}
	

	@Begin(join = true)
	public void authenticateUploadedKeyfile() { 
		try {
			conversationHelper.logStatus();
			display.initialize("LoginUpload");
			uploadManager = (UploadManager) BeanContext.get("uploadManager");
			uploadManager = (UploadManager) Component.getInstance(UploadManager.class, ScopeType.CONVERSATION);
			byte[] fileData = uploadManager.getFileData();
			if (fileData == null) {
				String message = "No Key-File to save...";
				display.error(message);
				log.error(message);
			} else {
				//Assert.notNull(fileData, "Key-file must be specified");
				byte[] unzippedFileData = KeystoreUtil.getUnzippedKeystoreFileData(uploadManager.getFileData());
				Assert.notNull(unzippedFileData, "Error unzipping Key-File");
				keystoreFileData = unzippedFileData;
			}			
		} catch (Exception e) {
			display.error(e);
			log.error(e);
		}
	}

	protected void validateKeyFile() {
    	Assert.notNull(keystoreFileData, "Key-file must be specified");
    	Assert.isTrue(keystoreFileData.length > 0, "Key-file data not found");
    	Assert.equals(keystoreFileData.length, 1903, "Key-file data not correct");

//    	try {
//    		byte[] fileData = KeystoreUtil.getUnzippedKeystoreFileData("key-source.zip");
//	    	Assert.equals(keystoreFileData.length, fileData.length, "Key-file data not correct");
//	    	for (int i = 0; i < fileData.length; i++) {
//		    	Assert.equals(keystoreFileData[i], fileData[i], "Key-file data mismatch");
//	    	}
//	    	
//    	} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}

	public String logout() {
		log.debug("logout");
		identity.logout();
		username = null;
		if (isTesting())
			return "testing";
		return "success";
	}

	protected <T> T getClient(String clientId) {
		String proxyKey = clientId;
		ProxyLocator proxyLocator = BeanContext.get("org.aries.proxyLocator");
		T client = proxyLocator.get(proxyKey);
		if (client == null)
			System.out.println();
		//((Client) client).setCorrelationId(getCorrelationId());
		return client;
	}
	
}
