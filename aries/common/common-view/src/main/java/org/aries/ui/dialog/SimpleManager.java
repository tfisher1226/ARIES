package org.aries.ui.dialog;

import java.io.Serializable;

import org.aries.ui.AbstractPanelManager;


//@SessionScoped
//@Named("sampleManager")
public class SimpleManager extends AbstractPanelManager implements Serializable {

	public SimpleManager() {
	}

	public String refresh() {
		return super.refresh();
	}

	public String submit() {
		setModule("sampleManager");
		return super.submit();
	}
	
	public boolean validate() {
		return super.validate();
	}

	public String cancel() {
		return super.cancel();
	}
	
}
