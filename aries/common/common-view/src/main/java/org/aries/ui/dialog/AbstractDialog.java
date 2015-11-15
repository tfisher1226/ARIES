package org.aries.ui.dialog;

import java.io.Serializable;

import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.BeanContext;
import org.aries.util.ClassUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.ReflectionUtil;


@SuppressWarnings("serial")
public abstract class AbstractDialog implements Serializable {

	protected Log log = LogFactory.getLog(getClass());

	private String status = "";

	private String title = "";
	
	private String subtitle = "";
	
	private String message = "";

	private String text = "";
	
	private String caption;

	private String renderList;

	private boolean progressEnabled;

	private boolean cancelEnabled;

	private String actionEvent;

	
	public AbstractDialog() {
		//setStatus(Status.INFO);
	}
	
//	public boolean isInfoType() {
//		return status == Status.INFO;
//	}
//
//	public boolean isPromptType() {
//		return status == Status.PROMPT;
//	}
//
//	public boolean isWarningType() {
//		return status == Status.WARNING;
//	}
//
//	public boolean isErrorType() {
//		return status == Status.ERROR;
//	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isTitleExists() {
		return !isEmpty(getTitle());
	}

	public boolean isSubtitleExists() {
		return !isEmpty(getSubtitle());
	}

	public boolean isMessageExists() {
		return !isEmpty(getMessage());
	}

	public boolean isTextExists() {
		return !isEmpty(getText());
	}

	public boolean isErrorsExist() {
		return FacesContext.getCurrentInstance().getMessageList().size() > 0;
	}

	protected boolean isEmpty(String text) {
		return StringUtils.isEmpty(text);
	}

	
//	public String getStatus() {
//		switch (status) {
//		case INFO: return "info";
//		case PROMPT: return "prompt";
//		case WARNING: return "warning";
//		case ERROR: return "error";
//		default: return "";
//		}
//	}

	public String getTitle() {
		//log.info("--------- getTitle: "+title+", "+this);
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getRenderList() {
		return renderList;
	}

	public void setRenderList(String renderList) {
		this.renderList = renderList;
	}

	public boolean isProgressEnabled() {
		return progressEnabled;
	}

	public void setProgressEnabled(boolean value) {
		progressEnabled = value;
	}
	
	public boolean isCancelEnabled() {
		return cancelEnabled;
	}

	public void setCancelEnabled(boolean value) {
		cancelEnabled = value;
	}

	public String getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(String actionEvent) {
		this.actionEvent = actionEvent;
	}


	protected void addInfoMessage(String clientId, String detail) {
		addMessage(clientId, FacesMessage.SEVERITY_INFO, detail);
	}

	protected void addErrorMessage(String clientId, String detail) {
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, detail);
	}

	protected void addWarningMessage(String clientId, String detail) {
		addMessage(clientId, FacesMessage.SEVERITY_WARN, detail);
	}

	protected void addMessage(String clientId, Severity severity, String detail) {
		FacesMessage message = new FacesMessage(severity, "", detail);
		FacesContext.getCurrentInstance().addMessage(clientId, message);
	}

	
	public void reset() {
		//actionEvent = null;
		//cancelEnabled = false;
		//progressEnabled = false;
		//setRerenderList(null);
	}

	public void execute() {
		try {
			if (actionEvent != null) {
				int indexOf = actionEvent.indexOf(".");
				String beanName = actionEvent.substring(0, indexOf);
				String methodName = actionEvent.substring(indexOf+1);
				Object beanInstance = BeanContext.getFromSession(beanName);
				if (beanInstance != null) {
					ReflectionUtil.invokeMethod(beanInstance, methodName, null);
					return;
				}
			}
			if (actionEvent != null) {
				Class<?> loadClass = ClassUtil.loadClass(actionEvent);
				Object event = ObjectUtil.loadObject(loadClass);
				BeanManager lookBeanManager = lookBeanManager();
				lookBeanManager.fireEvent(event);
			}
			//SEAM 	Events.instance().raiseEvent(actionEvent);
		} catch (Throwable e) {
			//ignore at this point
			//(should be handled within action)
		} finally {
			reset();
		}
	}

	protected BeanManager lookBeanManager() {
		try {
			Context initialContext = new InitialContext();
			BeanManager beanManager = (BeanManager) initialContext.lookup("java:comp/BeanManager");
			return beanManager;
		} catch (final Exception e) {
			throw new IllegalStateException("Cannot locate BeanManager", e);
		}
	}

	public void cancel() {
		reset();
	}

}
