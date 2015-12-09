package org.aries.ui;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.BeanContext;
import org.aries.ui.util.SeamConversationHelper;
import org.aries.util.Validator;


public abstract class AbstractPanelManager {

	protected Log log = LogFactory.getLog(getClass());

	private String title;

	private String header;
	
	//SEAM protected Events events;

	protected Display display;
	
	protected String panelKey;

	protected String panelName;

	protected String panelTitle;

	protected String panelHeader;

	protected String panelMessage;

	protected String dialogKey;

	protected String dialogName;
	
	protected String dialogTitle;

	protected String dialogMessage;

	protected String actionEvent;

	protected String refreshEvent;
	
	protected String cancelEvent;

	protected String targetService;

	protected String targetDomain;

	protected String targetField;

	protected String renderList;
	
	protected int zIndex;

	protected boolean visible;
	
	protected boolean immediate;

	protected boolean modified;

	protected boolean checked;

	protected boolean validated;

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPanelKey() {
		return panelKey;
	}
	
	public void setPanelKey(String panelKey) {
		this.panelKey = panelKey;
	}

	public String getPanelName() {
		return panelName;
	}

	public void setPanelName(String pageName) {
		this.panelName = pageName;
	}
	
	public String getPanelTitle() {
		return panelTitle;
	}

	public void setPanelTitle(String pageTitle) {
		this.panelTitle = pageTitle;
	}
	
	public String getPanelHeader() {
		return panelHeader;
	}

	public void setPanelHeader(String pageHeader) {
		this.panelHeader = pageHeader;
	}
	
	public String getPanelMessage() {
		return panelMessage;
	}

	public void setPanelMessage(String pageMessage) {
		this.panelMessage = pageMessage;
	}
	
	public String getDialogKey() {
		return dialogKey;
	}
	
	public void setDialogKey(String dialogKey) {
		this.dialogKey = dialogKey;
	}

	public String getDialogName() {
		return dialogName;
	}
	
	public void setDialogName(String dialogName) {
		this.dialogName = dialogName;
	}

	public String getDialogMessage() {
		return dialogMessage;
	}
	
	public void setDialogMessage(String dialogMessage) {
		this.dialogMessage = dialogMessage;
	}

	public String getActionEvent() {
		return actionEvent;
	}
	
	public void setActionEvent(String actionEvent) {
		this.actionEvent = actionEvent;
	}

	public String getRefreshEvent() {
		return refreshEvent;
	}
	
	public void setRefreshEvent(String refreshEvent) {
		this.refreshEvent = refreshEvent;
	}

	public String getCancelEvent() {
		return  cancelEvent;
	}
	
	public void setCancelEvent(String cancelEvent) {
		this.cancelEvent = cancelEvent;
	}
	
	public String getTargetService() {
		return targetService;
	}

	public void setTargetService(String targetService) {
		this.targetService = targetService;
	}
	
	public String getTargetDomain() {
		return targetDomain;
	}

	public void setTargetDomain(String targetDomain) {
		this.targetDomain = targetDomain;
	}

	public String getTargetField() {	
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public String getRenderList() {
		return renderList;
	}

	public void setRenderList(String renderList) {
		this.renderList = renderList;
	}
	
	public int getZIndex() {
		return zIndex;
	}

	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void toggleVisible() {
		visible = !visible;
	}
	
	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}
	
	public boolean wasModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	
	public String getLabel() {
		return "unspecified";
	}

	protected String getMessageDomain() {
		return "default";
	}
	
	public void setContext(String module, String owner, String name) {
		setModule(owner + module);
		setTitle(owner, name);
		setHeader(owner, name);
		setMessage(owner, name);
	}

	public void setTitle(String owner, String name) {
		String label = getLabel();
		if (!StringUtils.isEmpty(name))
			setTitle(name);
		else if (!StringUtils.isEmpty(owner))
			setTitle("New "+owner+" "+label);
		else setTitle("New "+label);
	}
	
