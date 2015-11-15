package admin.skin;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Skin;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("skinEventManager")
public class SkinEventManager extends AbstractEventManager<Skin> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Skin getInstance() {
		return selectionContext.getSelection("skin");
	}
	
	public void removeSkin() {
		Skin skin = getInstance();
		fireRemoveEvent(skin);
	}
	
}
