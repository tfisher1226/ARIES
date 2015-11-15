package org.aries.ui.dialog;

import java.io.Serializable;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



@SessionScoped
@Named("messageDialog")
@SuppressWarnings("serial")
public class MessageDialog extends AbstractDialog implements Serializable {

	private static final String ICON_INFO = "resource://images/icons/status/WindowsInfo64.gif";
	
	private static final String ICON_PROMPT = "resource://images/icons/status/WindowsPrompt64.gif";
	
	private static final String ICON_WARNING = "resource://images/icons/status/WindowsWarning64.gif";
	
	private static final String ICON_ERROR = "resource://images/icons/status/WindowsError64.gif";

	private static final Log log = LogFactory.getLog(MessageDialog.class);

	private String iconUrl;
	
	private boolean messageDialogRunnable;
	
	private boolean messageDialogSelection;
	
	
//	public void createMessageDialog(String subtitle, String message) {
//		reset();
//		setSubtitle(subtitle);
//		setMessage(message);
//		setStatus(Status.INFO);
//		setIconUrl(ICON_INFO);
//	}
//
//	public void createQuestionDialog(String subtitle, String message) {
//		reset();
//		setSubtitle(subtitle);
//		setMessage(message);
//		//setStatus(Status.PROMPT);
//		setIconUrl(ICON_PROMPT);
//		setCancelEnabled(true);
//	}
//
//	public void createWarningDialog(String subtitle, String message) {
//		reset();
//		setSubtitle(subtitle);
//		setMessage(message);
//		//setStatus(Status.WARNING);
//		setIconUrl(ICON_WARNING);
//	}
//
//	public void createErrorDialog(String subtitle, String message) {
//		reset();
//		setSubtitle(subtitle);
//		setMessage(message);
//		//setStatus(Status.ERROR);
//		setIconUrl(ICON_ERROR);
//	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;	
	}

//	protected String calculateIconUrl() {
//		switch (getStatus()) {
//		case INFO: return ICON_INFO;
//		case PROMPT: return ICON_PROMPT;
//		case WARNING: return ICON_WARNING;
//		case ERROR: return ICON_ERROR;
//		default: return "";
//		}
//	}

	public void execute() {
		super.execute();
	}
	
	public void reset() {
		super.reset();
		messageDialogSelection = true;
	}

}
