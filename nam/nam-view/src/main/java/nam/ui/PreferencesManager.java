package nam.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@SessionScoped
@Named("preferencesManager")
public class PreferencesManager implements Serializable {

	//private Log log;

	//selectedColor
	
	//highlightColor
	
	//@Out(required = true)
	private String openActionTrigger = "onRowClick";

	
	//@Create
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
