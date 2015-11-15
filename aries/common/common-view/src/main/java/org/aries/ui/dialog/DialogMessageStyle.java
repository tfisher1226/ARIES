package org.aries.ui.dialog;


public enum DialogMessageStyle {
	
	Fatal("error", "/icons/status/Error16.gif"),
	
	Error("error", "/icons/status/Error.gif"),
	
	Warn("warn", "/icons/status/Warning16.gif"),
	
	Info("info", "/icons/status/Info16.gif");
	
	private final String iconUrl;
	
	private final String styleClass;

	
	private DialogMessageStyle(String styleClass, String iconUrl) {
		this.styleClass = styleClass;
		this.iconUrl = iconUrl;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}
	
	public String getStyleClass() {
		return styleClass;
	}

}
