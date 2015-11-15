package ecos.ui;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;


@AutoCreate
@Name("preferencesManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class PreferencesManager implements Serializable {

	@Logger
	private Log log;

	//selectedColor
	
	//highlightColor
	
	@Out(required = true)
	private String openActionTrigger = "onRowClick";

	
	@Create
	public void reset() {
		openActionTrigger = "onRowClick";
	}
	
	public String getOpenActionTrigger() {
		return openActionTrigger;
	}

	public void setOpenActionTrigger(String openActionTrigger) {
		this.openActionTrigger = openActionTrigger;
	}

}