	public void setHeader(String owner, String name) {
		String label = getLabel();
		if (StringUtils.isEmpty(owner) && !StringUtils.isEmpty(name))
			setHeader(label+" for: "+name);
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(name))
			setHeader(label+" for "+owner+": "+name);
		else setHeader(label+" Information");
	}
	
	public void setMessage(String owner, String name) {
		String label = getLabel();
		if (!StringUtils.isEmpty(name))
			setDialogMessage("Specify "+label+" for "+owner+": "+name);
		else setDialogMessage("Specify "+label+" for New "+owner);
		String dialogKey = owner + label.replace(" ", "");
		setDialogName(dialogKey + "Dialog");
		setDialogKey(dialogKey);
	}


	protected Validator getValidator() {
		Validator validator = BeanContext.getFromEvent("validator");
		if (validator == null) {
			validator = new Validator();
			//SEAM Contexts.getEventContext().remove("validator");
			//SEAM Contexts.getEventContext().set("validator", validator);
		}
		Validator.setValidator(validator);
		return validator;
	}


	public void setModule(String moduleId) {
		display = BeanContext.getFromSession("display");
		display.setModule(moduleId);
//		if (moduleId == null)
//			System.out.println();
	}
	
	protected Display getDisplay(String moduleId) {
		display = BeanContext.getFromSession("display");
		display.setModule(moduleId);
		return display;
	}

	protected void raiseEvent(String eventId) {
		//SEAM events = Events.instance();
		//SEAM events.raiseEvent(eventId);
	}
	
	
	protected SeamConversationHelper getConversationHelper() {
		return BeanContext.getFromSession("seamConversationHelper");
	}
	
	public void outject(Object instance) {
		getConversationHelper().outject(instance);
	}
	
	public void outjectTo(String name, Object instance) {
		getConversationHelper().outject(targetDomain+"."+name, instance);
	}
	
	public void outject(String name, Object instance) {
		getConversationHelper().outject(getInstanceId(name), instance);
	}
	
	public String getInstanceId(String instanceName) {
		if (targetService != null)
			return targetService + "." + instanceName;
		if (targetDomain != null)
			return targetDomain + "." + instanceName;
		return instanceName;
	}
	

	/*
	 * Lifecycle related
	 */

	public void initialize() {
		if (dialogMessage != null)
			display.info(dialogMessage);
		reset();
	}

	public String refresh() {
		//nothing by default
		return null;
	}
	
	protected Globals getGlobals() {
		return BeanContext.getFromSession("globals");
	}
	
	public void activate() {
		show();
	}

	public void show() {
		Globals globals = getGlobals();
		int nextZIndex = globals.getZIndex() + 100;
		globals.setZIndex(nextZIndex);
		setZIndex(nextZIndex);
//		if (targetDomain != null)
//			setModule(targetDomain);
		setModified(false);
		setValidated(false);
		//setImmediate(true);
		setVisible(true);
	}
	
//	public void hide() {
//		getGlobals().setZIndex(getZIndex() - 10);
//		setVisible(false);
//	}
	
	//SEAM @Observer("org.aries.ui.reset")
	public void reset() {
		setZIndex(0);
		setVisible(false);
		setModified(false);
		setValidated(false);
	}
	
	public String submit() {
		setModified(true);
		if (validate() && actionEvent != null)
			raiseEvent(actionEvent);
		setVisible(false);
		return null;
	}
	
	public boolean validate() {
		//valid by default
		setValidated(true);
		return true;
	}
	
	public void populate() {
		//do nothing by default
	}
	
	public String cancel() {
		setModified(false);
		if (cancelEvent != null)
			raiseEvent(cancelEvent);
		setVisible(false);
		return null;
	}
	
	protected void handleException(Exception e) {
		Throwable cause = ExceptionUtils.getRootCause(e);
		if (cause == null)
			cause = e;
		//cause.printStackTrace();
		String message = cause.getMessage();
		log.error("Error: "+message);
		display = BeanContext.getFromSession("display");
		if (display != null) {
			display.setModule(getMessageDomain());
			display.setModule("registrationWizard");
			display.error("Error: "+message);
		}
	}

}
