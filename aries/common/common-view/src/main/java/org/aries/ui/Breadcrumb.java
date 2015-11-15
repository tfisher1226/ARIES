package org.aries.ui;


public class Breadcrumb {

	private String label;
	
	private String action;
	
	
	public Breadcrumb(String label, String action) {
		this.label = label;
		this.action = action;
	}


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
