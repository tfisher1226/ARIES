package org.aries.ui.mobile;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MobileView implements Serializable {
	private String viewName;
	
	private String viewId;
	
	private MobileView(final String viewName, final String viewId) {
		this.viewName = viewName;
		this.viewId = viewId;
	}
	
	public static MobileView newInstance(final String viewName, final String viewId) {
		return new MobileView(viewName, viewId);
	}
	
	public String getViewName() {
		return viewName;
	}

	public String getViewId() {
		return viewId;
	}
}
