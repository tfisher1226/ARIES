package org.aries.ui;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.aries.runtime.BeanContext;
import org.aries.ui.event.ResetEvent;
import org.aries.ui.util.SeamConversationHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


//@SessionScoped
//@Named("globals")

@Startup
@AutoCreate
@Name("globals")
@Scope(ScopeType.SESSION)
public class Globals implements Serializable {

	private boolean superUser = true;

	private boolean readOnly = false;

	private String page;

	private int pageWidth = 900;

	private int screenWidth = 0;

	private int screenHeight = 0;

	private int zIndex = 100;
	
	private String browserName = "placeholder";

	private String browserVersion = "placeholder";

//	private UserSkin userSkin;
	

	public Long getTime() {
		return System.currentTimeMillis();
	}

	public boolean isSuperUser() {
		return superUser;
	}
	
	public void setSuperUser(boolean value) {
		superUser = value;
	}
	
	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean value) {
		readOnly = value;
	}

	public String getUserRole() {
		if (isSuperUser())
			return "[Administrator]";
		return "";
	}
	
	
	public int getScreenWidth() {
		return Math.min(screenWidth, 1050);
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}

	public int getZIndex() {
		return zIndex;
	}

	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public boolean isMozilla() {
		return browserName != null && browserName.equals("mozilla");
	}

	public boolean isSafari() {
		return browserName != null && browserName.equals("safari");
	}

	public boolean isIE() {
		return browserName != null && browserName.equals("msie");
	}

	
	private long nextId = 1;
	
	public String nextId() {
		return Long.toString(nextId++);
	}

	public String getUrl() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String contextPath = request.getContextPath();
		String localAddr = request.getLocalAddr();
		int localPort = request.getLocalPort();
		String url = "http://"+localAddr+":"+localPort+contextPath;
		return url;
	}

	@Create
	public void create() {
		BeanContext.set("globals",  this);
	}
	
	@Destroy
	public void destroy() {
		BeanContext.set("globals",  null);
	}
	

	public String doNothing() {
		//nothing for now
		return null;
	}

	public String showPage(String page) {
		this.page = page;
		return null;
	}

	public String showLoginPage() {
		return "/login.xhtml";
	}
	
	public String showMainPage() {
		return "/main.xhtml";
	}

	public String showErrorPage() {
		return "/error.xhtml";
	}

	public String showHelp() {
		return "/help.xhtml";
	}

	public void pause() {
		pause(2000);
	}
	
	public void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			//ignore
		}
	}
	
	public static boolean isPostback() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return context.getRenderKit().getResponseStateManager().isPostback(context);
	}

	public void observeReset(@Observes ResetEvent event) {
		reset();
	}
	
	public void reset() {
		SeamConversationHelper seamConversationHelper = BeanContext.getFromApplication("seamConversationHelper");
		seamConversationHelper.end();
		//userSkin.reset();
		zIndex = 100;
	}

	public void rebuildRootView() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    Application application = context.getApplication();
	    ViewHandler viewHandler = application.getViewHandler();
	    UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
	    context.setViewRoot(viewRoot);
	}
	
}
