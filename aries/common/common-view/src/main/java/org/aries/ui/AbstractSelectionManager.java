package org.aries.ui;

import java.io.Serializable;


@SuppressWarnings("serial")
public abstract class AbstractSelectionManager extends AbstractPanelManager implements Serializable {

	protected boolean selectionRequired;
	
	private boolean multipleSelectionEnabled;


	public boolean isSelectionRequired() {
		return selectionRequired;
	}

	public void setSelectionRequired(boolean selectionRequired) {
		this.selectionRequired = selectionRequired;
	}
	
	public boolean isMultipleSelectionEnabled() {
		return multipleSelectionEnabled;
	}

	public void setMultipleSelectionEnabled(boolean multipleSelectionEnabled) {
		this.multipleSelectionEnabled = multipleSelectionEnabled;
	}
	
}
