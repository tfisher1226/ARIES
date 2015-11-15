package org.aries.ui.skin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import org.aries.runtime.BeanContext;
import org.aries.ui.Globals;
import org.aries.ui.event.ResetEvent;
import org.aries.ui.util.FacesUtil;


@SessionScoped
@ManagedBean(name = "skinBean")
public class SkinBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String SKIN_VIEW_PARAMETER = "skin";
	
	@ManagedProperty(value = "blueSky")
	private String skin;
	
	private List<String> skins;
	
	@Inject
	private Event<ResetEvent> skinChangeEvent;

	private boolean visible;
	
	
	@PostConstruct
	public void initialize() {
		skins = new ArrayList<String>();
		skins.add("aries");
		skins.add("blueSky");
		skins.add("classic");
		skins.add("deepMarine");
		skins.add("emeraldTown");
		skins.add("japanCherry");
		skins.add("ruby");
		skins.add("wine");
	}

	public List<String> getSkins() {
		return skins;
	}

	public String getSkin() {
		String currentSkin = FacesUtil.getViewParameter(SKIN_VIEW_PARAMETER);
		if (currentSkin != null)
			skin = currentSkin;
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getTitle() {
		return "ECOS-NET";
	}

	public String getHeader() {
		return "User-Interface Theme";
	}
	
	public String getDialogMessage() {
		return "Select a new appearance for the UI";
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean wasValidated() {
		return true;
	}
	
	public int getZIndex() {
		Globals globals = BeanContext.getFromSession("globals");
		int zIndex = globals.getZIndex() + 100;
		return zIndex;
	}
	
	public void submit() {
		System.out.println("SelectedSkin: "+skin);
		skinChangeEvent.fire(new ResetEvent());
		//SEAM Events.instance().raiseEvent("org.aries.view.reset");
	}

	public void refresh() {
	}
	
	public void activate() {
		visible = true;
	}
	
	public void cancel() {
		visible = false;
	}
	
}
