package org.aries.ui.dialog;

import java.io.Serializable;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;



@SessionScoped
@Named("progressDialog")
@SuppressWarnings("serial")
public class ProgressDialog extends AbstractDialog implements Serializable {

//	private static final String ICON_INFO = "/images/icons/status/WindowsInfo.gif";
//
//	private static final String ICON_PROMPT = "/images/icons/status/WindowsPrompt.gif";
//	
//	private static final String ICON_WARNING = "/images/icons/status/WindowsWarning.gif";
//	
//	private static final String ICON_ERROR = "/images/icons/status/WindowsError.gif";

//	private boolean visible;
	
	
	public ProgressDialog() {
//		setTitle("Title");
//		setSubtitle("Subtitle");
//		setMessage("Request In-progress...");
//		setStatus(Status.INFO);
	}
	
//	public void show() {
//		setVisible(true);
//	}
//
//	public void hide() {
//		setVisible(false);
//	}
//
//	public boolean isVisible() {
//		return visible;
//	}
//
//	public void setVisible(boolean visible) {
//		this.visible = visible;
//	}

//	public void initialize(String subtitle, String message) {
//		//setTitle("Request In-progress...");
//		setTitle(null);
//		setSubtitle(subtitle);
//		setMessage(message);
//		//setStatus(Status.INFO);
//	}

//	public boolean isIconExists() {
//		return getIcon() != null;
//	}

//	public String getIcon() {
//		switch (getStatus()) {
//		case INFO: return ICON_INFO;
//		case PROMPT: return ICON_PROMPT;
//		case WARNING: return ICON_WARNING;
//		case ERROR: return ICON_ERROR;
//		default: return "";
//		}
//	}

//	public void setIcon(String icon) {
//		//NO-OP
//	}

	public void cancel() {
		reset();
	}
	
}
