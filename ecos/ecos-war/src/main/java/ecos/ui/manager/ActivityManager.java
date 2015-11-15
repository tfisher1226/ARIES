package ecos.ui.manager;

import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.aries.runtime.BeanContext;
import org.aries.ui.wizard.AbstractManager;
import org.aries.util.FileUtil;
import org.aries.util.ResourceUtil;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import admin.client.userService.UserService;
import admin.ui.user.UserHelper;


@Startup
@AutoCreate
@Name("activityManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class ActivityManager extends AbstractManager {

	private String actionName;


	public ActivityManager() {
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	protected UserHelper getUserHelper() {
		return BeanContext.getFromSession("userHelper");
	}

	protected UserService getUserService() {
		return BeanContext.getFromSession(UserService.ID);
	}

	public void saveAction() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		//String requestURI = request.getRequestURI();
		//StringBuffer requestURL = request.getRequestURL();
		//String ipAddress = request.getRemoteAddr();
		String remoteHost = request.getRemoteHost();
		int remotePort = request.getRemotePort();
		//String remoteUser = request.getRemoteUser();
		//Principal userPrincipal = request.getUserPrincipal();
		String sessionId = request.getRequestedSessionId();
		sessionId = sessionId.replace(".undefined", "");		

		//		Event event = new Event();
		//		event.setTimestamp(new Date());
		//		event.setEventAction(EventAction.ACCESSED);
		//		event.setEventSeverity(EventSeverity.EVENT);
		//		event.setSource(remoteHost+":"+remotePort);
		//		event.setDescription(sessionId);

		log.info("Action from user: host=" + remoteHost+", session="+sessionId+", action="+actionName);
	}

	public void showFlyer(OutputStream outputStream, Object object) throws Exception {
		InputStream inputStream = ResourceUtil.getResourceAsStream("/ecos/ui/manager/flyer.pdf");
		//FileInputStream inputStream = new FileInputStream("/ecos/ui/manager/flyer.pdf");
		String text = FileUtil.readStreamAsString(inputStream);
		outputStream.write(text.getBytes());
	}

}
