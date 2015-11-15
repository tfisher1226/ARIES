package admin.skin;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import admin.Skin;


@SessionScoped
@Named("skinWizard")
@SuppressWarnings("serial")
public class SkinWizard extends AbstractDomainElementWizard<Skin> implements Serializable {
	
	@Inject
	private SkinDataManager skinDataManager;
	
	@Inject
	private SkinPageManager skinPageManager;
	
	@Inject
	private SkinEventManager skinEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Skin";
	}
	
	@Override
	public String getUrlContext() {
		return skinPageManager.getSkinWizardPage();
	}
	
	@Override
	public void initialize(Skin skin) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(skinPageManager.getSections());
		super.initialize(skin);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		skinPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		skinPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		skinPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		skinPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Skin skin = getInstance();
		skinDataManager.saveSkin(skin);
		skinEventManager.fireSavedEvent(skin);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Skin skin = getInstance();
		//TODO take this out soon
		if (skin == null)
			skin = new Skin();
		skinEventManager.fireCancelledEvent(skin);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Skin skin = selectionContext.getSelection("skin");
		String name = skin.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("skinWizard");
			display.error("Skin name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
